package net.coobird.paint.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;

import net.coobird.paint.image.Canvas;
import net.coobird.paint.image.ImageLayer;

public final class DefaultImageInput extends ImageInput
{
	static
	{
		addFilter(new ImageFileFilter(
				"Paint dot Jar Format (pjz)",
				new String[]{"pjz"}
				)
		);
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
			
			ZipEntry canvasZipEntry = entries.nextElement();
			
			if (!canvasZipEntry.getName().equals("canvas.serialized"))
			{
				throw new IOException(
						"Found: " + canvasZipEntry.getName() +
						" Expected: canvas.serialized"
				);
			}
			
			ObjectInputStream is = new ObjectInputStream(
					zf.getInputStream(canvasZipEntry)
			);
			
			c = (Canvas)is.readObject();
			
			Map<String, ImageLayer> layerMap =
				new TreeMap<String, ImageLayer>();
			
			Map<String, BufferedImage> imgMap =
				new TreeMap<String, BufferedImage>();
			
			/*
			 * Go through ZIP entries and place ImageLayer and BufferedImage
			 * into their respective Maps.
			 */
			while (entries.hasMoreElements())
			{
				ZipEntry ze = entries.nextElement();
				InputStream zis = zf.getInputStream(ze);
				String entryName = ze.getName();
				
				System.out.println("begin reading " + entryName);

				if (entryName.endsWith(".serialized"))
				{
					ImageLayer il;
					il = (ImageLayer)new ObjectInputStream(zis).readObject();
					
					int index = entryName.lastIndexOf(".serialized");
					String filename = entryName.substring(0, index);
					
					layerMap.put(filename, il);
				}
				else if (entryName.endsWith(".png"))
				{
					BufferedImage img = ImageIO.read(zis);

					int index = entryName.lastIndexOf(".png");
					String filename = entryName.substring(0, index);
					
					imgMap.put(filename, img);
				}
				
				System.out.println("done reading " + entryName);
			}
			
			/*
			 * Set up Canvas.
			 */
			/*
			 * Note:
			 * This section is dependent on the ordering of the keys in the map.
			 * This can be misordered and cause layers to be in the wrong order.
			 * Current implementation is a TreeMap - that will work as it is 
			 * sorted by natural order of the keys. However, use of other maps,
			 * such as HashMap can cause the order to be incorrect.
			 */
			for (String key : imgMap.keySet())
			{
				ImageLayer layer = layerMap.get(key);
				layer.setImage(imgMap.get(key));
				c.addLayer(layer);
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
		catch (ClassNotFoundException e)
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
