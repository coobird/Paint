package net.coobird.paint.brush;

import java.awt.Color;

/**
 * The {@code SolidCircularBrush} class represents a solid circular brush.
 * 
 * This class is a subclass of {@code SolidEllipticalBrush} where the rotation
 * angle and the ratio of the ellipse are both set to zero.
 * @author coobird
 *
 */
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
		
		makeBrushImage();
	}
	
	protected void setDefaultName()
	{
		this.setName("Solid Circular Brush (" + size + " px)");
	}
}
