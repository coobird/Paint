package net.coobird.paint.io;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileFilter;

import net.coobird.paint.application.ApplicationUtils;
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
		
		if (ImageIO.getImageWritersByFormatName("png").hasNext())
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
		
		if (ImageIO.getImageWritersByFormatName("jpeg").hasNext())
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
		
		if (ImageIO.getImageWritersByFormatName("bmp").hasNext())
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
			BufferedImage img = ImageRendererFactory.getInstance().render(
					c,
					false
			);
			
			if (
					format.equals("jpeg") ||
					format.equals("jpg") ||
					format.equals("bmp")
			)
			{
				BufferedImage reducedImg = new BufferedImage(
						img.getWidth(),
						img.getHeight(),
						BufferedImage.TYPE_INT_RGB
				);
				
				Graphics2D g = reducedImg.createGraphics();
				g.setColor(Color.white);
				g.fillRect(0, 0, img.getWidth(), img.getHeight());
				g.drawImage(img, 0, 0, null);
				g.dispose();
				
				img = reducedImg;
			}
			
			ImageIO.write(img, format, f);
		}
		catch (IOException e)
		{
			ApplicationUtils.showExceptionMessage(e);
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
