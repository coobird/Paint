package net.coobird.paint.filter;

import java.awt.image.BufferedImage;

/**
 * Base abstract class of ImageFilters to be used in Paint Application With
 * No Interesting Name.
 * @author coobird
 *
 */
public abstract class ImageFilter
{
	/*
	 * The name of the filter.
	 */
	protected String name;

	/**
	 * Processes the specified BufferedImage with this ImageFilter.
	 * @param img	BufferedImage to be processed.
	 * @return		Resulting BufferedImage after processing.
	 */
	public abstract BufferedImage processImage(BufferedImage img);

	/**
	 * Gets the name of the filter.
	 * @return	Name of the filter.
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Sets the name of the filter.
	 * @param name	Name of the filter.
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}
