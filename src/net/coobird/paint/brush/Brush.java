package net.coobird.paint.brush;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.coobird.paint.BlendingMode;

/**
 * 
 * @author coobird
 *
 */
public abstract class Brush
{
	protected BufferedImage brush;
	protected BufferedImage thumbBrush;
	protected String name;
	protected BlendingMode.Brush mode = BlendingMode.Brush.NORMAL;
	protected float alpha = 1f;
	protected Color brushColor;
	protected int size;
	
	protected static final int THUMB_SIZE = 32;
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
	 * Creates a thumbnail of the Brush.
	 * @throws IllegalStateException is thrown when the brush has not
	 * been initialized.
	 */
	protected void makeBrushThumbnail()
	{
		if (brush == null)
		{
			throw new IllegalStateException("Brush not initialized.");
		}

		int thumbSize = THUMB_SIZE;
	
		thumbBrush = new BufferedImage(
				thumbSize,
				thumbSize,
				DEFAULT_BRUSH_TYPE
		);
	
		Graphics g = thumbBrush.createGraphics();
		
		if (size > thumbSize)
		{
			g.drawImage(brush, 0, 0, thumbSize, thumbSize, null);
		}
		else
		{
			int center = (thumbSize - size) / 2;
			g.drawImage(brush, center, center, size, size, null);
		}
		
		g.dispose();
	}
	
	/**
	 * Returns a copy of the provided instance of {@link BufferedImage}.
	 * @param img
	 * @return
	 */
	private static BufferedImage copyImage(BufferedImage img)
	{
		if (img == null)
		{
			return null;
		}

		BufferedImage copyImg = new BufferedImage(
				img.getWidth(),
				img.getHeight(),
				img.getType()
		);
		
		Graphics2D g = copyImg.createGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();
		
		return copyImg;
	}

	/**
	 * Returns a copy of the brush.
	 * @return {@code null} if the brush is not initialized.
	 */
	public BufferedImage getImage()
	{
		return copyImage(brush);
	}

	/**
	 * Returns a copy of the thumbnail of the brush.
	 * @return {@code null} if the brush is not initialized.
	 */
	public BufferedImage getThumbImage()
	{
		return copyImage(thumbBrush);
	}

	/**
	 * Returns the blending mode for the {@code Brush} object.
	 * @return				The brush mode.
	 */
	public BlendingMode.Brush getMode()
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
