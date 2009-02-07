package net.coobird.paint.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileFilter;

import net.coobird.paint.image.Canvas;
import net.coobird.paint.image.ImageRendererFactory;

/**
 * 
 * @author coobird
 *
 */
public final class JavaSupportedImageOutput extends ImageOutput
{
	static
	{
		filterList = new ArrayList<FileFilter>();
		
		if (ImageIO.getImageWritersByFormatName("png") != null)
		{
			filterList.add(new FileFilter() {
				@Override
				public boolean accept(File f)
				{
					if (
							f.isDirectory() ||
							getExtension(f).toLowerCase().equals("png")
					)
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
					return "Portable Network Graphics (png)";
				}
			});
		}
		
		if (ImageIO.getImageWritersByFormatName("jpeg") != null)
		{
			filterList.add(new FileFilter() {
				@Override
				public boolean accept(File f)
				{
					if (
							f.isDirectory() ||
							getExtension(f).toLowerCase().equals("jpg") ||
							getExtension(f).toLowerCase().equals("jpeg")
					)
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
					return "JPEG Image (jpeg, jpg)";
				}
			});
		}
		
		if (ImageIO.getImageWritersByFormatName("bmp") != null)
		{
			filterList.add(new FileFilter() {
				@Override
				public boolean accept(File f)
				{
					if (
							f.isDirectory() ||
							getExtension(f).toLowerCase().equals("bmp")
					)
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
					return "Bitmap Image (bmp)";
				}
			});
		}
	}
	
	@Override
	public void write(Canvas c, File f)
	{
		/*
		 * Determine format string from file extension.
		 */
		write(c, f, getExtension(f));
	}
	
	@Override
	public void write(Canvas c, File f, String format)
	{
		/*
		 * Note: saving as JPEG causes image to look over saturated.
		 * TODO investigate cause and find mitigation measures.
		 */
		try
		{
			ImageIO.write(
					ImageRendererFactory.getInstance().render(c),
					format,
					f
			);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see net.coobird.paint.io.ImageOutput#supportsFile(java.io.File)
	 */
	@Override
	public boolean supportsFile(File f)
	{
		//FIXME getWriterFileSuffixes is from Java 1.6
		String[] suffixes = ImageIO.getWriterFileSuffixes();
		
		//TODO check if this next line will fail if 
		
		for (String suffix : suffixes)
		{
			if (suffix.equals(getExtension(f)))
			{
				System.out.println(suffix);
				return true;
			}
		}
		
		return false;
	}
}
