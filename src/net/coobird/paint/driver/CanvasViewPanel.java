package net.coobird.paint.driver;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

import net.coobird.paint.image.Canvas;
import net.coobird.paint.image.PartialImageRenderer;

public class CanvasViewPanel extends JPanel
{
	private static final long serialVersionUID = -3102666235735470779L;
	
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
		
		/*
		 * Possibly clean up.
		 */
		Rectangle r = getVisibleRect();
		
		int visibleX = r.x;
		int visibleY = r.y;
		int visibleWidth = r.width;
		int visibleHeight = r.height;

		double zoom = c.getZoom();
		
		int viewX = (int)(visibleX / zoom);
		int viewY = (int)(visibleY / zoom);
		int viewWidth = (int)(visibleWidth / zoom);
		int viewHeight = (int)(visibleHeight / zoom);
		
		int canvasWidth = (int)(c.getWidth() * zoom);
		int canvasHeight = (int)(c.getHeight() * zoom);
		
		int x = visibleX;
		int y = visibleY;
		
		if (visibleWidth > canvasWidth)
		{
			x = (visibleWidth / 2) - (canvasWidth / 2);
		}
		if (visibleHeight > canvasHeight)
		{
			y = (visibleHeight / 2) - (canvasHeight / 2);
		}
		
		g.drawImage(
				renderer.render(c, viewX, viewY, viewWidth, viewHeight),
				x,
				y,
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
