package net.coobird.paint.brush;

import java.awt.Color;

public class RegularCircularBrush extends RegularEllipticalBrush
{
	/**
	 * Creates an instance of RegularCircularBrush with specified brush size
	 * and color. Number of steps is determined by dividing the size of the
	 * brush by DEFAULT_STEPS_DIVISOR constant.
	 * @param name	Identification for the new RegularCircularBrush.
	 * @param size	The diameter of the RegularCircularBrush.
	 * @param brushColor	Color of the brush.
	 */
	public RegularCircularBrush(String name, int size, Color brushColor) 
	{
		this(name, size, size / DEFAULT_STEPS_DIVISOR, brushColor);
	}

	/**
	 * Creates an instance of RegularCircularBrush with specified brush size,
	 * color and number of steps in drawing the brush.
	 * @param name			Identification for the new RegularCircularBrush.
	 * @param size			The diameter of the RegularCircularBrush.
	 * @param brushColor	Color of the brush.
	 */
	public RegularCircularBrush(
			String name,
			int size,
			int steps,
			Color brushColor) 
	{
		super(name, size, steps, 0, 1, brushColor);

		if (name == null)
		{
			this.setName("Regular Circular Brush (" + size + " px)");
		}
		else
		{
			this.setName(name);
		}
	}
}
