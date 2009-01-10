package net.coobird.paint.driver;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.coobird.paint.brush.BrushFilter;
import net.coobird.paint.brush.RegularCircularBrush;
import net.coobird.paint.image.Canvas;
import net.coobird.paint.image.ImageLayer;
import net.coobird.paint.image.ImageRendererFactory;
import net.coobird.paint.io.DefaultImageOutput;

public class CanvasDriver
{

	public void makeGUI()
	{
		JFrame f = new JFrame("CanvasDriver");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ImageLayer layer = new ImageLayer(200, 200);
		layer.setCaption("New Layer");
		layer.setAlpha(1.0f);
		layer.setVisible(true);
		
		Graphics g = layer.getImage().createGraphics();
		g.setColor(Color.black);
		g.setFont(new Font("Serif", Font.PLAIN, 24));
		g.drawString("helo",100,100);
		g.dispose();

		ImageLayer layer2 = new ImageLayer(200, 200);
		layer2.setCaption("New Layer2");
		layer2.setAlpha(0.8f);
		layer2.setVisible(true);
		
		Graphics g2 = layer2.getImage().createGraphics();
		g2.setColor(Color.blue);
		g2.fillRect(50, 50, 70, 70);
		g2.dispose();
		
		
		Canvas c = new Canvas(200, 200);
		c.addLayer(layer);
		c.addLayer(layer2);
		
		BrushFilter bf = new BrushFilter(new RegularCircularBrush("new brush", 20, Color.black));
		
		for (int i = 0; i < 100; i+=2)
			bf.drawBrush(layer2, i, i);
		
		final BufferedImage img = ImageRendererFactory.getInstance().render(c);
		
		JPanel p = new JPanel() {
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
//				g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
				g.drawImage(img, 0, 0, null);
			}
		};
		
		f.getContentPane().add(p);
		
		f.setSize(200, 200);
		f.setLocation(200, 200);
		f.validate();
		f.setVisible(true);
		
		new DefaultImageOutput().write(c, new File(""));
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				new CanvasDriver().makeGUI();
			}			
		});

	}

}
