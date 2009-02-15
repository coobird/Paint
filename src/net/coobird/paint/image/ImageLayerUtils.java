package net.coobird.paint.image;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class ImageLayerUtils
{
	public static ImageLayer mergeLayers(ImageLayer top, ImageLayer bottom)
	{
		Rectangle topRect = new Rectangle(
				top.getX(),
				top.getY(),
				top.getWidth(),
				top.getHeight()
		);
		Rectangle bottomRect = new Rectangle(
				bottom.getX(),
				bottom.getY(),
				bottom.getWidth(),
				bottom.getHeight()
		);
		
		topRect.add(bottomRect);
		
		Canvas c = new Canvas(topRect.width, topRect.height);
		c.addLayer(top);
		c.addLayer(bottom);
		
		BufferedImage img = ImageRendererFactory.getInstance().render(c, false);
		
		ImageLayer il = new ImageLayer(img);
		
		// Set the imagelayer properties
		il.setLocation(top.getX(), top.getY());
		
		return il;
	}
}
