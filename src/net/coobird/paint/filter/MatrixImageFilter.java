package net.coobird.paint.filter;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class MatrixImageFilter extends ImageFilter
{
	protected int width;
	protected int height;
	protected float[] matrix;
	
	/**
	 * Cannot instantiate by using the 0-argument constructor. 
	 */
	@SuppressWarnings("unused")
	private MatrixImageFilter() {}
	
	public MatrixImageFilter(int width, int height, float[] matrix)
	{
		this.width = width;
		this.height = height;
		this.matrix = matrix;
	}
	
	@Override
	public BufferedImage processImage(BufferedImage img)
	{
		BufferedImageOp op = new ConvolveOp(new Kernel(width, height, matrix));
		
		BufferedImage outImg = new BufferedImage(
				img.getWidth(),
				img.getHeight(),
				img.getType()
		);
		
		return op.filter(img, outImg);
	}
}
