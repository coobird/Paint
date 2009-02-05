package net.coobird.paint.filter;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class TestImageFilter extends ImageFilter
{
	@Override
	public BufferedImage processImage(BufferedImage img)
	{
//		float[] matrix = new float[]{
//				0.0f,	0.0f,	0.0f,	0.0f,	0.0f,
//				0.0f,	0.0f,	-0.2f,	0.0f,	0.0f,
//				0.0f,	-0.2f,	1.0f,	-0.2f,	0.0f,
//				0.0f,	0.0f,	-0.2f,	0.0f,	0.0f,
//				0.0f,	0.0f,	0.0f,	0.0f,	0.0f
//		};
//		float[] matrix = new float[]{
//				0.0f,	0.0f,	0.0f,	0.0f,	0.0f,
//				0.0f,	0.1f,	0.1f,	0.1f,	0.0f,
//				0.0f,	0.1f,	0.2f,	0.1f,	0.0f,
//				0.0f,	0.1f,	0.1f,	0.1f,	0.0f,
//				0.0f,	0.0f,	0.0f,	0.0f,	0.0f
//		};
//		float[] matrix = new float[]{
//				0.0f,	0.0f,	0.0f,	0.0f,	0.0f,
//				0.0f,	0.0f,	0.0f,	0.0f,	0.0f,
//				0.5f,	0.0f,	0.5f,	0.0f,	0.0f,
//				0.0f,	0.0f,	0.0f,	0.0f,	0.0f,
//				0.0f,	0.0f,	0.0f,	0.0f,	0.0f
//		};
//		
//		BufferedImageOp op = new ConvolveOp(new Kernel(5, 5, matrix));
		float[] matrix = new float[]{
				0.1f,0.1f,0.1f,0.2f,0.5f,0.2f,0f,0f,0f
		};
		
		BufferedImageOp op = new ConvolveOp(new Kernel(9, 1, matrix));
		
		return op.filter(img, new BufferedImage(img.getWidth(), img.getHeight(), img.getType()));
	}
}
