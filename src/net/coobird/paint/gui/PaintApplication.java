package net.coobird.paint.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import net.coobird.paint.driver.CanvasViewPanel;
import net.coobird.paint.image.Canvas;
import net.coobird.paint.image.ClippableImageRenderer;
import net.coobird.paint.io.FormatManager;
import net.coobird.paint.io.ImageInput;
import net.coobird.paint.io.ImageInputOutputException;

public class PaintApplication
{
	private void makeGUI()
	{
		final JFrame mainFrame = new JFrame();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(new BorderLayout());
		
		final JTabbedPane tp = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		JLabel label = new JLabel("Status bar");
		
		JMenuBar menuBar = new JMenuBar();
		
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
				
				File f = fc.getSelectedFile();
				ImageInput input = FormatManager.getImageInput(f);
				
				if (input != null)
				{
					try
					{
						Canvas c = input.read(f);
						ClippableImageRenderer r = new ClippableImageRenderer();
						CanvasViewPanel p = new CanvasViewPanel(r, c);
						JScrollPane sp = new JScrollPane(p);
						sp.setRowHeaderView(new JLabel("helloo"));
						sp.setColumnHeaderView(new JLabel("helloo"));
						tp.add(f.getName(), sp);
						tp.setSelectedIndex(tp.getTabCount()-1);
					}
					catch (ImageInputOutputException e1)
					{
						e1.printStackTrace();
					}
				}
			}
		};
		JMenuItem fileOpenMenu = new JMenuItem(fileOpenAction);
		fileMenu.add(fileOpenMenu);
		
		menuBar.add(fileMenu);
		
		
		mainFrame.setJMenuBar(menuBar);
		
		
		JToolBar tb = new JToolBar();
		tb.add(fileOpenAction);
		tb.setFloatable(false);
		
		mainFrame.getContentPane().add(tb, BorderLayout.NORTH);
		mainFrame.getContentPane().add(tp, BorderLayout.CENTER);
		mainFrame.getContentPane().add(label, BorderLayout.SOUTH);

		mainFrame.setLocation(200, 200);
		mainFrame.setSize(400, 400);
		mainFrame.validate();
		mainFrame.setVisible(true);
		
		JFrame f = new JFrame();
		f.getContentPane().add(new BrushComposer());
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
		catch (UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				new PaintApplication().makeGUI();
			}
		});
	}
}
