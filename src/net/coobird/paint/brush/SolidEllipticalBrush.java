package net.coobird.paint.brush;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class SolidEllipticalBrush extends Brush
{
	private double angle;
	
	/** 
	 * The ratio between the semiminor axis and the semimajor axis of the
	 * ellipse. Must be in the range (0, 1].
	 */
	private double ratio;
	/**
	 * Cannot instantiate by the 0-argument constructor.
	 */
	@SuppressWarnings("unused")
	private SolidEllipticalBrush() {}
	
	/**
	 * 
	 * @param name
	 * @param size
	 * @param angle
	 * @param ratio
	 * @param brushColor
	 */
	public SolidEllipticalBrush(
			String name,
			int size,
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
			this.setDefaultName();
		}
		else
		{
			this.setName(name);
		}
		
		this.size = size;
		this.angle = angle;
		this.ratio = ratio;
		this.brushColor = brushColor;
		
		makeBrushImage();
	}
	
	protected void setDefaultName()
	{
		this.setName("Solid Elliptical Brush (" + size + ")");
	}
	
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
