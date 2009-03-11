package net.coobird.paint.brush;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * The {@code SolidEllipticalBrush} class represents a solid elliptical brush.
 * @author coobird
 *
 */
public class SolidEllipticalBrush extends Brush
{
	/**
	 * The rotation angle of the brush, in radians.
	 */
	private double angle;
	
	/** 
	 * The ratio between the semiminor axis and the semimajor axis of the
	 * ellipse. Must be in the range (0, 1].
	 */
	private double ratio;
	
	/**
	 * Instantiates a {@code SolidEllipticalBrush} object with a specified
	 * name, size, angle, brush size ratio, and brush color.
	 * @param name			The name of the brush.
	 * @param size			The size of the brush, as the width. For a circular
	 * 						brush, this will correspond to the diameter.
	 * @param angle			The rotation angle of the brush, in radians.
	 * @param ratio			The ratio between the semiminor and semimajor axes
	 * 						of the elliptical brush. Must be in the range
	 * 						{@code 0d} and {@code 1d}. 
	 * @param brushColor	The color of the brush.
	 */
	public SolidEllipticalBrush(
			String name,
			int size,
			double angle,
			double ratio,
			Color brushColor
	)
	{
		super(name, size, brushColor);
		
		this.angle = angle;
		this.ratio = ratio;
		
		makeBrushImage();
	}
	
	/**
	 * Sets the default name for the instance of {@code SolidEllipticalBrush}.
	 */
	@Override
	protected void setDefaultName()
	{
		this.setName("Solid Elliptical Brush (" + size + ")");
	}
	
	/**
	 * Generates the brush image.
	 */
	@Override
	protected void makeBrushImage()
	{
		brush = new BufferedImage(size, size, DEFAULT_BRUSH_TYPE);
		
		Graphics2D g = brush.createGraphics();
		g.rotate(angle, size / 2.0, size / 2.0);
		g.setColor(brushColor);
		
		int center = (int)Math.round(size / 2.0);
		
		g.fillOval(
				center - (int)Math.round(size / 2.0),
				center - (int)Math.round(size / 2.0 * ratio),
				size,
				(int)Math.round(size * ratio)
		);
		
		g.dispose();

		makeBrushThumbnail();
	}
}
