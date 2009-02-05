package net.coobird.paint.brush;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.coobird.paint.BlendingMode;

public abstract class Brush
{
	protected BufferedImage brush;
	protected BufferedImage thumbBrush;
	protected String name;
	protected BlendingMode mode = BlendingMode.LAYER_NORMAL;
	protected float alpha = 1f;
	
	public static final int THUMB_SIZE = 20;
	protected final int DEFAULT_BRUSH_TYPE = BufferedImage.TYPE_INT_ARGB;
	
	/**
	 * Creates a thumbnail of the Brush.
	 * @throws IllegalStateException is thrown when the brush has not
	 * been initialized.
	 */
	protected void makeThumbnail()
	{
		if (brush == null)
		{
			throw new IllegalStateException("Brush not initialized.");
		}
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
	 * Returns a copy of the brush.
	 * Returns null if brush is not initialized.
	 * @return
	 */
	public BufferedImage getBrush()
	{
		if (brush == null)
		{
			return null;
		}
		
		BufferedImage brushCopy = new BufferedImage(
				brush.getWidth(),
				brush.getHeight(),
				brush.getType()
		);
		
		Graphics2D g = brushCopy.createGraphics();
		g.drawImage(brush, 0, 0, null);
		g.dispose();
		
		return brushCopy;
	}
	
	/**
	 * Returns a copy of the thumbnail of the brush.
	 * Returns null if thumbnail of the brush is not initialized.
	 * @return
	 */
	public BufferedImage getThumbBrush()
	{
		if (thumbBrush == null)
		{
			return null;
		}

		BufferedImage thumbBrushCopy = new BufferedImage(
				thumbBrush.getWidth(),
				thumbBrush.getHeight(),
				thumbBrush.getType()
		);
		
		Graphics2D g = thumbBrushCopy.createGraphics();
		g.drawImage(thumbBrush, 0, 0, null);
		g.dispose();

		return thumbBrushCopy;
	}
	
	/**
	 * 
	 */
	protected abstract void makeBrushImage();

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
	
	public BlendingMode getMode()
	{
		return this.mode;
	}
	
	public float getAlpha()
	{
		return alpha;
	}
}
