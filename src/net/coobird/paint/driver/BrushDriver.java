package net.coobird.paint.driver;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.coobird.paint.brush.Brush;
import net.coobird.paint.brush.RegularCircularBrush;
import net.coobird.paint.brush.SolidCircularBrush;

public class BrushDriver
{
	public void makeGUI()
	{
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Brush rcb1 = new RegularCircularBrush(null, 80, new Color(0,0,255,32));
		Brush rcb2 = new SolidCircularBrush(null, 80, Color.red);
		
		final BufferedImage brush1 = rcb1.getBrush();
		final BufferedImage brush2 = rcb2.getBrush();
		
		final BufferedImage img = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
		Graphics ig = img.createGraphics();
		
		for (int i = 20; i < 100; i+=10)
			ig.drawImage(brush2, i, 20, null);	
		for (int i = 20; i < 100; i+=10)
			ig.drawImage(brush1, i, 60, null);	

		
		ig.dispose();
		
		final JPanel p = new JPanel() {
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.drawImage(img, 0, 0, null);
			}
		};
		
		MouseAdapter ma = new MouseAdapter()
		{
			Graphics2D g = img.createGraphics();
			
			public void mouseDragged(MouseEvent e)
			{
				g.drawImage(brush1, e.getX()- (brush1.getWidth()/2), e.getY()-(brush1.getHeight()/2), null);
//				if (e.getButton() == MouseEvent.BUTTON1)
//					g.drawImage(brush1, e.getX()- (brush1.getWidth()/2), e.getY()-(brush1.getHeight()/2), null);
//				else if (e.getButton() == MouseEvent.BUTTON3)
//					g.drawImage(brush2, e.getX()- (brush2.getWidth()/2), e.getY()-(brush2.getHeight()/2), null);
				
				p.repaint();
			}

			public void mousePressed(MouseEvent e)
			{
				//Graphics2D g = img.createGraphics();
				
				if (e.getButton() == MouseEvent.BUTTON1)
					g.drawImage(brush1, e.getX()- (brush1.getWidth()/2), e.getY()-(brush1.getHeight()/2), null);
				else if (e.getButton() == MouseEvent.BUTTON3)
					g.drawImage(brush2, e.getX()- (brush2.getWidth()/2), e.getY()-(brush2.getHeight()/2), null);
				
				//g.dispose();
				p.repaint();
			}
		};
		
		p.addMouseListener(ma);
		p.addMouseMotionListener(ma);
		
		
		f.setSize(450,450);
		f.setLocation(200, 100);
		f.getContentPane().add(p);
		f.validate();
		f.setVisible(true);

	}
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new BrushDriver().makeGUI();
			}
		});
	}
}
