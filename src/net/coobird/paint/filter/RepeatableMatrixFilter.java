package net.coobird.paint.filter;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class RepeatableMatrixFilter extends MatrixImageFilter
{
	protected int repeat;
	
	public RepeatableMatrixFilter(int width, int height, float[] matrix)
	{
		this(width, height, 1, matrix);
	}
	
	public RepeatableMatrixFilter(int width, int height, int repeat, float[] matrix)
	{
		super(width, height, matrix);
		this.repeat = repeat;		
	}

	/* (non-Javadoc)
	 * @see net.coobird.paint.filter.MatrixImageFilter#processImage(java.awt.image.BufferedImage)
	 */
	@Override
	public BufferedImage processImage(BufferedImage img)
	{
		BufferedImageOp op = new ConvolveOp(new Kernel(width, height, matrix));
		
		BufferedImage outImg = null;
		
		for (int i = 0; i < repeat; i++)
		{
			outImg = new BufferedImage(
					img.getWidth(),
					img.getHeight(),
					img.getType()
			);

			outImg = op.filter(img, outImg);
			
			img = outImg;
		}
				
		return outImg;
	}
}
