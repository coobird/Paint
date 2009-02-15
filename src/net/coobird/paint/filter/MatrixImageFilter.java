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
	
	public MatrixImageFilter(int width, int height, float[] matrix)
	{
		this("MatrixImageFilter", width, height, matrix);
	}

	public MatrixImageFilter(String name, int width, int height, float[] matrix)
	{
		super(name);
		this.width = width;
		this.height = height;
		this.matrix = matrix.clone();
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
