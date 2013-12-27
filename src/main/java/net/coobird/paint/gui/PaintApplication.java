package net.coobird.paint.gui;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.coobird.paint.driver.CanvasViewPanel;
import net.coobird.paint.image.Canvas;
import net.coobird.paint.image.ClippableImageRenderer;
import net.coobird.paint.io.FormatManager;
import net.coobird.paint.io.ImageInput;
import net.coobird.paint.io.ImageInputOutputException;

public final class PaintApplication
{
	private static class ApplicationComponents
	{
		StatusBar statusBar = new StatusBar();
		JMenuBar menuBar = new JMenuBar();
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		JToolBar toolBar = new JToolBar();
		BrushComposer brushComposer = new BrushComposer();
	}
	
	private ApplicationComponents appComponents = new ApplicationComponents(); 
	
	private void makeGUI()
	{
		final JFrame mainFrame = new JFrame();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(new BorderLayout());
		
		JMenu fileMenu = new JMenu("File");
		Action fileOpenAction = new AbstractAction("Open...") {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				final JFileChooser fc = new JFileChooser();
				int option = fc.showOpenDialog(mainFrame);
				
				if (option != JFileChooser.APPROVE_OPTION)
				{
					return;
				}
				
				final File f = fc.getSelectedFile();
				final ImageInput input = FormatManager.getImageInput(f);
				
				if (input != null)
				{
					SwingUtilities.invokeLater(new Runnable() {
						public void run()
						{
							try
							{
								Canvas c = input.read(f);
								
								appComponents.statusBar.setStatus("Opening " + f.getName() + ".", StatusBar.INFO);
								ClippableImageRenderer r = new ClippableImageRenderer();
								CanvasViewPanel p = new CanvasViewPanel(r, c);
								
								
								JScrollPane sp = new JScrollPane(p);
								
								VerticalRuler vr = new VerticalRuler();
								HorizontalRuler hr = new HorizontalRuler();
								
								p.addPositionListener(hr);
								p.addPositionListener(hr);
								
								sp.setRowHeaderView(vr);
								sp.setColumnHeaderView(hr);
								
								
								// Poor performance to have to render each time.
								Image img = r.render(c).getScaledInstance(16, 16, Image.SCALE_DEFAULT);
								
								appComponents.tabbedPane.insertTab(f.getName(), 
										new ImageIcon(img),
										sp,
										f.getName(),
										0
								);
							
								appComponents.tabbedPane.setSelectedIndex(0);
								
								//tp.setSelectedIndex(tp.getTabCount() - 1);
								appComponents.statusBar.setStatus("Successfully opened " + f.getName() + ".", StatusBar.INFO);
							}
							catch (ImageInputOutputException e1)
							{
								e1.printStackTrace();
							}
						}
					});
					
				}
			}
		};
		JMenuItem fileOpenMenu = new JMenuItem(fileOpenAction);
		fileMenu.add(fileOpenMenu);
		
		appComponents.menuBar.add(fileMenu);
		
		
		mainFrame.setJMenuBar(appComponents.menuBar);
		
		appComponents.toolBar.add(fileOpenAction);
		appComponents.toolBar.setFloatable(false);
		
		mainFrame.getContentPane().add(appComponents.toolBar, BorderLayout.NORTH);
		mainFrame.getContentPane().add(appComponents.tabbedPane, BorderLayout.CENTER);
		mainFrame.getContentPane().add(appComponents.statusBar, BorderLayout.SOUTH);

		mainFrame.setLocation(200, 200);
		mainFrame.setSize(400, 400);
		mainFrame.validate();
		mainFrame.setVisible(true);
		
		JFrame f = new JFrame();
		f.getContentPane().add(appComponents.brushComposer);
		f.setLocation(50, 50);
		f.pack();
		f.setVisible(true);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new PaintApplication().makeGUI();
			}
		});
	}
}
