package net.coobird.paint.brush;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

import net.coobird.paint.image.ImageLayer;

public class BrushController
{
	class BrushEvent
	{
		private Brush b;
		private ImageLayer il;
		private int x;
		private int y;
		
		private BrushEvent(ImageLayer il, Brush b, int x, int y)
		{
			this.b = b;
			this.il = il;
			this.x = x;
			this.y = y;
		}
	}
	
	// queue of events
	// angle of brush
	Queue<BrushEvent> queue = new LinkedList<BrushEvent>(); 
	
	private double theta;
	private static final int UNDEFINED = Integer.MIN_VALUE;
	private int lastX = UNDEFINED;
	private int lastY = UNDEFINED;
	private Brush lastBrush = null;
	
	class DrawingThread extends Thread
	{
		boolean running = false;
		
		public void run()
		{
			running = true;
			while(!queue.isEmpty())
			{
				System.out.println("processing");
				processBrush();
			}
			running = false;
		}
	}

	private DrawingThread drawingThread = new DrawingThread();
	
	public BrushController()
	{
		theta = 0;
	}
	
	private void processBrush()
	{
		BrushEvent e = queue.remove();
		
		if (lastBrush != e.b)
		{
			clearTheta();
		}
		
		calcTheta(e.x, e.y);
		
		Graphics2D g = e.il.getGraphics();
		BufferedImage b = e.b.getBrush();
		
		g.drawImage(
				b,
				e.x - (b.getWidth() / 2),
				e.y - (b.getHeight() / 2),
				null
		);		
		
		lastBrush = e.b;
	}
	
	private long lastTime = System.currentTimeMillis();
	
	public void drawBrush(ImageLayer il, Brush b, int x, int y)
	{
		queue.add(new BrushEvent(il, b, x, y));
		System.out.println("add brushevent");
		long timePast = System.currentTimeMillis() - lastTime;
		
		if (!drawingThread.running && timePast > 100)
		{
			System.out.println("start thread");
			new DrawingThread().start();
			lastTime = System.currentTimeMillis();
		}
	}
	
	private void calcTheta(int x, int y)
	{
		if (lastX == UNDEFINED && lastY == UNDEFINED)
		{
			lastX = x;
			lastY = y;
		}
		int diffX = lastX - x;
		int diffY = lastY - y;
		
		theta = Math.atan2(diffX, diffY);
	}
	
	private void clearTheta()
	{
		lastX = UNDEFINED;
		lastY = UNDEFINED;
	}
}
