package net.coobird.paint.brush;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class RandomSplashBrush extends Brush
{
	private int splashes;
	
	public RandomSplashBrush(
			String name,
			int size,
			int splashes,
			Color brushColor
	)
	{
		super(name, size, brushColor);
		this.alpha = 0.5f;
		this.splashes = splashes;

		makeBrushImage();
	}

	/**
	 * On each call, the brush is redrawn.
	 */
	@Override
	public BufferedImage getImage()
	{
		makeBrushImage();
		
		return super.getImage();
	}

	/**
	 * Generates the brush image.
	 */
	@Override
	protected void makeBrushImage()
	{
		brush = new BufferedImage(
				size,
				size,
				DEFAULT_BRUSH_TYPE
		);
		
		Graphics g = brush.createGraphics();
		
		Random r = new Random();

		for (int i = 0; i < splashes; i++)
		{
			int x = r.nextInt(size / 2) + (size / 4);
			int y = r.nextInt(size / 2) + (size / 4);
			int t = r.nextInt(size / 4);
			
			Color c = new Color(
					r.nextFloat(),
					r.nextFloat(),
					r.nextFloat(),
					r.nextFloat()
			);
			
			g.setColor(c);
			
			g.fillOval(x, y, t, t);
		}
		
		g.dispose();
		
		makeBrushThumbnail();
	}

	@Override
	protected void setDefaultName()
	{
		this.setName("Random Splash Brush");
	}
}