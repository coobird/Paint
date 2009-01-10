package net.coobird.paint.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.paint.image.Canvas;
import net.coobird.paint.image.ImageLayer;

public class JavaSupportedImageInput extends ImageInput
{

	@Override
	public Canvas read(File f)
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return c;
	}
	

}
