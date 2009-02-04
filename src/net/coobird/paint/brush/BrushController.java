package net.coobird.paint.brush;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

import net.coobird.paint.image.ImageLayer;

public class BrushController
{
	static class BrushEvent
	{
		private Brush b;
		private ImageLayer il;
		private int x;
		private int y;
		private State state;

		private enum State
		{
			DRAW,
			RELEASE;
		}
		
		private BrushEvent(ImageLayer il, Brush b, int x, int y)
		{
			this.b = b;
			this.il = il;
			this.x = x;
			this.y = y;
			this.state = State.DRAW;
		}
		
		private BrushEvent()
		{
			this.state = State.RELEASE;
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
		
		if (lastBrush != e.b || e.state == BrushEvent.State.RELEASE)
		{
			clearTheta();
		}
		
		if (e.state == BrushEvent.State.RELEASE)
		{
			lastX = UNDEFINED;
			lastY = UNDEFINED;
			return;
		}
		
		calcTheta(e.x, e.y);
		
		Graphics2D g = e.il.getGraphics();
		BufferedImage b = e.b.getBrush();
		
		
		if (lastX != UNDEFINED)
		{
			double distX = lastX - e.x;
			double distY = lastY - e.y;

			System.out.println(distX);
			System.out.println(distY);
			
			double dist = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
			double steps = dist / ((b.getWidth())/ 8);
			
			double incX = -distX / steps;
			double incY = -distY / steps;
			
			double x = lastX - (b.getWidth() / 2);
			double y = lastY - (b.getHeight() / 2);
			
			int intSteps = (int)Math.round(steps);
			
			for (int i = 0; i < intSteps; i++)
			{
				x += incX;
				y += incY;
				
				g.drawImage(b, (int)x, (int)y, null);			
			}
		}
		else
		{
			int x = e.x - (b.getWidth() / 2);
			int y = e.y - (b.getHeight() / 2);
			
			g.drawImage(b, x, y, null);
		}
		
		lastX = e.x;
		lastY = e.y;
		
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
	
	public void releaseBrush()
	{
		queue.add(new BrushEvent());
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
