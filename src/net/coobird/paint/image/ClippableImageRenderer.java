package net.coobird.paint.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * The {@code ClippableImageRenderer} class is an {@code PartialImageRenderer}
 * which supports rendering of a certain section of a {@link Canvas} object
 * in addition to rendering an entire {@code Canvas} object.
 * 
 * @author coobird
 *
 */
public final class ClippableImageRenderer 
	implements ImageRenderer, PartialImageRenderer
{
	/**
	 * Indicates whether or not to draw a checkered background.
	 */
	private boolean drawBackground = true;
	
	/**
	 * Render the canvas.
	 * @param c				The canvas to render.
	 */
	public BufferedImage render(Canvas c)
	{
		return render(c, 0, 0, c.getWidth(), c.getHeight());
	}

	/**
	 * Render the canvas.
	 * @param c					The canvas to render.
	 * @param drawBackground	Whether or not to render a background.
	 */
	public BufferedImage render(Canvas c, boolean drawBackground)
	{
		return render(c, 0, 0, c.getWidth(), c.getHeight());
	}
	

	/**
	 * 
	 */
	public BufferedImage render(
			Canvas c,
			int x,
			int y,
			int width,
			int height
	)
	{
		return render(c, x, y, width, height, true);
	}
	
	/**
	 * TODO write javadoc
	 * TODO implement this class completely and test.
	 * renders a part of an canvas
	 */
	public BufferedImage render(
			Canvas c,
			int x,
			int y,
			int width,
			int height,
			boolean drawBackground
	)
	{
		if (c == null)
		{
			throw new NullPointerException("Canvas not initialized.");
		}
		
		List<ImageLayer> layerList = c.getRenderOrder();
		
		// If the width or height is greater than the width or height of the
		// source image, set the rendering width/height to source width/height.
		if (width > c.getWidth())
		{
			width = c.getWidth();
		}
		if (height > c.getHeight())
		{
			height = c.getHeight();
		}
		
		BufferedImage img = new BufferedImage(
				width,
				height,
				ImageLayer.getDefaultType()
		);
		
		Graphics2D g = img.createGraphics();
		
		Composite originalComposite = g.getComposite();
		
		if (drawBackground)
		{
			drawBackground(img);
		}
		
		for (ImageLayer layer : layerList)
		{
			if (!layer.isVisible())
			{
				continue;
			}
			
			Composite layerComposite = AlphaComposite.getInstance(
					layer.getMode().getComposite().getRule(),
					layer.getAlpha()
			);
			
			g.setComposite(layerComposite);
			BufferedImage bi = layer.getImage().getSubimage(x, y, width, height);
			g.drawImage(bi, layer.getX(), layer.getY(), null);
		}
		
		g.setComposite(originalComposite);
		g.dispose();
		
		return img;
	}
	
	/**
	 * Draws a checkered white and gray background.
	 * @param img			The {@code BufferedImage}Å@to draw the background on.
	 */
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
