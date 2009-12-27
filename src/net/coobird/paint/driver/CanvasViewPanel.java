package net.coobird.paint.driver;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import net.coobird.paint.gui.PositionListener;
import net.coobird.paint.image.Canvas;
import net.coobird.paint.image.PartialImageRenderer;

public class CanvasViewPanel extends JPanel implements MouseMotionListener
{
	private static final long serialVersionUID = -3102666235735470779L;
	
	private PartialImageRenderer renderer;
	private Canvas c;
	
	/*
	 * variables used to locate the position of where to draw the image.
	 */
	private int x;
	private int y;
	
	public CanvasViewPanel(PartialImageRenderer renderer, Canvas c)
	{
		this.renderer = renderer;
		this.c = c;
		this.addMouseMotionListener(this);
	}
	
	public void setCanvas(Canvas c)
	{
		this.c = c;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		/*
		 * Centers the image.
		 * 
		 */
		
		/*
		 * Possibly clean up.
		 */
		Rectangle r = this.getVisibleRect();
		
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
		
		x = visibleX;
		y = visibleY;
		
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
	
	@Override
	public Dimension getPreferredSize()
	{
		int width = (int)Math.round(c.getWidth() * c.getZoom());
		int height = (int)Math.round(c.getHeight() * c.getZoom());

		return new Dimension(width, height);
	}

	@Override
	public void mouseDragged(MouseEvent e) {}
	
	private List<PositionListener> positionListeners = 
		new ArrayList<PositionListener>();
	
	public void addPositionListener(PositionListener listener)
	{
		positionListeners.add(listener);
	}
	
	public void removePositionListener(PositionListener listener)
	{
		positionListeners.remove(listener);
	}
	
	private void notifyPositionListeners(int x, int y)
	{
		for (PositionListener listener : positionListeners)
		{
			listener.positionChanged(x, y);
		}
	}
	

	@Override
	public void mouseMoved(MouseEvent e)
	{
		//int xOnImage = e.getX() - x;
		//int yOnImage = e.getY() - y;
		//notifyPositionListeners(xOnImage, yOnImage);
		
		notifyPositionListeners(e.getX(), e.getY());
	}
	
//	public Point getPositionOnImage()
//	{
//		return new Point(xOnImage, yOnImage);
//	}
}
