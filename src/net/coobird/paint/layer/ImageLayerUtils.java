package net.coobird.paint.layer;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.coobird.paint.image.Canvas;
import net.coobird.paint.image.ImageRendererFactory;

/**
 * Utility class for {@link ImageLayer} processing.
 * @author coobird
 *
 */
public class ImageLayerUtils
{
	/**
	 * Merge the image contents of two {@link ImageLayer} objects.
	 * @param top			The top {@code ImageLayer}.
	 * @param bottom		The bottom {@code ImageLayer}.
	 * @return				A new {@code ImageLayer} object containing an image
	 * 						as a result of merging two {@code ImageLayer}s.
	 */
	public static ImageLayer mergeLayers(ImageLayer top, ImageLayer bottom)
	{
		Rectangle topRect = new Rectangle(
				top.getX(),
				top.getY(),
				top.getWidth(),
				top.getHeight()
		);
		Rectangle bottomRect = new Rectangle(
				bottom.getX(),
				bottom.getY(),
				bottom.getWidth(),
				bottom.getHeight()
		);
		
		topRect.add(bottomRect);
		
		Canvas c = new Canvas(topRect.width, topRect.height);
		c.addLayer(top);
		c.addLayer(bottom);
		
		BufferedImage img = ImageRendererFactory.getInstance().render(c, false);
		
		ImageLayer il = new ImageLayer(img);
		
		il.setLocation(top.getX(), top.getY());
		
		return il;
	}
}
