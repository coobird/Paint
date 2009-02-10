package net.coobird.paint.brush;

import java.awt.Color;

public class SolidCircularBrush extends SolidEllipticalBrush
{
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
		super(name, size, 0, 1, brushColor);
		
		if (size < 1)
		{
			String msg = "The size of brush must be at least 1 pixel.";
			throw new IllegalArgumentException(msg);
		}

		this.setDefaultName();
		
		makeBrushImage();
	}
	
	protected void setDefaultName()
	{
		this.setName("Solid Circular Brush (" + size + " px)");
	}
}
