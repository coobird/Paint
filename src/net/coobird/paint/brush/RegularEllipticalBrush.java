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
	protected final static int DEFAULT_STEPS_DIVISOR = 2;

	private int size;
	private double angle;
	private int steps;
	
	/** 
	 * The ratio between the semiminor axis and the semimajor axis of the
	 * ellipse. Must be in the range (0, 1].
	 */
	private double ratio;
	private Color brushColor;
	
	/**
	 * Cannot instantiate by the 0-argument constructor.
	 */
	@SuppressWarnings("unused")
	private RegularEllipticalBrush() {}
	
	public RegularEllipticalBrush(
			String name,
			int size,
			double angle,
			double ratio,
			Color brushColor
	)
	{
		this(null, size, size / DEFAULT_STEPS_DIVISOR, angle, ratio, brushColor);
	}
	
	public RegularEllipticalBrush(
			String name,
			int size,
			int steps,
			double angle,
			double ratio,
			Color brushColor
	)
	{
		if (size < 1)
		{
			String msg = "The size of brush must be at least 1 pixel.";
			throw new IllegalArgumentException(msg);
		}

		if (name == null)
		{
			this.setName("Regular Elliptical Brush (" + size + ")");
		}
		else
		{
			this.setName(name);
		}
		
		this.size = size;
		this.steps = steps;
		this.angle = angle;
		this.ratio = ratio;
		this.brushColor = brushColor;
		
		makeBrushImage();
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
