package net.coobird.paint.brush;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * The {@code RegularEllipticalBrush} class represents a brush with a regular
 * elliptical shape.
 * 
 * @author coobird
 *
 */
public class RegularEllipticalBrush extends Brush
{
	/**
	 * The constant used to calculate the number of steps to use to render a
	 * brush from the size of the brush.
	 */
	protected final static int DEFAULT_STEPS_DIVISOR = 2;

	/** 
	 * The ratio between the semiminor axis and the semimajor axis of the
	 * ellipse. Must be in the range (0, 1].
	 */
	private double angle;
	
	/**
	 * Specifies the number of steps are required to draw the brush.
	 * This number corresponds to the number of concentric ellipses that are
	 * rendered to draw the brush.
	 */
	private int steps;
	
	/** 
	 * The ratio between the semiminor axis and the semimajor axis of the
	 * ellipse. Must be in the range (0, 1].
	 */
	private double ratio;

	/**
	 * Creates an instance of {@code RegularEllipticalBrush} with specified
	 * name, brush size, rotation angle and color in drawing the brush.
	 * 
	 * @param name			The name of the brush.
	 * @param size			The size of the brush, as the width. For a circular
	 * 						brush, this will correspond to the diameter.
	 * @param angle			The rotation angle of the brush, in radians.
	 * @param ratio			The ratio between the semiminor and semimajor axes
	 * 						of the elliptical brush. Must be in the range
	 * 						{@code 0d} and {@code 1d}. A round brush would have
	 * 						a value of {@code 1d}. 
	 * @param brushColor	The color of the brush.
	 */
	public RegularEllipticalBrush(
			String name,
			int size,
			double angle,
			double ratio,
			Color brushColor
	)
	{
		/*
		 * TODO Add information in the Javadoc about how the number of steps is
		 * being calculated. 
		 */
		
		this(
				null,
				size,
				size / DEFAULT_STEPS_DIVISOR,
				angle,
				ratio,
				brushColor
		);
	}
	
	/**
	 * Creates an instance of {@code RegularEllipticalBrush} with specified
	 * name, brush size, rotation angle, color and number of steps in drawing
	 * the brush.
	 * 
	 * @param name			The name of the brush.
	 * @param size			The size of the brush, as the width. For a circular
	 * 						brush, this will correspond to the diameter.
	 * @param angle			The rotation angle of the brush, in radians.
	 * @param steps			The number of steps (number of times a circle is
	 * 						drawn) in order to draw the brush.
	 * @param ratio			The ratio between the semiminor and semimajor axes
	 * 						of the elliptical brush. Must be in the range
	 * 						{@code 0d} and {@code 1d}. A round brush would have
	 * 						a value of {@code 1d}. 
	 * @param brushColor	The color of the brush.
	 */
	public RegularEllipticalBrush(
			String name,
			int size,
			int steps,
			double angle,
			double ratio,
			Color brushColor
	)
	{
		super(name, size, brushColor);
		
		this.steps = steps;
		this.angle = angle;
		this.ratio = ratio;
		
		makeBrushImage();
	}
	
	/**
	 * 
	 */
	@Override
	protected void setDefaultName()
	{
		this.setName("Regular Elliptical Brush (" + size + " px)");
	}
	
	/**
	 * Draws the brush image.
	 */
	@Override
	protected void makeBrushImage()
	{
		brush = new BufferedImage(size, size, DEFAULT_BRUSH_TYPE);
		Graphics2D g = brush.createGraphics();
		
		double alphaInc = brushColor.getAlpha() / (double)steps;
		double sizeInc = size / (double)steps;
		
		g.rotate(angle, size / 2.0, size / 2.0);
		
		int center = (int)Math.round(size / 2.0);
		
		for (int i = steps; i > 0; i--)
		{
			g.setColor(
					new Color(
							brushColor.getRed(),
							brushColor.getGreen(),
							brushColor.getBlue(),
							(int)Math.round(alphaInc * (steps - i))
					)
			);
			
			g.fillOval(
					center - (int)Math.round((sizeInc * i) / 2.0),
					center - (int)Math.round((sizeInc * i) / 2.0 * ratio),
					(int)Math.round(sizeInc * i),
					(int)Math.round(sizeInc * i * ratio)
			);
		}
		
		g.dispose();

		makeBrushThumbnail();
	}
}
