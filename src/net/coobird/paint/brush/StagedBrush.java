package net.coobird.paint.brush;

import java.awt.Color;
import java.awt.image.BufferedImage;

/*
 * Also, make a brush that gets smaller / fades with time, like a
 * real brush.
 */

public class StagedBrush extends RegularCircularBrush
{
	long lastTime = 0;
	int defaultSize = 0;

	@Override
	public BufferedImage getImage()
	{
		long timePast = System.currentTimeMillis() - lastTime;
		
		if (timePast < 100)
		{
			if (size < 100)
			{
				size = size + 1;
				makeBrushImage();
			}
		}
		else
		{
			if (size != defaultSize)
			{
				size = defaultSize;
				makeBrushImage();
			}
		}
		
		lastTime = System.currentTimeMillis();
		return super.getImage();
	}

	public StagedBrush(String name, int size, Color brushColor)
	{
		super(name, size, brushColor);
		defaultSize = size;
	}

}
