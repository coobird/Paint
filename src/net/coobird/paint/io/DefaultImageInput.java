package net.coobird.paint.io;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;

import net.coobird.paint.image.Canvas;
import net.coobird.paint.image.ImageLayer;

public final class DefaultImageInput extends ImageInput
{
	/**
	 * 
	 * @param f		File to read.
	 */
	@Override
	public Canvas read(File f)
	{
		Canvas c = new Canvas(400,400);
		
		if (f == null)
		{
			throw new NullPointerException();
		}
		
		try
		{
			ZipFile zf = new ZipFile(f);
			Enumeration<? extends ZipEntry> entries = zf.entries();
			
			while (entries.hasMoreElements())
			{
				ZipEntry ze = entries.nextElement();
				InputStream is = zf.getInputStream(ze);
				
				// Copy image from img -> img2.
				// Without copy, program will crawl. I hate ImageIO.
				BufferedImage img = ImageIO.read(is);
//				BufferedImage img2 = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_ARGB);
//				Graphics2D g = img2.createGraphics();
//				g.drawImage(img, 0, 0, null);
//				img = null;
				c.addLayer(new ImageLayer(img));
				is.close();
			}
			zf.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return c;
	}
}
