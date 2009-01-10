package net.coobird.paint.image;

import java.awt.image.BufferedImage;

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

	@Override
	public BufferedImage render(Canvas c)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Terminates the current rendering process.
	 */
	public void terminateRendering()
	{
		// TODO implement method
	}

}
