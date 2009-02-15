package net.coobird.paint.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import net.coobird.paint.image.Canvas;
import net.coobird.paint.image.ImageLayer;

/**
 * 
 * @author coobird
 *
 */
public final class DefaultImageOutput extends ImageOutput
{
	static
	{
		addFilter(new ImageFileFilter(
				"Paint dot Jar Format (pjz)",
				new String[] {"pjz"}
				)
		);		
	}

	public DefaultImageOutput()
	{
		this("DefaultImageOutput Filter");
	}
	
	public DefaultImageOutput(String name)
	{
		super(name);
	}
	
	/**
	 * TODO Description.
	 * @param c		Canvas
	 * @param f		File.
	 */
	@Override
	public void write(Canvas c, File f)
	{
		if (c == null || f == null)
		{
			throw new NullPointerException();
		}
		
		try
		{
			FileOutputStream fos = new FileOutputStream(f);
			ZipOutputStream zos = new ZipOutputStream(fos);

			zos.putNextEntry(new ZipEntry("canvas.serialized"));
			new ObjectOutputStream(zos).writeObject(c);
			zos.closeEntry();
			
			int index = 0;
			
			for (ImageLayer l : c.getLayers())
			{
				zos.putNextEntry(new ZipEntry("" + index + ".serialized"));
				new ObjectOutputStream(zos).writeObject(l);
				zos.closeEntry();

				zos.putNextEntry(new ZipEntry("" + index + ".png"));
				ImageIO.write(l.getImage(), "png", zos);
				zos.closeEntry();
				
				index++;
			}
			
			zos.finish();
			zos.close();
			fos.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	public void write(Canvas c, File f, String format)
	{
		write(c, f);
	}

	/* (non-Javadoc)
	 * @see net.coobird.paint.io.ImageOutput#supportsFile(java.io.File)
	 */
	@Override
	public boolean supportsFile(File f)
	{
		if (ImageFileFilter.getExtension(f).toLowerCase().equals("pjz"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
