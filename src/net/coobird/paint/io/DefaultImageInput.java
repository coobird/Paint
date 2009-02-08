package net.coobird.paint.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileFilter;

import net.coobird.paint.image.Canvas;
import net.coobird.paint.image.ImageLayer;

public final class DefaultImageInput extends ImageInput
{
	{
		filterList = new ArrayList<FileFilter>();
		filterList.add(new FileFilter() {
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
	
	public DefaultImageInput()
	{
		this("DefaultImageInput Filter");
	}
	
	public DefaultImageInput(String name)
	{
		super(name);
	}
	
	/**
	 * 
	 * @param f		File to read.
	 */
	@Override
	public Canvas read(File f)
	{
		Canvas c = null;
		
		if (f == null)
		{
			throw new NullPointerException();
		}
		
		try
		{
			ZipFile zf = new ZipFile(f);
			Enumeration<? extends ZipEntry> entries = zf.entries();
			
			ZipEntry info = entries.nextElement();
			String size = info.getComment();
			String[] s = size.split(",");
			int width = Integer.parseInt(s[0]);
			int height = Integer.parseInt(s[1]);
			
			c = new Canvas(width, height);
			
			while (entries.hasMoreElements())
			{
				ZipEntry ze = entries.nextElement();
				InputStream is = zf.getInputStream(ze);
				
				BufferedImage img = ImageIO.read(is);
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

	/**
	 * 
	 * @param f				File to open.
	 */
	@Override
	public boolean supportsFile(File f)
	{
		return true;
	}
}
