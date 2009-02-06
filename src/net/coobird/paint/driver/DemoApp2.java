package net.coobird.paint.driver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.coobird.paint.application.BrushListCellRenderer;
import net.coobird.paint.application.ImageLayerListCellRenderer;
import net.coobird.paint.brush.Brush;
import net.coobird.paint.brush.BrushController;
import net.coobird.paint.brush.RegularCircularBrush;
import net.coobird.paint.brush.RegularEllipticalBrush;
import net.coobird.paint.brush.RegularEllipticalEraser;
import net.coobird.paint.brush.SolidCircularBrush;
import net.coobird.paint.filter.ImageFilter;
import net.coobird.paint.filter.MatrixImageFilter;
import net.coobird.paint.filter.RepeatableMatrixFilter;
import net.coobird.paint.filter.ThreadedWrapperFilter;
import net.coobird.paint.image.Canvas;
import net.coobird.paint.image.ImageLayer;
import net.coobird.paint.image.ImageRenderer;
import net.coobird.paint.image.ImageRendererFactory;
import net.coobird.paint.io.DefaultImageInput;
import net.coobird.paint.io.DefaultImageOutput;
import net.coobird.paint.io.JavaSupportedImageInput;

public class DemoApp2
{
	private void makeGUI()
	{
		final JFrame f = new JFrame("Paint Dot Jar Demonstration 2");
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
		brushListModel.addElement(new SolidCircularBrush(null, 120, Color.white));
		brushListModel.addElement(new RegularCircularBrush("Small black brush", 40, new Color(0,0,0,32)));
		brushListModel.addElement(new RegularCircularBrush("Thin black brush", 80, new Color(0,0,0,4)));
		brushListModel.addElement(new RegularCircularBrush("Emerald green 40 px brush", 40, new Color(0,255,96,32)));
		brushListModel.addElement(new RegularEllipticalBrush(null, 60, Math.PI * 0.67, 0.3, new Color(0,0,128,4)));
		brushListModel.addElement(new RegularEllipticalBrush(null, 60, Math.PI * 0.67, 0.5, new Color(0,0,128,4)));
		brushListModel.addElement(new RegularEllipticalBrush(null, 60, Math.PI * 0.8, 0.8, new Color(0,0,128,4)));
		brushListModel.addElement(new RegularEllipticalBrush(null, 120, Math.PI * 0.4, 0.8, new Color(0,0,0,16)));
		brushListModel.addElement(new RegularEllipticalBrush(null, 240, Math.PI * 0.4, 0.8, new Color(255,0,0,16)));
		brushListModel.addElement(new RegularEllipticalBrush(null, 240, Math.PI * 0.2, 0.6, new Color(255,0,0,4)));
		brushListModel.addElement(new RegularEllipticalBrush(null, 40, Math.PI * 0.9, 0.2, new Color(0,255,128,4)));
		brushListModel.addElement(new RegularEllipticalBrush(null, 60, Math.PI * 0.0, 0.4, new Color(0,0,128,4)));
		brushListModel.addElement(new RegularEllipticalEraser(null, 40, 0, 1, 1f));
		brushListModel.addElement(new RegularEllipticalEraser(null, 80, 0, 1, 1f));
		brushListModel.addElement(new RegularEllipticalEraser(null, 80, Math.PI * 0.25, 0.5, 0.5f));
		final JScrollPane brushListSp = new JScrollPane(brushList);
		listPanels.add(brushListSp);
		
		final BrushController bc = new BrushController();
		
		final JList ilList = new JList();
		ilList.setCellRenderer(new ImageLayerListCellRenderer());
		final DefaultListModel ilListModel = new DefaultListModel();
		ilList.setModel(ilListModel);
		
		final int SIZE = 400;
		
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
		

		final ImageRenderer renderer = ImageRendererFactory.getInstance();
//		final PartialImageRenderer renderer = new ClippableImageRenderer();
//		final ProgressiveImageRenderer renderer = new ProgressiveImageRenderer();
		
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
			long lastTime = System.currentTimeMillis();
			
			public void mouseDragged(MouseEvent e)
			{
				ImageLayer il = (ImageLayer)ilList.getSelectedValue();
				Brush b = (Brush)brushList.getSelectedValue();
				
				if (il == null || b == null)
				{
					return;
				}
				
				bc.drawBrush(
						il,
						b,
						e.getX(),
						e.getY()
				);
				
				long timePast = System.currentTimeMillis() - lastTime;
				if (timePast > 50)
				{
					p.repaint();
					lastTime = System.currentTimeMillis();
				}
			}
			
			public void mouseReleased(MouseEvent e)
			{
				if (ilList.getSelectedValue() == null)
				{
					return;
				}
				
				((ImageLayer)ilList.getSelectedValue()).update();
				ilList.repaint();
				
				bc.releaseBrush();
				
				p.repaint();
			}

			public void mousePressed(MouseEvent e)
			{
				ImageLayer il = (ImageLayer)ilList.getSelectedValue();
				Brush b = (Brush)brushList.getSelectedValue();
				
				if (il == null || b == null)
				{
					return;
				}
				
				bc.drawBrush(
						il,
						b,
						e.getX(),
						e.getY()
				);
			}
		};
		
		p.addMouseListener(ma);
		p.addMouseMotionListener(ma);

		ilList.setDropTarget(new DropTarget()
		{

			/* (non-Javadoc)
			 * @see java.awt.dnd.DropTarget#drop(java.awt.dnd.DropTargetDropEvent)
			 */
			@Override
			public synchronized void drop(DropTargetDropEvent e)
			{
				super.drop(e);
				Point pt = e.getLocation();
				int fromIndex = ilList.getSelectedIndex();
				int toIndex = ilList.locationToIndex(pt);
				
				ch.getCanvas().moveLayer(fromIndex, toIndex);

				ilListModel.removeAllElements();
				for (ImageLayer il : ch.getCanvas().getLayers())
				{
					ilListModel.addElement(il);
				}
				p.repaint();
			}
		});
		ilList.setDragEnabled(true);
		
		class ActionMenuItem extends JMenuItem implements ActionListener
		{
			public ActionMenuItem(String s)
			{
				addActionListener(this);
				setText(s);
			}
			
			public void actionPerformed(ActionEvent e) {};
		}
		final JPopupMenu menu = new JPopupMenu();
		menu.add(new ActionMenuItem("Change Name...") {
			public void actionPerformed(ActionEvent e)
			{
				String s = JOptionPane.showInputDialog(f, "Name:");
				((ImageLayer)ilList.getSelectedValue()).setCaption(s);
				p.repaint();
			}
		});
		menu.add(new ActionMenuItem("Change Alpha...") {
			public void actionPerformed(ActionEvent e)
			{
				String s = JOptionPane.showInputDialog(f, "Alpha:");
				((ImageLayer)ilList.getSelectedValue()).setAlpha(Float.parseFloat(s));
				p.repaint();
			}
		});
		menu.add(new ActionMenuItem("Change Location...") {
			public void actionPerformed(ActionEvent e)
			{
				String s = JOptionPane.showInputDialog(f, "Location (x,y):");
				String[] t = s.split(",");
				int x = Integer.parseInt(t[0]);
				int y = Integer.parseInt(t[1]);
				
				((ImageLayer)ilList.getSelectedValue()).setLocation(x, y);
				p.repaint();
			}
		});
		
		ilList.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e)
			{
				if (e.isPopupTrigger())
				{
					menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
			public void mouseReleased(MouseEvent e)
			{
				if (e.isPopupTrigger())
				{
					menu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		
		final JMenuBar menubar = new JMenuBar();
		final JMenu fileMenu = new JMenu("File");
		final JMenu brushMenu = new JMenu("Brush");
		final JMenu layerMenu = new JMenu("Layer");
		
		fileMenu.add(new ActionMenuItem("New") {
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("new");
				
				String s = JOptionPane.showInputDialog(f, "Enter dimensions in (w,h):");
				
				int width = SIZE;
				int height = SIZE;
				
				String[] t = s.split(",");
				
				try
				{
					width = Integer.parseInt(t[0]);
					height = Integer.parseInt(t[1]);
				}
				catch (Exception ex)
				{
					JOptionPane.showMessageDialog(f, "Invalid dimensions.\n" + ex);
				}
				
				Canvas c = new Canvas(width, height);
				c.addLayer(new ImageLayer(width, height));
				c.addLayer(new ImageLayer(width, height));
				c.addLayer(new ImageLayer(width, height));
				ch.setCanvas(c);
				
				ilListModel.removeAllElements();
				for (ImageLayer il : ch.getCanvas().getLayers())
				{
					ilListModel.addElement(il);
				}
				p.repaint();
			}
		});

		fileMenu.addSeparator();
		
		fileMenu.add(new ActionMenuItem("Open") {
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("open");
				ch.setCanvas(new DefaultImageInput().read(new File("output.zip")));

				ilListModel.removeAllElements();
				for (ImageLayer il : ch.getCanvas().getLayers())
				{
					ilListModel.addElement(il);
				}
				p.repaint();
			}
		});

		fileMenu.add(new ActionMenuItem("Save") {
			public void actionPerformed(ActionEvent arg0)
			{
				System.out.println("save");
				new DefaultImageOutput().write(ch.getCanvas(), new File("output.zip"));
			}
		});
		
		fileMenu.addSeparator();
		
		fileMenu.add(new ActionMenuItem("Open Supported Image...") {
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fc = new JFileChooser();
				int option = fc.showOpenDialog(f);
				
				if (option != JFileChooser.APPROVE_OPTION)
					return;
				
				File f = fc.getSelectedFile();
				
				ch.setCanvas(new JavaSupportedImageInput().read(f));
				ilListModel.removeAllElements();
				for (ImageLayer il : ch.getCanvas().getLayers())
				{
					ilListModel.addElement(il);
				}
				p.repaint();
			}
		});
		
		fileMenu.addSeparator();

		fileMenu.add(new ActionMenuItem("Exit") {
			public void actionPerformed(ActionEvent arg0)
			{
				f.setVisible(false);
			}
		});

		final JCheckBoxMenuItem bcMenu = new JCheckBoxMenuItem("Movable Brush");
		bcMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0)
			{
				System.out.println("movable toggle");
				bc.setMovable(!bc.getMovable());
				bcMenu.setSelected(bc.getMovable());
			}
		});
		brushMenu.add(bcMenu);
		
		layerMenu.add(new ActionMenuItem("New Layer") {
			public void actionPerformed(ActionEvent e)
			{
				ch.getCanvas().addLayer(new ImageLayer(ch.getCanvas().getWidth(), ch.getCanvas().getHeight()));
				
				ilListModel.removeAllElements();
				for (ImageLayer il : ch.getCanvas().getLayers())
				{
					ilListModel.addElement(il);
				}
				p.repaint();

			}
		});
		
		layerMenu.addSeparator();
		
		layerMenu.add(new ActionMenuItem("Blur") {
			public void actionPerformed(ActionEvent e)
			{
				ImageFilter filter = new MatrixImageFilter(3, 3, new float[]{
						0.0f,	0.1f,	 0.0f,
						0.1f,	0.6f,	 0.1f,
						0.0f,	0.1f,	 0.0f
				});
				
				for (ImageLayer il : ch.getCanvas().getLayers())
				{
					il.setImage(filter.processImage(il.getImage()));
				}
				p.repaint();
			}
		});

		layerMenu.add(new ActionMenuItem("Blur More") {
			public void actionPerformed(ActionEvent e)
			{
				long st = System.currentTimeMillis();
				
				ImageFilter filter = new RepeatableMatrixFilter(
						3, 3, 10, new float[]{
						0.0f,	0.1f,	 0.0f,
						0.1f,	0.6f,	 0.1f,
						0.0f,	0.1f,	 0.0f
				});
				
				for (ImageLayer il : ch.getCanvas().getLayers())
				{
					il.setImage(filter.processImage(il.getImage()));
				}
				
				long tp = System.currentTimeMillis() - st;
				System.out.println("complete in: " + tp);
				
				p.repaint();
			}
		});

		layerMenu.add(new ActionMenuItem("Blur More Concurrent") {
			public void actionPerformed(ActionEvent e)
			{
				long st = System.currentTimeMillis();
				
				ImageFilter filter = new ThreadedWrapperFilter( 
					
					new RepeatableMatrixFilter(
						3, 3, 10, new float[]{
								0.0f,	0.1f,	 0.0f,
								0.1f,	0.6f,	 0.1f,
								0.0f,	0.1f,	 0.0f
						})
					);
				
				for (ImageLayer il : ch.getCanvas().getLayers())
				{
					il.setImage(filter.processImage(il.getImage()));
				}
				
				long tp = System.currentTimeMillis() - st;
				System.out.println("complete in: " + tp);
				
				p.repaint();
			}
		});

		layerMenu.add(new ActionMenuItem("No Effect") {
			public void actionPerformed(ActionEvent e)
			{
				ImageFilter filter = new MatrixImageFilter(3, 3, new float[]{
						0.0f,	0.0f,	 0.0f,
						0.0f,	1.0f,	 0.0f,
						0.0f,	0.0f,	 0.0f
				});
				
				for (ImageLayer il : ch.getCanvas().getLayers())
				{
					il.setImage(filter.processImage(il.getImage()));
				}
				p.repaint();
			}
		});

		layerMenu.add(new ActionMenuItem("Saturate") {
			public void actionPerformed(ActionEvent e)
			{
				ImageFilter filter = new MatrixImageFilter(3, 3, new float[]{
						0.0f,	0.0f,	 0.0f,
						0.0f,	2.0f,	 0.0f,
						0.0f,	0.0f,	 0.0f
				});
				
				for (ImageLayer il : ch.getCanvas().getLayers())
				{
					il.setImage(filter.processImage(il.getImage()));
				}
				p.repaint();
			}
		});

		layerMenu.add(new ActionMenuItem("Blur + Lighten") {
			public void actionPerformed(ActionEvent e)
			{
				ImageFilter filter = new MatrixImageFilter(5, 5, new float[]{
						0.0f,	0.0f,	 0.25f,	0.0f,	0.0f,
						0.0f,	0.0f,	 0.0f,	0.0f,	0.0f,
						0.25f,	0.0f,	 1.0f,	0.0f,	0.25f,
						0.0f,	0.0f,	 0.0f,	0.0f,	0.0f,
						0.0f,	0.0f,	 0.25f,	0.0f,	0.0f
				});
				
				for (ImageLayer il : ch.getCanvas().getLayers())
				{
					il.setImage(filter.processImage(il.getImage()));
				}
				p.repaint();
			}
		});
		

		menubar.add(fileMenu);
		menubar.add(brushMenu);
		menubar.add(layerMenu);
		f.setJMenuBar(menubar);

		bcMenu.setSelected(bc.getMovable());

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
				new DemoApp2().makeGUI();
			}
		});
	}
	
	class CanvasHolder
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
