package net.coobird.paint.filter;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import net.coobird.paint.image.ImageLayer;

public class ResizeFilter extends ImageFilter
{
	private double scale;
	
	public ResizeFilter(String name, double scale)
	{
		super("Resize filter (" + scale + "x)");
		this.scale = scale;
	}

	@Override
	public BufferedImage processImage(BufferedImage img)
	{
		int nwidth = (int)Math.round(img.getWidth() * scale);
		int nheight = (int)Math.round(img.getHeight() * scale);
		
		BufferedImage newImg = new BufferedImage(
				nwidth,
				nheight,
				ImageLayer.getDefaultType()
		);
		
		Graphics2D g = newImg.createGraphics();
		g.scale(scale, scale);
		
		Map<RenderingHints.Key, Object> hints = 
			new HashMap<RenderingHints.Key, Object>();
		
		hints.put(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON
		);
		hints.put(
				RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY
		);
		hints.put(
				RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC
		);
		
		g.addRenderingHints(hints);
		
		g.drawImage(img, 0, 0, null);
		g.dispose();
		
		return newImg;
	}
}
