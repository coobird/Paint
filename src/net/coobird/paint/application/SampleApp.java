package net.coobird.paint.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.coobird.paint.image.Canvas;
import net.coobird.paint.image.ImageLayer;
import net.coobird.paint.image.ImageRenderer;
import net.coobird.paint.image.ImageRendererFactory;

public class SampleApp
{
	public void makeGUI()
	{
		final Canvas c = new Canvas(200,200);
		ImageLayer layer1 = new ImageLayer(200, 200);
		ImageLayer layer2 = new ImageLayer(200, 200);
		ImageLayer layer3 = new ImageLayer(200, 200);
		
		layer1.setCaption("Layer1");
		layer2.setCaption("Layer2");
		layer3.setCaption("Layer3");
		
		c.addLayer(layer1);
		c.addLayer(layer2);
		c.addLayer(layer3);
		
		Font ft = new Font("Monospaced", Font.BOLD, 24);
		
		layer1.getGraphics().setColor(Color.BLACK);
		layer2.getGraphics().setColor(Color.BLACK);
		layer3.getGraphics().setColor(Color.BLACK);
		
		layer1.getGraphics().setFont(ft);
		layer2.getGraphics().setFont(ft);
		layer3.getGraphics().setFont(ft);
		
		layer1.getGraphics().drawString("Layer1", 40, 40);
		layer2.getGraphics().drawString("Layer2", 60, 60);
		layer3.getGraphics().drawString("Layer3", 80, 80);

		layer1.getGraphics().setColor(Color.BLUE);
		layer2.getGraphics().setColor(Color.RED);
		layer3.getGraphics().setColor(Color.GREEN);
		layer1.getGraphics().fillRect(40,40,60,30);
		layer2.getGraphics().fillRect(60,60,60,30);
		layer3.getGraphics().fillRect(80,80,60,30);
		

		layer1.update();
		layer2.update();
		layer3.update();
		
		final JList list = new JList();
		list.setCellRenderer(new ImageLayerListCellRenderer());
		final DefaultListModel listModel = new DefaultListModel();
		listModel.addElement(layer3);
		listModel.addElement(layer2);
		listModel.addElement(layer1);
		list.setModel(listModel);
		
		JPanel p = new JPanel() {
			public void paintComponent(Graphics g)
			{
				ImageRenderer renderer = ImageRendererFactory.getInstance();
				g.drawImage(renderer.render(c),0,0,null);
			}
			
		};
		
		final JFrame f = new JFrame("Sample Appliaction");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().setLayout(new BorderLayout());

		JButton b = new JButton("remove");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				int i = list.getSelectedIndex();
				Object o = list.getSelectedValue();
				System.out.println("removing " + ((ImageLayer)o).getCaption() + " " + i);
				listModel.removeElement(o);
				c.removeLayer((ImageLayer)o);
				list.validate();
				f.repaint();
			}
		});

		JButton b2 = new JButton("move");
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				ImageLayer layer = (ImageLayer)list.getSelectedValue();
//				layer.setAlpha(layer.getAlpha() * 0.9f);
				layer.setX(layer.getX()+10);
				layer.setY(layer.getY()+10);
				f.repaint();
			}
		});
		
		JButton b3 = new JButton("alpha");
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				ImageLayer layer = (ImageLayer)list.getSelectedValue();
				layer.setAlpha(layer.getAlpha() * 0.9f);
				f.repaint();
			}
		});
		
		f.getContentPane().add(list, BorderLayout.SOUTH);
		f.getContentPane().add(p, BorderLayout.CENTER);
		
		JPanel bp = new JPanel(new GridLayout(0,1));
		bp.add(b);
		bp.add(b2);
		bp.add(b3);
		
		f.getContentPane().add(bp, BorderLayout.EAST);
		f.setSize(400, 400);
		f.setLocation(100, 100);
		f.validate();
		f.setVisible(true);
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
//		try
//		{
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		}
//		catch (ClassNotFoundException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		catch (InstantiationException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		catch (IllegalAccessException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		catch (UnsupportedLookAndFeelException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				new SampleApp().makeGUI();
			}
		});
	}

}
