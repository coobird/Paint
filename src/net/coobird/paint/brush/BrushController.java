package net.coobird.paint.brush;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.coobird.paint.layer.ImageLayer;

/*
 * FIXME Threading issues?
 * synchronize methods?
 * look into fixing the problems with threading, if exists.
 */

/**
 * The {@code BrushController} handles the drawing of brushes onto an image.
 * @author coobird
 *
 */
public class BrushController
{
	private List<BrushRenderProgressListener> listeners;
	
	{
		listeners = new ArrayList<BrushRenderProgressListener>();
	}
	
	public void addBrushRenderProgressListener(BrushRenderProgressListener l)
	{
		listeners.add(l);
	}
	
	/**
	 * The {@code BrushAction} class represents an event to process.
	 * Events such as drawing and releasing a brush is represented as an
	 * instance of {@code BrushAction}, and is processed by the 
	 * {@code BrushController}.
	 * @author coobird
	 *
	 */
	private static class BrushAction
	{
		private final Brush b;
		private final ImageLayer il;
		private final int x;
		private final int y;
		private final State state;

		private static enum State
		{
			DRAW,
			RELEASE;
		}
		
		private BrushAction(ImageLayer il, Brush b, int x, int y)
		{
			this.b = b;
			this.il = il;
			this.x = x;
			this.y = y;
			this.state = State.DRAW;
		}
		
		private BrushAction()
		{
			this.b = null;
			this.il = null;
			this.x = UNDEFINED;
			this.y = UNDEFINED;
			this.state = State.RELEASE;
		}
		
		private int getX()
		{
			return this.x;
			
		}
		private int getY()
		{
			return this.y;
			
		}
		private Brush getBrush()
		{
			return this.b;
			
		}
		private ImageLayer getLayer()
		{
			return this.il;
			
		}
		private State getState()
		{
			return this.state;
			
		}
	}

	private static final int UNDEFINED = Integer.MIN_VALUE;
	
	private Queue<BrushAction> actionQueue;
	private double theta;
	private int lastX = UNDEFINED;
	private int lastY = UNDEFINED;
	private Brush lastBrush = null;

	private DrawingThread drawingThread = new DrawingThread();
	private ExecutorService executor = Executors.newSingleThreadExecutor();
	
	
	/**
	 * Constructs an instance of the {@code BrushController}.
	 */
	public BrushController()
	{
		actionQueue = new LinkedList<BrushAction>();
		theta = 0;
	}
	
	/**
	 * Draws the brush between last draw point and current draw point.
	 * @param action		The {@code BrushAction} containing the draw action.
	 */
	private void interpolatedDraw(BrushAction action)
	{
		/*
		 *  Constant used to determine the distance moved between one brush
		 *  draw. By detault, the distance is 1/8 the size of the brush.
		 */
		final int STEP_DIVISION = 8;
		
		Graphics2D g = action.getLayer().getGraphics();
		setCompositeForBrush(g, action.getBrush());
		BufferedImage brushImage = action.getBrush().getImage();

		double distX = (double)lastX - (double)action.getX();
		double distY = (double)lastY - (double)action.getY();
	
		double dist = Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
		
		double steps = (dist / ((double)brushImage.getWidth() / (double)STEP_DIVISION)) + 1;
		
		double incX = -distX / steps;
		double incY = -distY / steps;
		
		double x = (double)lastX - (brushImage.getWidth() / 2.0d);
		double y = (double)lastY - (brushImage.getHeight() / 2.0d);
		
		int intSteps = (int)Math.round(steps);
		
		BufferedImage rotatedBrushImage = new BufferedImage(
				brushImage.getWidth(),
				brushImage.getHeight(),
				Brush.DEFAULT_BRUSH_TYPE
		);
		
		/*
		 * FIXME Could improve rotating quality of brush is brush is recalculated
		 * for each step. The rotated brush creation would have to be in
		 * the loop instead.
		 */

		Graphics2D brushg = rotatedBrushImage.createGraphics();
		
		// FIXME improve rotation rendering quality. performance impact?
//		brushg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		brushg.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		if (action.getBrush().isRotatable())
		{
			brushg.rotate(
					-theta,
					rotatedBrushImage.getWidth() / 2.0d,
					rotatedBrushImage.getHeight() / 2.0d
			);
		}

		brushg.drawImage(brushImage, 0, 0, null);
		brushg.dispose();

		for (int i = 0; i < intSteps; i++)
		{
			x += incX;
			y += incY;
			
			g.drawImage(rotatedBrushImage, (int)x, (int)y, null);
		}
	}

	/*
	 * Note: Exception happened here, may be threading-related.
	 *  
	 * Exception in thread "pool-1-thread-1" java.util.NoSuchElementException
	at java.util.LinkedList.remove(Unknown Source)
	at java.util.LinkedList.removeFirst(Unknown Source)
	at java.util.LinkedList.remove(Unknown Source)
	at net.coobird.paint.brush.BrushController.processBrush(BrushController.java:217)
	at net.coobird.paint.brush.BrushController.access$1(BrushController.java:215)
	at net.coobird.paint.brush.BrushController$DrawingThread.run(BrushController.java:397)
	at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(Unknown Source)processing

	at java.util.concurrent.ThreadPoolExecutor$Worker.run(Unknown Source)
	at java.lang.Thread.run(Unknown Source)
	 */
	/*
	 * Experimentally synchronizing the processBrush method.
	 */
	
	/**
	 * Processes a {@code BrushAction} in the queue.
	 */
	private synchronized void processBrush()
	{
		BrushAction action = actionQueue.remove();
		
		if (
				lastBrush != action.getBrush() ||
				action.getState() == BrushAction.State.RELEASE
		)
		{
			clearTheta();
		}
		
		if (action.getState() == BrushAction.State.RELEASE)
		{
			lastX = UNDEFINED;
			lastY = UNDEFINED;
			return;
		}
		

		Graphics2D g = action.getLayer().getGraphics();
		
		// TODO Setting Composite for g should be fine.
		// if so, remove one setComposite... from interpolateDraw and here.
		// uncomment below:
		// setCompositeForBrush(g, action.getBrush());

		BufferedImage brushImage = action.getBrush().getImage();
		
		// Determine the brush painting mean to use.
		// If brush is already being dragged across, then use interpolatedDraw.
		// If not, handle the "single-click" painting of brush.
		if (lastX != UNDEFINED)
		{
			if (action.getBrush().isRotatable())
			{
				calcTheta(action.getX(), action.getY());
			}
			
			interpolatedDraw(action);
		}
		else
		{
			int x = action.getX() - (brushImage.getWidth() / 2);
			int y = action.getY() - (brushImage.getHeight() / 2);

			setCompositeForBrush(g, action.getBrush());
			g.drawImage(brushImage, x, y, null);
		}
		
		lastX = action.x;
		lastY = action.y;
		
		lastBrush = action.getBrush();
	}
	
	/**
	 * Sets the {@link Composite} of the given {@link Graphics2D} object. Used
	 * for setting the composite mode of the {@code Graphics2D} object to allow
	 * switching modes between a regular brush and an eraser.
	 * @param g				The {@code Graphics2D} object to change the
	 * 						composite mode of.
	 * @param b				The {@code Brush} object from which the composite
	 * 						mode to set to should be retrieved.
	 */
	private void setCompositeForBrush(Graphics2D g, Brush b)
	{
		Composite layerComposite = AlphaComposite.getInstance(
				b.getMode().getComposite().getRule(),
				b.getAlpha()
		);
		
		g.setComposite(layerComposite);
	}
	
	/**
	 * Draws the on the specified {@link ImageLayer} object, using the specified
	 * {@link Brush} object at the location {@code x, y}.
	 * @param il			The {@code ImageLayer} object to draw on.
	 * @param b				The {@code Brush} object to draw with.
	 * @param x				The {@code x} coordinate of the {@code ImageLayer}
	 * 						to draw the brush at.
	 * @param y				The {@code y} coordinate of the {@code ImageLayer}
	 * 						to draw the brush at.
	 */
	public void drawBrush(ImageLayer il, Brush b, int x, int y)
	{
		/*
		 * Account for offset of the ImageLayer
		 * wont work at zoom != 1
		 */
//		x = x - il.getX();
//		y = y - il.getY();
		
		actionQueue.add(new BrushAction(il, b, x, y));
		
		if (!drawingThread.running)
		{
			executor.execute(drawingThread);
		}
	}
	
	public void releaseBrush()
	{
		actionQueue.add(new BrushAction());
	}
	
	/**
	 * Calculate the angle to rotate the brush.
	 * @param x			The x coordinate of the brush.
	 * @param y			The y coordinate of the brush.
	 */
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
	
	/**
	 * Clears the stored location and angle of the last brush action.
	 */
	private void clearTheta()
	{
		lastX = UNDEFINED;
		lastY = UNDEFINED;
	}
	
	/**
	 * Thread for processing the {@link BrushAction} queue.
	 * @author coobird
	 *
	 */
	class DrawingThread extends Thread
	{
		boolean running = false;
		
		/**
		 * Processes the {@link BrushAction} queue.
		 */
		public void run()
		{
			running = true;
			while(!actionQueue.isEmpty())
			{
				processBrush();
				
				for (BrushRenderProgressListener listener : listeners)
				{
					listener.drawProgress(actionQueue.size());
				}
			}
			for (BrushRenderProgressListener listener : listeners)
			{
				listener.drawComplete();
			}
			running = false;
		}
	}
}
