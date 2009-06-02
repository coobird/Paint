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

/**
 * 
 * @author coobird
 *
 */
public final class DefaultImageInput extends ImageInput
{
	private boolean debug = true;
	
	static
	{
		addFilter(new ImageFileFilter(
				"Paint dot Jar Format (pjz)",
				new String[]{"pjz"}
				)
		);
	}
	
	/**
	 * Instantiates a {@code DefaultImageInput} filter.
	 */
	public DefaultImageInput()
	{
		this("DefaultImageInput Filter");
	}
	
	/**
	 * Instantiates a {@code DefaultImageInput} filter with a specified name.
	 * @param name
	 */
	public DefaultImageInput(String name)
	{
		super(name);
	}
	
	/**
	 * Reads a file supported by the {@code DefaultImageInput} filter and
	 * returns the contents of the image file as a {@code Canvas} object.
	 * @param f				File to read.
	 * @return				The {@code Canvas} object.
	 * 						If something goes wrong, the method will return
	 * 						{@code null}.

	 * 
	 * @throws NullPointerException		If {@code f} is {@code null}.
	 * @throws IOException				If an error occurs while reading in
	 * 									the serialzed data from a file.

	 * 
	 */
	@Override
	public Canvas read(File f)
		throws ImageInputOutputException
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
				
				if (debug)
				{
					System.out.println("begin reading " + entryName);
				}

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
				
				if (debug)
				{
					System.out.println("done reading " + entryName);
				}
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
			for (Map.Entry<String, BufferedImage> entry: imgMap.entrySet())
			{
				ImageLayer il = layerMap.get(entry.getKey());
				il.setImage(entry.getValue());
				c.addLayer(il);
			}
			
			zf.close();
		}
		catch (FileNotFoundException e)
		{
			throw new ImageInputOutputException(e);
			//e.printStackTrace();
		}
		catch (IOException e)
		{
			throw new ImageInputOutputException(e);
			//e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			throw new ImageInputOutputException(e);
			//e.printStackTrace();
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
