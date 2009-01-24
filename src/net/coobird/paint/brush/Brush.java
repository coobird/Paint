package net.coobird.paint.brush;

import java.awt.Graphics;
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
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name of the Brush.
	 * @param name	The name of the Brush.
	 */
	public void setName(String name)
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
	
	public BufferedImage getThumbBrush()
	{
		return this.thumbBrush;
	}

	/**
	 * 
	 * @param size
	 * @param brushColor
	 */
	protected void makeBrushThumbnail()
	{
		int size = Brush.THUMB_SIZE;
	
		thumbBrush = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
	
		Graphics g = thumbBrush.createGraphics();
		g.drawImage(brush, 0, 0, size, size, null);
		g.dispose();
	}
}
