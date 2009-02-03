package net.coobird.paint.image;

import java.awt.image.BufferedImage;

public interface PartialImageRenderer extends ImageRenderer
{
	public BufferedImage render(
			Canvas c,
			int x,
			int y,
			int width,
			int height
	);
}
