package net.coobird.paint.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

public class VerticalRuler extends Ruler implements PositionListener
{
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Rectangle r = g.getClipBounds();
		
		int minY = (int)r.getMinY();
		int maxY = (int)r.getMaxY();
		
		int width = (int)r.getWidth();
		
		g.clearRect((int)r.getMinX(), minY, (int)r.getMaxX(), maxY);
		for (int i = minY; i < maxY; i += 10)
		{
			g.drawLine(0, i, width, i);
		}
		
		g.setColor(Color.blue);
		g.drawLine(0, mouseY, width, mouseY);
	}

	/*
	 * cannot deal with behavior in scroll pane.
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	@Override
	public Dimension getPreferredSize()
	{
		int width = 10;
		int height = (int)this.getParent().getBounds().getHeight();
		
		System.out.println("new height: " + height);
		
		return new Dimension(width, height);
	}
	
	private int mouseY;

	@Override
	public void positionChanged(int x, int y)
	{
		mouseY = y;
		this.repaint();
	}
}
