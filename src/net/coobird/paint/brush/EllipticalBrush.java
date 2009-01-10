package net.coobird.paint.brush;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class EllipticalBrush extends Brush
{
	private double angle;
	private int size;
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
	
	public EllipticalBrush()
	{
		
	}
	
	private void makeBrushImage()
	{
		brush = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = brush.createGraphics();
		
		//TODO testing
		//int steps = size / 2;
		
//		double alphaInc = brushColor.getAlpha() / (double)steps;
//		double sizeInc = size / (double)steps;
//		
//		for (int i = steps; i > 0; i--)
//		{
//			g.setColor(
//					new Color(
//							brushColor.getRed(),
//							brushColor.getGreen(),
//							brushColor.getBlue(),
//							(int)Math.round(alphaInc * (steps - i))
//					)
//			);
//			
//			g.fillOval(
//					(int)Math.round(size / 2) - (int)Math.round((sizeInc * i) / 2),
//					(int)Math.round(size / 2) - (int)Math.round((sizeInc * i) / 2),
//					(int)Math.round(sizeInc * i),
//					(int)Math.round(sizeInc * i)
//			);
//		}
		g.dispose();
	}

}
