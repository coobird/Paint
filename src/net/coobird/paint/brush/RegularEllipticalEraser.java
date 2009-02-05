package net.coobird.paint.brush;

import java.awt.Color;
import java.awt.Graphics;

import net.coobird.paint.BlendingMode;

public class RegularEllipticalEraser extends RegularEllipticalBrush
{
	public RegularEllipticalEraser(
			String name,
			int size,
			double angle,
			double ratio,
			float alpha
	)
	{
		super(name, size, angle, ratio, Color.black);
		this.mode = BlendingMode.BRUSH_ERASER;
		this.setName("Regular Elliptical Eraser (" + size +")");
		this.alpha = alpha;
	}

	/* (non-Javadoc)
	 * @see net.coobird.paint.brush.Brush#makeBrushThumbnail()
	 */
	@Override
	protected void makeBrushThumbnail()
	{
		super.makeBrushThumbnail();
		Graphics g = thumbBrush.getGraphics();
		int width = g.getFontMetrics().stringWidth("E");
		int height = g.getFontMetrics().getHeight();
		int cx = (thumbBrush.getWidth() / 2) - (width / 2);
		int cy = (thumbBrush.getHeight() / 2) + (height / 4);
		
		g.setColor(Color.white);
		g.drawString("E", cx, cy);
		
		g.dispose();
	}
}
