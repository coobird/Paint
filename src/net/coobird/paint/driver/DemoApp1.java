package net.coobird.paint.driver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.coobird.paint.application.BrushListCellRenderer;
import net.coobird.paint.application.ImageLayerListCellRenderer;
import net.coobird.paint.brush.Brush;
import net.coobird.paint.brush.RegularCircularBrush;
import net.coobird.paint.brush.RegularEllipticalBrush;
import net.coobird.paint.brush.SolidCircularBrush;
import net.coobird.paint.image.Canvas;
import net.coobird.paint.image.ImageLayer;
import net.coobird.paint.image.ProgressiveImageRenderer;
import net.coobird.paint.io.DefaultImageInput;
import net.coobird.paint.io.DefaultImageOutput;

public class DemoApp1
{
	private void makeGUI()
	{
		JFrame f = new JFrame("Paint Dot Jar Demonstration");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().setLayout(new BorderLayout());
		
		JPanel listPanels = new JPanel(new GridLayout(0, 1));
		
		final JList brushList = new JList();
		brushList.setCellRenderer(new BrushListCellRenderer());
		final DefaultListModel brushListModel = new DefaultListModel();
		brushList.setModel(brushListModel);
		brushListModel.addElement(new RegularCircularBrush(null, 80, new Color(0,0,255,32)));
		brushListModel.addElement(new SolidCircularBrush(null, 80, Color.red));
		brushListModel.addElement(new SolidCircularBrush(null, 40, Color.orange));
		brushListModel.addElement(new RegularCircularBrush("Small black brush", 40, new Color(0,0,0,32)));
		brushListModel.addElement(new RegularCircularBrush("Thin black brush", 80, new Color(0,0,0,4)));
		brushListModel.addElement(new RegularCircularBrush("Emerald green 40 px brush", 40, new Color(0,255,96,32)));
		brushListModel.addElement(new RegularEllipticalBrush(null, 60, Math.PI * 0.67, 0.3, new Color(0,0,128,4)));
		brushListModel.addElement(new RegularEllipticalBrush(null, 60, Math.PI * 0.67, 0.5, new Color(0,0,128,4)));
		brushListModel.addElement(new RegularEllipticalBrush(null, 60, Math.PI * 0.8, 0.8, new Color(0,0,128,4)));
		brushListModel.addElement(new RegularEllipticalBrush(null, 120, Math.PI * 0.4, 0.8, new Color(0,0,0,16)));
		final JScrollPane brushListSp = new JScrollPane(brushList);
		listPanels.add(brushListSp);
		
		final JList ilList = new JList();
		ilList.setCellRenderer(new ImageLayerListCellRenderer());
		final DefaultListModel ilListModel = new DefaultListModel();
		ilList.setModel(ilListModel);
		
		final int SIZE = 1200;
		
		final CanvasHolder ch = new CanvasHolder();
		Canvas c = new Canvas(SIZE, SIZE);
		c.addLayer(new ImageLayer(SIZE, SIZE));
		c.addLayer(new ImageLayer(SIZE, SIZE));
		c.addLayer(new ImageLayer(SIZE, SIZE));
		ch.setCanvas(c);
		
		for (ImageLayer il : c.getLayers())
		{
			ilListModel.addElement(il);
		}
		
		final JScrollPane ilListSp = new JScrollPane(ilList);
		listPanels.add(ilListSp);
		
		f.getContentPane().add(listPanels, BorderLayout.EAST);
		

//		final ImageRenderer renderer = ImageRendererFactory.getInstance();
//		final PartialImageRenderer renderer = new ClippableImageRenderer();
		final ProgressiveImageRenderer renderer = new ProgressiveImageRenderer();
		
		final JPanel p = new JPanel() {
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				g.drawImage(renderer.render(ch.getCanvas()), 0, 0, null);
//				g.drawImage(
//						renderer.render(ch.getCanvas(), 0, 0, 200, 200),
//						0,
//						0,
//						null
//				);
			}
		};
		
		MouseAdapter ma = new MouseAdapter()
		{
			Graphics2D g;
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
			
			public void mouseReleased(MouseEvent e)
			{
				((ImageLayer)ilList.getSelectedValue()).update();
				ilList.repaint();
			}

			public void mousePressed(MouseEvent e)
			{
				g = ((ImageLayer)ilList.getSelectedValue()).getGraphics();
				b = ((Brush)brushList.getSelectedValue()).getImage();
				
				g.drawImage(
						b,
						e.getX()- (b.getWidth() / 2),
						e.getY()-(b.getHeight() / 2),
						null
				);
				
				p.repaint();
			}
		};
		
		p.addMouseListener(ma);
		p.addMouseMotionListener(ma);
		
		final JMenuBar menubar = new JMenuBar();
		final JMenu fileMenu = new JMenu("File");
		final JMenuItem newMenu = new JMenuItem("New");
		newMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				ilListModel.removeAllElements();
				System.out.println("new");
				Canvas c = new Canvas(SIZE, SIZE);
				c.addLayer(new ImageLayer(SIZE, SIZE));
				c.addLayer(new ImageLayer(SIZE, SIZE));
				c.addLayer(new ImageLayer(SIZE, SIZE));
				ch.setCanvas(c);
				for (ImageLayer il : ch.getCanvas().getLayers())
				{
					ilListModel.addElement(il);
				}
				p.repaint();
			}
		});
		final JMenuItem openMenu = new JMenuItem("Open");
		openMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				ilListModel.removeAllElements();
				System.out.println("open");
				ch.setCanvas(new DefaultImageInput().read(new File("output.zip")));
				for (ImageLayer il : ch.getCanvas().getLayers())
				{
					ilListModel.addElement(il);
				}
				p.repaint();
			}
		});
		final JMenuItem saveMenu = new JMenuItem("Save");
		saveMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0)
			{
				System.out.println("save");
				new DefaultImageOutput().write(ch.getCanvas(), new File("output.zip"));
			}
		});

		fileMenu.add(newMenu);
		fileMenu.add(openMenu);
		fileMenu.add(saveMenu);
		menubar.add(fileMenu);
		f.setJMenuBar(menubar);
		
		f.setSize(600,450);
		f.setLocation(200, 100);
		f.getContentPane().add(p);
		f.validate();
		f.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				new DemoApp1().makeGUI();
			}
		});
	}
	
	private static class CanvasHolder
	{
		Canvas c;
		
		public Canvas getCanvas()
		{
			return c;
		}
		
		public void setCanvas(Canvas c)
		{
			this.c = c;
		}
	}
}
