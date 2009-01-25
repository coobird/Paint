package net.coobird.paint.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

public final class DefaultImageRenderer implements ImageRenderer
{
	private boolean drawBackground = true;
	
	/**
	 * 
	 */
	public DefaultImageRenderer()
	{
		
	}
	
	/**
	 * Renders the ImageLayers in the given Canvas to a single BufferedImage.
	 * @param c		Canvas to render.
	 */
	public BufferedImage render(Canvas c)
	{
//		List<ImageLayer> layerList = c.getLayers();
		List<ImageLayer> layerList = c.getRenderOrder();
		
		BufferedImage img = new BufferedImage(
				c.getWidth(),
				c.getHeight(),
				BufferedImage.TYPE_INT_ARGB
		);

		
		Graphics2D g = img.createGraphics();
		Composite originalComposite = g.getComposite();
		
		if (drawBackground)
		{
			drawBackground(img);
		}
		
		for (ImageLayer layer : layerList)
		{
			Composite layerComposite = AlphaComposite.getInstance(
					layer.getMode().getComposite().getRule(),
					layer.getAlpha()
			);
			
			g.setComposite(layerComposite);
			g.drawImage(layer.getImage(), layer.getX(), layer.getY(), null);
		}
		
		g.setComposite(originalComposite);
		g.dispose();
		
		return img;
	}
	
	private void drawBackground(BufferedImage img)
	{
		Graphics g = img.getGraphics();

		int width = img.getWidth();
		int height = img.getHeight();
		
		for (int i = 0; i < width; i += 20)
		{
			for (int j = 0; j < height; j += 20)
			{
				if ((i + j) % 40 == 0)
					g.setColor(Color.gray);
				else
					g.setColor(Color.white);
			
				g.fillRect(i, j, 20, 20);
			}
		}
		
		g.dispose();
	}
}
