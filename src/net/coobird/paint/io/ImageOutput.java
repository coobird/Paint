package net.coobird.paint.io;

import java.io.File;

import net.coobird.paint.image.Canvas;

/**
 *  The {@code ImageOutput} class is the base abstract class for image output
 *  filters. Image format writing support will be in the form of subclasses
 *  extending the {@code ImageOutput} abstract class.
 * @author coobird
 *
 */
public abstract class ImageOutput
{
	/**
	 * Writes the contents of the {@link Canvas} object to file specified in
	 * the {@link File} object in  the format defined by the [@code ImageOutput}
	 * filter. 
	 * @param c				The {@code Canvas} object to write.
	 * @param f				The [@code File} object to write to.
	 */
	public abstract void write(Canvas c, File f);
}
