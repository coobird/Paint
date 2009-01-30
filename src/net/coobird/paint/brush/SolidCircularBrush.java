package net.coobird.paint.brush;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class SolidCircularBrush extends Brush
{
	private int size;
	private Color brushColor;
	
	/**
	 * Cannot instantiate with default constructor.
	 */
	@SuppressWarnings("unused")
	private SolidCircularBrush() {}
	
	/**
	 * Creates an instance of CircularBrush with specified brush size.
	 * @param name	Identification for the new CircularBrush.
	 * @param size	The diameter of the CircularBrush.
	 */
	public SolidCircularBrush(String name, int size) 
	{
		this(name, size, Color.black);
	}

	/**
	 * Creates an instance of CircularBrush with specified brush size and color.
	 * @param name			Identification for the new CircularBrush.
	 * @param size			The diameter of the CircularBrush.
	 * @param brushColor	Color of the brush.
	 */
	public SolidCircularBrush(String name, int size, Color brushColor) 
	{
		if (size < 1)
		{
			String msg = "The size of brush must be at least 1 pixel.";
			throw new IllegalArgumentException(msg);
		}

		this.setName("Circular Brush (" + size + " px)");
	}

	/* (non-Javadoc)
	 * @see net.coobird.paint.brush.Brush#makeBrushImage()
	 */
	@Override
	protected void makeBrushImage()
	{
		brush = new BufferedImage(
				size,
				size,
				BufferedImage.TYPE_INT_ARGB
		);

		Graphics2D g = brush.createGraphics();
		g.setColor(brushColor);
		g.fillOval(0, 0, size, size);
		g.dispose();
		
		makeBrushThumbnail();
	}
}
