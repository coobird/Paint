package net.coobird.paint.image;

import java.awt.image.BufferedImage;

/**
 * <p>
 * The {@code PartialImageRenderer} interface is used to render a limited
 * section of a {@code Canvas} object.
 * </p>
 * <p>
 * An {@code ImageRenderer} which implements {@code PartialImageRenderer} is a
 * renderer which will be able to render a part of a {@link Canvas} object.
 * </p>
 * @author coobird
 *
 */
public interface PartialImageRenderer extends ImageRenderer
{
	/**
	 * Renders the contents of a {@link Canvas} object with the specified
	 * constraints.
	 * @param c				The {@code Canvas} object to render.
	 * @param x				The {@code x} coordinate of the top-left hand corner
	 * 						of the canvas to render.
	 * @param y				The {@code y} coordinate of the top-left hand corner
	 * 						of the canvas to render.
	 * @param width			The width of the section of the canvas to render.
	 * @param height		The height of the section of the canvas to render.
	 * @return				An image of the rendered canvas.
	 */
	public BufferedImage render(
			Canvas c,
			int x,
			int y,
			int width,
			int height
	);
	
	/**
	 * Renders the contents of a {@link Canvas} object with the specified
	 * constraints.
	 * @param c					The {@code Canvas} object to render.
	 * @param x					The {@code x} coordinate of the top-left hand
	 * 							corner of the canvas to render.
	 * @param y					The {@code y} coordinate of the top-left hand
	 * 							corner of the canvas to render.
	 * @param width				The width of the section of the canvas to
	 * 							render.
	 * @param height			The height of the section of the canvas to
	 * 							render.
	 * @param drawBackground	Whether or not to render a background.
	 * @return					An image of the rendered canvas.
	 */
	public BufferedImage render(
			Canvas c,
			int x,
			int y,
			int width,
			int height,
			boolean drawBackground
	);
}
