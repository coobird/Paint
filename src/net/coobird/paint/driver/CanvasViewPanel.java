package net.coobird.paint.driver;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

import net.coobird.paint.image.Canvas;
import net.coobird.paint.image.PartialImageRenderer;

public class CanvasViewPanel extends JPanel
{
	private PartialImageRenderer renderer;
	private Canvas c;
	
	public CanvasViewPanel(PartialImageRenderer renderer, Canvas c)
	{
		this.renderer = renderer;
		this.c = c;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Rectangle r = getVisibleRect();
		
//		g.drawImage(renderer.render(ch.getCanvas()), 0, 0, null);
		
		int width = r.width;
		int height = r.height;
		double zoom = c.getZoom();
		
		g.drawImage(
				renderer.render(
						c,
						(int)(r.x / zoom),
						(int)(r.y / zoom),
						(int)(width / zoom),
						(int)(height / zoom)
				),
				r.x,
				r.y,
				null
		);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	@Override
	public Dimension getPreferredSize()
	{
		int width = (int)Math.round(c.getWidth() * c.getZoom());
		int height = (int)Math.round(c.getHeight() * c.getZoom());

		return new Dimension(width, height);
	}
}
