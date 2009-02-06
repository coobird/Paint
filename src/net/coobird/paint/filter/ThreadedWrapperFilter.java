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
	private ImageFilter filter;
	
	private ThreadedWrapperFilter() {}
	
	public ThreadedWrapperFilter(ImageFilter filter)
	{
		this.filter = filter;
	}
	
	@Override
	public BufferedImage processImage(BufferedImage img)
	{
		int halfWidth = img.getWidth() / 2;
		int halfHeight = img.getHeight() / 2;
		
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
		
		ExecutorService es = Executors.newCachedThreadPool();
		
		Future<BufferedImage> f1 = es.submit(new Callable<BufferedImage>() {
			public BufferedImage call()
			{
				return filter.processImage(i1);
			}
		});
		Future<BufferedImage> f2 = es.submit(new Callable<BufferedImage>() {
			public BufferedImage call()
			{
				return filter.processImage(i2);
			}
		});
		Future<BufferedImage> f3 = es.submit(new Callable<BufferedImage>() {
			public BufferedImage call()
			{
				return filter.processImage(i3);
			}
		});
		Future<BufferedImage> f4 = es.submit(new Callable<BufferedImage>() {
			public BufferedImage call()
			{
				return filter.processImage(i4);
			}
		});
		
		Graphics2D g = result.createGraphics();
		
		try
		{
			g.drawImage(f1.get(), 0, 0, null);
			g.drawImage(f2.get(), halfHeight, 0, null);
			g.drawImage(f3.get(), 0, halfHeight, null);
			g.drawImage(f4.get(), halfHeight, halfHeight, null);
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
