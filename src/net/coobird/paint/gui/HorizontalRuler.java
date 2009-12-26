package net.coobird.paint.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

public class HorizontalRuler extends Ruler implements PositionListener
{
	private int mouseX;
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Rectangle r = g.getClipBounds();
		g.clearRect(0, 0, r.width, r.height);
		
		int minX = (int)r.getMinX();
		int maxX = (int)r.getMaxX();
		
		int height = (int)r.getHeight();
		
		for (int i = minX; i < maxX; i += 10)
		{
			g.drawLine(i, 0, i, height);
		}
		
		g.setColor(Color.blue);
		g.drawLine(mouseX, 0, mouseX, height);
	}

	@Override
	public Dimension getPreferredSize()
	{
		int width = (int)this.getParent().getBounds().getWidth();
		int height = 10;
		
		return new Dimension(width, height);
	}

	@Override
	public void positionChanged(int x, int y)
	{
		mouseX = x;
		this.repaint();
	}
}
