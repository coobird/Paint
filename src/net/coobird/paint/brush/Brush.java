package net.coobird.paint.brush;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.coobird.paint.BlendingMode;

public abstract class Brush
{
	protected BufferedImage brush;
	protected BufferedImage thumbBrush;
	protected String name;
	protected BlendingMode mode = BlendingMode.BRUSH_NORMAL;
	protected float alpha = 1f;
	protected Color brushColor;
	protected int size;
	
	public static final int THUMB_SIZE = 20;
	protected static final int DEFAULT_BRUSH_TYPE = BufferedImage.TYPE_INT_ARGB;
	
	
	/**
	 * Sets the default name
	 * @return
	 */
	protected abstract void setDefaultName();

	/**
	 * 
	 */
	protected abstract void makeBrushImage();

	/**
	 * 
	 * @param brushColor
	 */
	protected void makeBrushThumbnail()
	{
		int size = Brush.THUMB_SIZE;
	
		thumbBrush = new BufferedImage(size, size, DEFAULT_BRUSH_TYPE);
	
		Graphics g = thumbBrush.createGraphics();
		g.drawImage(brush, 0, 0, size, size, null);
		g.dispose();
	}

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
	 * Returns a copy of the brush.
	 * Returns null if brush is not initialized.
	 * @return
	 */
	public BufferedImage getImage()
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
	public BufferedImage getThumbImage()
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
	 * Returns the blending mode for the {@code Brush} object.
	 * @return				The brush mode.
	 */
	public BlendingMode getMode()
	{
		return this.mode;
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
	 * Returns the opacity of the brush.
	 * @return				The opacity of the brush as a {@code float} value
	 * 						between {@code 0f} and {@code 1f}, where {@code 0f}
	 * 						represents transparent, and {@code 1f} represents
	 * 						opaque.
	 */
	public float getAlpha()
	{
		return alpha;
	}

	/**
	 * Sets the opacity of the brush.
	 * @param alpha			The opacity of the brush as a {@code float} value
	 * 						between {@code 0f} and {@code 1f}, where {@code 0f}
	 * 						represents transparent, and {@code 1f} represents
	 * 						opaque.
	 */
	public void setAlpha(float alpha)
	{
		this.alpha = alpha;
	}

	/**
	 * Gets the color of the brush.
	 * @return 				The {@code Color} of the brush.
	 */
	public Color getColor()
	{
		return brushColor;
	}

	/**
	 * Sets the color of the brush.
	 * @param c				The {@code Color} of the brush.
	 */
	public void setColor(Color c)
	{
		this.brushColor = c;
		makeBrushImage();
		setDefaultName();
	}

	/**
	 * @return the size
	 */
	public int getSize()
	{
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size)
	{
		this.size = size;
		makeBrushImage();
		setDefaultName();
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
}
