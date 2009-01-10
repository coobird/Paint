package net.coobird.paint.brush;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class RegularCircularBrush extends Brush
{
	private int steps;
	private final static int DEFAULT_STEPS_DIVISOR = 2;
	
	
	// TODO Reason not to store size of brush????
	
	/**
	 * Cannot instantiate with default constructor.
	 */
	@SuppressWarnings("unused")
	private RegularCircularBrush() {}
	
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
		if (size < 1)
		{
			String msg = "The size of brush must be at least 1 pixel.";
			throw new IllegalArgumentException(msg);
		}

		if (name == null)
		{
			this.setName("Regular Circular Brush (" + size + " px)");
		}
		else
		{
			this.setName(name);
		}
		
		this.steps = steps;
		
		makeBrushImage(size, brushColor);
		makeBrushThumbnail();
	}
	
	/**
	 * 
	 * @param size
	 * @param brushColor
	 */
	private void makeBrushImage(int size, Color brushColor)
	{
		brush = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = brush.createGraphics();
		
		//TODO testing
		//int steps = size / 2;
		
		double alphaInc = brushColor.getAlpha() / (double)steps;
		double sizeInc = size / (double)steps;
		
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
					(int)Math.round(size / 2) - (int)Math.round((sizeInc * i) / 2),
					(int)Math.round(size / 2) - (int)Math.round((sizeInc * i) / 2),
					(int)Math.round(sizeInc * i),
					(int)Math.round(sizeInc * i)
			);
		}
		g.dispose();
	}
	
	/**
	 * 
	 * @param size
	 * @param brushColor
	 */
	private void makeBrushThumbnail()
	{
		int size = Brush.THUMB_SIZE;
	
		thumbBrush = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);

		Graphics g = thumbBrush.createGraphics();
		g.drawImage(brush, 0, 0, size, size, null);
		g.dispose();
	}

}
