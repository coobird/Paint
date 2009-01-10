package net.coobird.paint.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import net.coobird.paint.image.Canvas;
import net.coobird.paint.image.ImageLayer;

public final class DefaultImageOutput extends ImageOutput
{
	/**
	 * TODO Description.
	 * @param c		Canvas
	 * @param f		File.
	 */
	@Override
	public void write(Canvas c, File f)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream("output.zip");
			ZipOutputStream zos = new ZipOutputStream(fos);
			
			for (ImageLayer l : c.getLayers())
			{
				zos.putNextEntry(new ZipEntry("" + System.currentTimeMillis()+".png"));
				ImageIO.write(l.getImage(), "png", zos);
				zos.closeEntry();
			}
			
			zos.finish();
			zos.close();
			fos.close();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
