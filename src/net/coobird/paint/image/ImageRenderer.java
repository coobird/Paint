package net.coobird.paint.image;

import java.awt.image.BufferedImage;

/**
 * The {@code ImageRenderer} interface defines the methods used for rendering
 * the contents of a {@link Canvas} object and returning a rendered 
 * {@link BufferedImage} object.
 *  
 * @author coobird
 *
 */
public interface ImageRenderer
{
	/**
	 * Renders the contents of a {@link Canvas} object and returns a
	 * {@link BufferedImage} object.
	 * @param c				The {@code Canvas} object to render.
	 * @return				The rendered image.
	 */
	public BufferedImage render(Canvas c);
}
