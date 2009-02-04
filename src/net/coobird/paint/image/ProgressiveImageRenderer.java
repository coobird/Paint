package net.coobird.paint.image;

import java.awt.AlphaComposite;
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
public class ProgressiveImageRenderer implements ImageRenderer
{
	/*
	 * (non-Javadoc)
	 * Look into using the concurrency ?
	 * @see net.coobird.paint.image.ImageRenderer#render(net.coobird.paint.image.Canvas)
	 */
	
	private ExecutorService es;
	
	{
		es = Executors.newFixedThreadPool(4);
	}
	
	class ImageCallable implements Callable<BufferedImage>
	{
		int x,y,w,h; 
		BufferedImage i;
		Canvas c;
		
		public ImageCallable(
				BufferedImage i,
				Canvas c,
				int x,
				int y,
				int width,
				int height
		)
		{
			this.i = i;
			this.c = c;
			this.x = x;
			this.y = y;
			this.w = width;
			this.h = height;
		}
		
		@Override
		public BufferedImage call() throws Exception
		{
			Graphics2D g = i.createGraphics();
			Composite originalComposite = g.getComposite();
			
			for (ImageLayer layer : c.getRenderOrder())
			{
				if (!layer.isVisible())
				{
					continue;
				}
				
				Composite layerComposite = AlphaComposite.getInstance(
						layer.getMode().getComposite().getRule(),
						layer.getAlpha()
				);
				
				g.setComposite(layerComposite);
				BufferedImage img = layer.getImage().getSubimage(x, y, w, h);
				g.drawImage(img, layer.getX(), layer.getY(), null);
			}
			
			g.setComposite(originalComposite);
			g.dispose();
			
			return i;
		}
	}

	@Override
	public BufferedImage render(Canvas c)
	{
		//System.out.println("progressive render start: " + System.currentTimeMillis());
		
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
		
//		while (!es.isShutdown())
//		{
//			try
//			{
//				System.out.println("waiting for ExecutorService to shutdown");
//				Thread.sleep(100);
//			}
//			catch (InterruptedException e)
//			{
//				e.printStackTrace();
//			}
//		}
		
		try
		{
			BufferedImage result1 = f1.get();
			BufferedImage result2 = f2.get();
			BufferedImage result3 = f3.get();
			BufferedImage result4 = f4.get();
			
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
		
		//System.out.println("progressive render end: " + System.currentTimeMillis());
		
		return img;
	}
	
	/**
	 * Terminates the current rendering process.
	 */
	public void terminateRendering()
	{
		// TODO implement method
		// does this work?
		es.shutdownNow();
	}
	
	private void drawBackground(BufferedImage img)
	{
		Graphics g = img.getGraphics();

		int width = img.getWidth();
		int height = img.getHeight();
		
		for (int i = 0; i < width; i += 20)
		{
			for (int j = 0; j < height; j += 20)
			{
				if ((i + j) % 40 == 0)
					g.setColor(Color.gray);
				else
					g.setColor(Color.white);
			
				g.fillRect(i, j, 20, 20);
			}
		}
		
		g.dispose();
	}

}
