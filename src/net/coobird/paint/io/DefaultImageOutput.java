package net.coobird.paint.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileFilter;

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
		addFilter(new FileFilter() {
			@Override
			public boolean accept(File f)
			{
				if (f.isDirectory() || f.getName().endsWith(".zip"))
				{
					return true;
				}
				else
				{
					return false;
				}
			}

			@Override
			public String getDescription()
			{
				return "Paint dot Jar Format (zip)";
			}
		});
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
			
			// Save info about Canvas size.
			ZipEntry info = new ZipEntry("data");
			info.setComment(c.getWidth() + "," + c.getHeight());
			zos.putNextEntry(info);
			
			int index = 0;
			
			for (ImageLayer l : c.getLayers())
			{
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
		/*
		 * Ignore format string.
		 */
		write(c, f);
	}

	/* (non-Javadoc)
	 * @see net.coobird.paint.io.ImageOutput#supportsFile(java.io.File)
	 */
	@Override
	public boolean supportsFile(File f)
	{
		String name = f.getName();
		
		if (name.endsWith(".zip"))
		{
			return true;
		}
		
		return false;
	}
}
