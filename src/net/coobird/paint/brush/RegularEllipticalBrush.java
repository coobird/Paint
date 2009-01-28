package net.coobird.paint.brush;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class RegularEllipticalBrush extends Brush
{
	private final static int DEFAULT_STEPS_DIVISOR = 2;

	private int size;
	private double angle;
	private int steps;
	private double squash;
	private Color brushColor;
	
	/**
	 * change class structure?
	 * 
	 * RegularBrush <- RegCircBrush, RegEllipBrush
	 * common;
	 * - size
	 * - color
	 * 
	 */
	
	public RegularEllipticalBrush()
	{
		
	}
	
	public RegularEllipticalBrush(String name, int size, double angle, double squash, Color brushColor)
	{
		this(null, size, size / DEFAULT_STEPS_DIVISOR, angle, squash, brushColor);
	}
	
	public RegularEllipticalBrush(
			String name,
			int size,
			int steps,
			double angle,
			double squash,
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
		this.squash = squash;
		this.brushColor = brushColor;
		
		makeBrushImage();
		makeBrushThumbnail();
	}
	
	private void makeBrushImage()
	{
		brush = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = brush.createGraphics();
		
		double alphaInc = brushColor.getAlpha() / (double)steps;
		double sizeInc = size / (double)steps;
		
		g.rotate(angle, size /2d, size /2d);
		
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
					center - (int)Math.round((sizeInc * i) / 2.0 * squash),
					(int)Math.round(sizeInc * i),
					(int)Math.round(sizeInc * i * squash)
			);
		}
		g.dispose();
	}

}
