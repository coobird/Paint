package net.coobird.paint.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.paint.image.Canvas;
import net.coobird.paint.image.ImageLayer;

/**
 * An image import filter for formats supported by the Java Image IO library.
 * @author coobird
 *
 */
public final class JavaSupportedImageInput extends ImageInput
{
	/**
	 * Reads a file form a Java supported format.
	 * @param f				The {@code File} object to read from.
	 */
	@Override
	public final Canvas read(File f)
	{
		BufferedImage img = null;
		Canvas c = null;
		
		try
		{
			img = ImageIO.read(f);
			
			ImageLayer layer = new ImageLayer(img);

			c = new Canvas(img.getWidth(), img.getHeight());
			c.addLayer(layer);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return c;
	}

	/**
	 * 
	 * @param f				The file to check whether the format is supported.
	 */
	@Override
	public boolean supportsFile(File f)
	{
		return false;
	}
}
