package net.coobird.paint.filter;

import java.awt.image.BufferedImage;

/**
 * Dummy ImageFilter. Returns a BufferedImage that is passed in.
 * @author coobird
 *
 */
public final class NullImageFilter extends ImageFilter
{
	
	/**
	 * Default constructor.
	 */
	public NullImageFilter()
	{
		this("Null Image Filter");
	}
	
	/**
	 * Creates a NullImageFilter instance with specified name.
	 * @param name	Name for the instance of NullImageFilter.
	 */
	public NullImageFilter(String name)
	{
		this.setName(name);
	}

	/**
	 * Dummy image filter. Performs no operation.
	 * @param img	BufferedImage to pass in.
	 * @return		BufferedImage which was passed in as the argument.
	 */
	@Override
	public BufferedImage processImage(BufferedImage img)
	{
		return img;
	}
}
