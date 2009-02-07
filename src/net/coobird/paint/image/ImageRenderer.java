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
	
	/**
	 * <p>
	 * Renders the contents of a {@link Canvas} object and returns a
	 * {@link BufferedImage} object, specifying whether or not to render a
	 * background layer which shows if any portions are transparent down to the
	 * deepest layer.
	 * </p>
	 * <p>
	 * Typically, a checkered background is drawn.
	 * </p>
	 * @param c					The {@code Canvas} object to render.
	 * @param drawBackground	Whether or not to render a background.
	 * @return					The rendered image.
	 */
	public BufferedImage render(Canvas c, boolean drawBackground);
}
