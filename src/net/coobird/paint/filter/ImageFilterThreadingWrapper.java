package net.coobird.paint.filter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
 * This wrapper only works if
 * BufferedImage(x,y,t) -> BufferedImage(x,y,t)
 * 
 * If it is expected that:
 * BufferedImage(x,y,t) -> BufferedImage(cx,cy,t) where c != 1 then
 * result is BufferedImage(x,y,t) where the contents is mangled.
 * 
 * Either this needs to be fixed, or this wrapper must be of limited use.
 * 
 * Problem comes from execution of the Callable.
 * After the filter is called a getSubImage call will trim off the OVERLAP
 * section, but it assumes that the image dimensions remain the same. If not,
 * the image will be cropped, or worse, a crash if resulting image is too small.
 * 
 * There needs to be a check to prevent a crash, if that is the case.
 */

/**
 * <p>
 * The {@code ImageFilterThreadingWrapper} class is a wrapper class to enable
 * multithreaded processing of images using a specified {@code ImageFilter}.
 * </p>
 * <p>
 * Note: An image with at least one dimension under 100 pixels will cause the
 * {@code ImageFilterThreadingWrapper} to bypass the multithreaded rendering
 * operation andÅ@handoff the processing to the wrapped {@link ImageFilter}.
 * </p>
 * <p>
 * At small sizes, the expected time savings from using multithreaded rendering
 * is negative, as the overhead for creating threads is larger than the benefit
 * of using multiple threads to render the image.
 * </p>
 * @author coobird
 *
 */
public class ImageFilterThreadingWrapper extends ImageFilter
{
	/**
	 * Number of pixels to overlap between the rendered quadrants.
	 */
	private static final int DEFAULT_OVERLAP = 5;
	private int overlap;

	/**
	 * The wrapped {@code ImageFilter}.
	 */
	private ImageFilter filter;

	/**
	 * Instantiates a {@code ThreadedWrapperFilter} object with the specified
	 * {@link ImageFilter}.
	 * @param filter		The {@code ImageFilter} to wrap.
	 */
	public ImageFilterThreadingWrapper(ImageFilter filter)
	{
		this(filter, DEFAULT_OVERLAP);
	}

	/**
	 * Instantiates a {@code ThreadedWrapperFilter} object with the specified
	 * {@link ImageFilter}, with a specified overlap between the processed.
	 * 
	 * The overlap should be at least as much the single most largest dimension
	 * of a convolution matrix that is being applied.
	 * 
	 * subimages.
	 * @param filter		The {@code ImageFilter} to wrap.
	 * @param overlap		The overlap of the processed subimages in pixels.
	 */
	public ImageFilterThreadingWrapper(ImageFilter filter, int overlap)
	{
		this(
				"ImageFilterThreadingWrapper wrapping " + filter.getName(),
				filter
		);
		
		this.overlap = overlap;
	}
	
	/**
	 * Instantiates a {@code ThreadedWrapperFilter} object with the specified
	 * {@link ImageFilter}.
	 * @param name			The name of the filter.
	 * @param filter		The {@code ImageFilter} to wrap.
	 */
	public ImageFilterThreadingWrapper(String name, ImageFilter filter)
	{
		super(name);
		this.filter = filter;
	}

	/**
	 * Process a image and return the filtered image.
	 * @param img			The image to process.
	 * @return				The filtered image.
	 */
	@Override
	public BufferedImage processImage(BufferedImage img)
	{
		final int overlap = this.overlap;
		int width = img.getWidth();
		int height = img.getHeight();
		
		ExecutorService es = null;
		
		/*
		 * If only one processor is available, multi-threaded processing will
		 * be bypassed.
		 * TODO Reconsider this behavior -- is using a single thread really
		 * going to be better than multiple threads for single core?
		 */
		/*
		 *  If the input size is less than 100 in any dimension, or if the
		 *  bypass flag is set, multi-threaded rendering will be bypassed, and
		 *  processing will be performed by the wrapped
		 */
		if (
				Runtime.getRuntime().availableProcessors() == 1 ||
				width < 100 ||
				height < 100
		)
		{
			return filter.processImage(img);
		}
		else
		{
			es = Executors.newFixedThreadPool(4);
		}

		final int halfWidth = width / 2;
		final int halfHeight = height / 2;
		
		/*
		 * Prepare the source images to perform the filtering on.
		 * Each image has an overhang to prevent a "bordering" effect when
		 * stitching the images back together at the end.
		 */
		final BufferedImage i1 = img.getSubimage(
				0,
				0,
				halfWidth + overlap,
				halfHeight + overlap
		);
		
		final BufferedImage i2 = img.getSubimage(
				halfWidth - overlap,
				0,
				halfWidth + overlap,
				halfHeight + overlap
		);
		
		final BufferedImage i3 = img.getSubimage(
				0,
				halfHeight - overlap,
				halfWidth + overlap,
				halfHeight + overlap
		);
		
		final BufferedImage i4 = img.getSubimage(
				halfWidth - overlap,
				halfHeight - overlap,
				halfWidth + overlap,
				halfHeight + overlap
		);
		
		BufferedImage result = new BufferedImage(
				img.getWidth(),
				img.getHeight(),
				img.getType()
		);
		
		/*
		 * Send off a Callable to four threads.
		 * Each thread will call the filter.processImage, then take a subimage
		 * and return that as the result.
		 * The subimageÅ@is shifted to remove the overlap to eliminate the 
		 * "bordering" effect (which occurs at the edges of an image when a
		 * filter is applied) which makes the resulting stitched up image to
		 * have borders down the horizontal and vertical center of the image.
		 */
		Future<BufferedImage> f1 = es.submit(new Callable<BufferedImage>() {
			public BufferedImage call()
			{
				return filter.processImage(i1).getSubimage(
						0,
						0,
						halfWidth,
						halfHeight
				);
			}
		});
		Future<BufferedImage> f2 = es.submit(new Callable<BufferedImage>() {
			public BufferedImage call()
			{
				return filter.processImage(i2).getSubimage(
						overlap,
						0,
						halfWidth,
						halfHeight
				);
			}
		});
		Future<BufferedImage> f3 = es.submit(new Callable<BufferedImage>() {
			public BufferedImage call()
			{
				return filter.processImage(i3).getSubimage(
						0,
						overlap,
						halfWidth,
						halfHeight
				);
			}
		});
		Future<BufferedImage> f4 = es.submit(new Callable<BufferedImage>() {
			public BufferedImage call()
			{
				return filter.processImage(i4).getSubimage(
						overlap,
						overlap,
						halfWidth,
						halfHeight
				);
			}
		});
		
		/*
		 * Stitch up the results from each of the four threads to make the
		 * resulting image.
		 */
		Graphics2D g = result.createGraphics();
		try
		{
			g.drawImage(f1.get(), 0, 0, null);
			g.drawImage(f2.get(), halfWidth, 0, null);
			g.drawImage(f3.get(), 0, halfHeight, null);
			g.drawImage(f4.get(), halfWidth, halfHeight, null);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		catch (ExecutionException e)
		{
			e.printStackTrace();
		}
		finally
		{
			g.dispose();
			es.shutdown();
			es = null;
		}
		
		return result;
	}
}
