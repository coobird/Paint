package net.coobird.paint.brush;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class RandomSplashBrush extends Brush
{
	public RandomSplashBrush()
	{
		this.size = 400;
		this.brushColor = Color.black;
		this.alpha = 0.5f;
		this.setDefaultName();
		
		makeBrushImage();
	}
	
	

	@Override
	public BufferedImage getImage()
	{
		makeBrushImage();
		
		return super.getImage();
	}



	@Override
	protected void makeBrushImage()
	{
		brush = new BufferedImage(
				size,
				size,
				DEFAULT_BRUSH_TYPE
		);
		
		Graphics g = brush.createGraphics();
		
		for (int i = 0; i < 100; i++)
		{
			Random r = new Random();
			int x = r.nextInt(size/2)+(size/4);
			int y = r.nextInt(size/2)+(size/4);
			int t = r.nextInt(size/4);
			
			Color c = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat(), r.nextFloat());
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