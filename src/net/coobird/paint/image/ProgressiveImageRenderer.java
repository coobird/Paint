package net.coobird.paint.image;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Progressively renders the canvas.
 * Canvas is divided into N fields and rendered independently by separate
 * threads, all of which can be terminated by calling the terminateRendering
 * method.
 * This ImageRenderer will allow for canceling rendering jobs, ideal for
 * stopping rendering trying out different rendering settings (such as 
 * change in transparency)
 * Not implemented.
 * @author coobird
 *
 */
public final class ProgressiveImageRenderer implements ImageRenderer
{
	/*
	 * TODO drawBackground should be updated to allow offset (x,y,w,h)
	 */
	
	private ExecutorService es;
	
	/**
	 * Instantiates an instance of {@code ProgressiveImageRenderer}.
	 */
	public ProgressiveImageRenderer()
	{
		// TODO adjust the number of threads in thread pool?
		es = Executors.newFixedThreadPool(4);
	}
	
	private static class ImageCallable implements Callable<BufferedImage>
	{
		int x, y, w, h; 
		BufferedImage img;
		Canvas c;
		
		public ImageCallable(
				BufferedImage img,
				Canvas c,
				int x,
				int y,
				int width,
				int height
		)
		{
			this.img = img;
			this.c = c;
			this.x = x;
			this.y = y;
			this.w = width;
			this.h = height;
		}
		
		@Override
		public BufferedImage call() throws Exception
		{
			Graphics2D g = img.createGraphics();
			Composite originalComposite = g.getComposite();
			
			for (ImageLayer layer : c.getRenderOrder())
			{
				if (!layer.isVisible())
				{
					continue;
				}
				
				g.setComposite(layer.getAlphaComposite());
				
				BufferedImage img = layer.getImage().getSubimage(x, y, w, h);
				
				g.drawImage(img, layer.getX(), layer.getY(), null);
			}
			
			g.setComposite(originalComposite);
			g.dispose();
			
			return img;
		}
	}

	/**
	 * 
	 */
	public BufferedImage render(Canvas c)
	{
		return render(c, true);
	}
	
	/**
	 * Renders a canvas.
	 * 
	 * Current implementation will section the canvas into four sections and
	 * renders them in four (4) separate Threads, using the 
	 * {@link ExecutorService} with a thread pool of 4 threads.
	 * 
	 * @param c				The canvas to render.
	 * @return				The rendered image of the canvas.
	 */
	/*
	 * (non-Javadoc)
	 * @see net.coobird.paint.image.ImageRenderer#render(net.coobird.paint.image.Canvas)
	 */
	@Override
	public BufferedImage render(Canvas c, boolean drawBackground)
	{
		/*
		 * TODO current implementation ignores drawBackground. Fix this.
		 */
		
		final BufferedImage img = new BufferedImage(
			c.getWidth(),
			c.getHeight(),
			ImageLayer.getDefaultType()
		);
		
		final BufferedImage i1 = new BufferedImage(
				c.getWidth() / 2,
				c.getHeight() / 2,
				ImageLayer.getDefaultType()
		);
		final BufferedImage i2 = new BufferedImage(
				c.getWidth() / 2,
				c.getHeight() / 2,
				ImageLayer.getDefaultType()
		);
		final BufferedImage i3 = new BufferedImage(
				c.getWidth() / 2,
				c.getHeight() / 2,
				ImageLayer.getDefaultType()
		);
		final BufferedImage i4 = new BufferedImage(
				c.getWidth() / 2,
				c.getHeight() / 2,
				ImageLayer.getDefaultType()
		);
		
		
		ImageCallable c1 = new ImageCallable(
				i1,
				c,
				0,
				0,
				c.getWidth() / 2,
				c.getHeight() / 2
		);
		ImageCallable c2 = new ImageCallable(
				i2,
				c,
				c.getWidth() / 2,
				0,
				c.getWidth() / 2,
				c.getHeight() / 2
		);
		ImageCallable c3 = new ImageCallable(
				i3,
				c,
				0,
				c.getHeight() / 2,
				c.getWidth() / 2,
				c.getHeight() / 2
		);
		ImageCallable c4 = new ImageCallable(
				i4,
				c,
				c.getWidth() / 2,
				c.getHeight() / 2,
				c.getWidth() / 2,
				c.getHeight() / 2
		);
		
		Future<BufferedImage> f1 = es.submit(c1);
		Future<BufferedImage> f2 = es.submit(c2);
		Future<BufferedImage> f3 = es.submit(c3);
		Future<BufferedImage> f4 = es.submit(c4);
		
		try
		{
			BufferedImage result1 = f1.get();
			BufferedImage result2 = f2.get();
			BufferedImage result3 = f3.get();
			BufferedImage result4 = f4.get();
			
			/*
			 * TODO re-order this to come before f#.get()?
			 * Possibly allow better performance?
			 * Or would it slow things down if many threads are already running?
			 */
			Graphics2D g = img.createGraphics();
			
			drawBackground(img);
			
			g.drawImage(result1, 0, 0, null);
			g.drawImage(result2, c.getWidth() / 2, 0, null);
			g.drawImage(result3, 0, c.getHeight() / 2, null);
			g.drawImage(result4, c.getWidth() / 2, c.getHeight() / 2, null);
			g.dispose();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		catch (ExecutionException e)
		{
			e.printStackTrace();
		}
		
		return img;
	}
	
	/**
	 * Terminates the current rendering process.
	 */
	public void terminateRendering()
	{
		// TODO implement method
		// TODO test this.
		// does this work?
		es.shutdownNow();
	}
	
	/**
	 * Draws a checkered background.
	 * @param img			The {@code BufferedImage} object to render a
	 * 						checkered background on.
	 */
	private void drawBackground(BufferedImage img)
	{
		final int SIZE = 20;
		final int DOUBLE_SIZE = SIZE * 2;
		
		Graphics g = img.getGraphics();

		int width = img.getWidth();
		int height = img.getHeight();
		
		for (int i = 0; i < width; i += SIZE)
		{
			for (int j = 0; j < height; j += SIZE)
			{
				if ((i + j) % DOUBLE_SIZE == 0)
				{
					g.setColor(Color.gray);
				}
				else
				{
					g.setColor(Color.white);
				}
			
				g.fillRect(i, j, SIZE, SIZE);
			}
		}
		
		g.dispose();
	}
}
