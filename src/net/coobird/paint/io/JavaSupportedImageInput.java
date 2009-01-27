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
	 * Determines whether the given {@link File} object is a supported image
	 * format by the {@code JavaSupportedImageInput} class.
	 * @param f				The file to check whether the format is supported.
	 */
	@Override
	public boolean supportsFile(File f)
	{
		//FIXME getReaderFileSuffixes is from Java 1.6
		String[] suffixes = ImageIO.getReaderFileSuffixes();
		
		//TODO check if this next line will fail if 
		
		for (String suffix : suffixes)
		{
			if (suffix.equals(getExtension(f)))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Gets the file extension of the given {@link File} object.
	 * @param f				The {@code File} object to determine the extension
	 * 						for.
	 * @return				The file extension.
	 */
	private String getExtension(File f)
	{
		int lastIndex = f.getName().lastIndexOf('.');
		
		if (lastIndex == -1)
		{
			return "";
		}
		
		return f.getName().substring(lastIndex);
	}
}
