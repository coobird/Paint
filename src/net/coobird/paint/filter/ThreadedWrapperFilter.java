package net.coobird.paint.filter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadedWrapperFilter extends ImageFilter
{
	/**
	 * Number of pixels to overlap between the rendered quadrants.
	 */
	private static final int OVERLAP = 5;
	private ImageFilter filter;
	
	/**
	 * Cannot instantiate an {@code ThreadedWrapperFilter} without any
	 * arguments.
	 */
	private ThreadedWrapperFilter() {}
	
	/**
	 * Instantiates a {@code ThreadedWrapperFilter} object with the specified
	 * {@link ImageFilter}.
	 * @param filter		The {@code ImageFilter} to wrap.
	 */
	public ThreadedWrapperFilter(ImageFilter filter)
	{
		this.filter = filter;
	}
	
	@Override
	public BufferedImage processImage(BufferedImage img)
	{
		int width = img.getWidth();
		int height = img.getHeight();
		
		// If the input size is less than 100 in any dimension, bypass the
		// multithreaded rendering.
		if (width < 100 || height < 100)
		{
			return filter.processImage(img);
		}
		
		final int halfWidth = img.getWidth() / 2;
		final int halfHeight = img.getHeight() / 2;
		
		final BufferedImage i1 = img.getSubimage(
				0,
				0,
				halfWidth + 5,
				halfHeight + 5
		);
		
		final BufferedImage i2 = img.getSubimage(
				halfWidth - 5,
				0,
				halfWidth + 5,
				halfHeight + 5
		);
		
		final BufferedImage i3 = img.getSubimage(
				0,
				halfHeight - 5,
				halfWidth + 5,
				halfHeight + 5
		);
		
		final BufferedImage i4 = img.getSubimage(
				halfWidth - 5,
				halfHeight - 5,
				halfWidth + 5,
				halfHeight +5
		);
		
		BufferedImage result = new BufferedImage(
				img.getWidth(),
				img.getHeight(),
				img.getType()
		);
		
		//ExecutorService es = Executors.newCachedThreadPool();
		ExecutorService es = Executors.newFixedThreadPool(4);
		
		Future<BufferedImage> f1 = es.submit(new Callable<BufferedImage>() {
			public BufferedImage call()
			{
				return filter.processImage(i1).getSubimage(0, 0, halfWidth, halfHeight);
			}
		});
		Future<BufferedImage> f2 = es.submit(new Callable<BufferedImage>() {
			public BufferedImage call()
			{
				return filter.processImage(i2).getSubimage(OVERLAP, 0, halfWidth, halfHeight);
			}
		});
		Future<BufferedImage> f3 = es.submit(new Callable<BufferedImage>() {
			public BufferedImage call()
			{
				return filter.processImage(i3).getSubimage(0, 5, halfWidth, halfHeight);
			}
		});
		Future<BufferedImage> f4 = es.submit(new Callable<BufferedImage>() {
			public BufferedImage call()
			{
				return filter.processImage(i4).getSubimage(5, 5, halfWidth, halfHeight);
			}
		});
		
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
		
		g.dispose();
		
		return result;
	}

}
