package net.coobird.paint.driver;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.geom.*;

import javax.swing.*;

public class Concept
{
	private float alpha = 0.1f;
	private void makeGUI()
	{
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final BufferedImage brush = new BufferedImage(20,20,BufferedImage.TYPE_INT_ARGB);
		Graphics2D brushg = brush.createGraphics();
		brushg.setPaint(new GradientPaint(0,0,new Color(255,0,0,128),20,20,Color.orange));
		brushg.fillOval(0, 0, 20, 20);
		brushg.dispose();
		
		final BufferedImage back = new BufferedImage(200,200,BufferedImage.TYPE_INT_ARGB);
		Graphics2D gb = back.createGraphics();
		for (int i = 0; i < 200; i += 20)
		{
			for (int j = 0; j < 200; j += 20)
			{
				if ((i + j) % 40 == 0)
					gb.setPaint(Color.white);
				else
					gb.setPaint(Color.black);
				
				gb.fill(new Rectangle(i, j, 20, 20));
			}
		}
		gb.dispose();

		
		final BufferedImage bi = new BufferedImage(200,200,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bi.createGraphics();

		g.setPaint(new GradientPaint(
			0,
			0,
			new Color(0, 0, 255, 128),
			200,
			200,
			new Color(0, 255, 0, 255)
		));
		
		g.setClip(new Rectangle(50,0,200,200));
		g.fillOval(20, 20, 200, 200);
		g.setClip(null);
		
		for (int i = 20; i < 200; i+=10)
			g.drawImage(brush, i,i,null);
		
		g.setPaint(new Color(255,255,255,128));
		Composite curComposite = g.getComposite();
		g.setComposite(AlphaComposite.SrcIn);
		g.fillOval(20, 60, 100, 140);
		g.fillOval(30, 60, 110, 140);
		g.fillOval(40, 60, 120, 140);
		g.drawImage(brush, 150, 100, null);
		g.setComposite(curComposite);
		
		
		g.dispose();

		final JPanel c = new JPanel(){
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				if (bi != null)
				{
					BufferedImage img = new BufferedImage(back.getWidth(),
							back.getHeight(),
							BufferedImage.TYPE_INT_ARGB
					);
					Graphics2D g2 = img.createGraphics();
					g2.drawImage(back, 0,0,null);
					
					Composite curComposite = g2.getComposite();
					g2.setComposite(
							AlphaComposite.getInstance(
									AlphaComposite.SRC_OVER, alpha
							)
					);
					g2.drawImage(bi, 0,0,null);
					g2.setComposite(curComposite);

					g2.dispose();
					
					g.drawImage(img, 0,0,null);
				}
			}
		};
		c.addMouseListener(new MouseAdapter()
		{

			public void mousePressed(MouseEvent e)
			{
				super.mousePressed(e);
//				Graphics g = bi.getGraphics();
//				g.drawImage(brush, e.getX()-10, e.getY()-10, null);
//				g.dispose();
				Graphics2D g = bi.createGraphics();
				//g.setStroke(new Ellipse2D.Double(0,0,5,5));
				Ellipse2D el = new Ellipse2D.Double(0,0,50,50);
				g.draw(el);
				g.dispose();
				
				alpha += 0.1f;
				if (alpha > 1f)
					alpha = 0.1f;
				c.repaint();
			}
		});

		f.setSize(250,250);
		f.setLocation(200, 200);
		f.getContentPane().add(c);
		f.validate();
		f.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new Concept().makeGUI();
			}
		});
	}
	
	/*
	 * Find out how to implement Stroke
	 * esp. createStroke(Shape p)
	 * , where the p must intersect and return
	 * a Shape that is within the paramters of p
	 */
	
//	class MyStroke implements Stroke
//	{
//		
//	}
//
}
