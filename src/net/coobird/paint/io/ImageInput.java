package net.coobird.paint.io;

import java.io.File;

import net.coobird.paint.image.Canvas;

/**
 *  The {@code ImageInput} class is the base abstract class for image input
 *  filters. Image format reading support will be in the form of subclasses
 *  extending the {@code ImageInput} abstract class.
 * @author coobird
 *
 */
public abstract class ImageInput
{
	/**
	 * 
	 * @param f
	 * @return
	 */
	public abstract Canvas read(File f);
	
	/**
	 * 
	 * @param f
	 * @return
	 */
	public abstract boolean supportsFile(File f);
}
