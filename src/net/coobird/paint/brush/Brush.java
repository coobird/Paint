package net.coobird.paint.brush;

import java.awt.image.BufferedImage;

public abstract class Brush
{
	protected BufferedImage brush;
	protected BufferedImage thumbBrush;
	protected String name;
	
	public static final int THUMB_SIZE = 20;
	
	/**
	 * Creates a thumbnail of the Brush.
	 * @throws IllegalStateException is thrown when the brush has not
	 * been initialized.
	 */
	protected void makeThumbnail()
	{
		// TODO
		// Resize the brush to create thumbnail
		throw new IllegalStateException("Brush not initialized.");
	}
	
	/**
	 * Returns a String representation of a Brush.
	 * @return	A String representation of the Brush.
	 */
	public String toString()
	{
		String brushString = "Brush: '" + name + "', Size: " +
				brush.getWidth() + ", " + brush.getHeight();
				
		return brushString;
	}

	/**
	 * Gets the name of the Brush.
	 * @return	The name of the Brush.
	 */
	protected String getName()
	{
		return name;
	}

	/**
	 * Sets the name of the Brush.
	 * @param name	The name of the Brush.
	 */
	protected void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * 
	 * @return
	 */
	public BufferedImage getBrush()
	{
		return this.brush;
	}
}
