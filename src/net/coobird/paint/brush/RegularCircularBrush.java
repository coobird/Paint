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
public class RegularCircularBrush extends RegularEllipticalBrush
{
	/**
	 * Creates an instance of {@code RegularCircularBrush} with specified brush
	 * size and color. Number of steps is determined by dividing the size of the
	 * brush by {@code DEFAULT_STEPS_DIVISOR} constant.
	 * @param name			Identification for the new
							{@code RegularCircularBrush} object.
	 * @param size			The diameter of the {@code RegularCircularBrush}.
	 * @param brushColor	The color of the brush.
	 */
	public RegularCircularBrush(
			String name,
			int size,
			Color brushColor
	) 
	{
		this(name, size, size / DEFAULT_STEPS_DIVISOR, brushColor);
	}

	/**
	 * Creates an instance of {@code RegularCircularBrush} with specified brush
	 * size, color and number of steps in drawing the brush.
	 * @param name			Identification for the new
							{@code RegularCircularBrush} object.
	 * @param size			The diameter of the {@code RegularCircularBrush}.
	 * @param steps			The number of steps (number of times a circle is
	 * 						drawn) in order to draw the brush.
	 * @param brushColor	The color of the brush.
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
			this.setDefaultName();
		}
		else
		{
			this.setName(name);
		}
	}
	
	/**
	 * 
	 */
	@Override
	protected void setDefaultName()
	{
		this.setName("Regular Circular Brush (" + size + " px)");
	}
}
