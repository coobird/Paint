package net.coobird.paint.driver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import net.coobird.paint.application.BrushListCellRenderer;
import net.coobird.paint.application.ImageLayerListCellRenderer;
import net.coobird.paint.brush.Brush;
import net.coobird.paint.brush.RegularCircularBrush;
import net.coobird.paint.brush.SolidCircularBrush;

public class BrushDriver
{
	public void makeGUI()
	{
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().setLayout(new BorderLayout());
		
		Brush rcb1 = new RegularCircularBrush(null, 80, new Color(0,0,255,32));
		Brush rcb2 = new SolidCircularBrush(null, 80, Color.red);
		
		final BufferedImage brush1 = rcb1.getBrush();
		final BufferedImage brush2 = rcb2.getBrush();
		
		final JList list = new JList();
		list.setCellRenderer(new BrushListCellRenderer());
		final DefaultListModel listModel = new DefaultListModel();
		listModel.addElement(rcb1);
		listModel.addElement(rcb2);
		listModel.addElement(new RegularCircularBrush(null, 40, new Color(0,0,0,32)));
		list.setModel(listModel);
		final JScrollPane listSp = new JScrollPane(list);
		f.getContentPane().add(listSp, BorderLayout.EAST);
		
		
		final BufferedImage img = new BufferedImage(
				600,
				600,
				BufferedImage.TYPE_INT_ARGB
		);
		
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
			BufferedImage b;
			
			public void mouseDragged(MouseEvent e)
			{
				g.drawImage(
						b,
						e.getX()- (b.getWidth() / 2),
						e.getY()-(b.getHeight() / 2),
						null
				);
				
				p.repaint();
			}

			public void mousePressed(MouseEvent e)
			{
				b = ((Brush)list.getSelectedValue()).getBrush();
				
				if (e.getButton() == MouseEvent.BUTTON1)
				{
					g.drawImage(
							b,
							e.getX()- (b.getWidth() / 2),
							e.getY()-(b.getHeight() / 2),
							null
					);
				}
				
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
