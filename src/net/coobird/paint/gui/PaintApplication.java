package net.coobird.paint.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class PaintApplication
{
	private void makeGUI()
	{
		final JFrame mainFrame = new JFrame();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(new BorderLayout());
		
		JTabbedPane tp = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		JLabel label = new JLabel("Status bar");
		tp.add(new JPanel() {{setName("Tab 1");}});
		tp.add(new JPanel() {{setName("Tab 2");}});
		
		JToolBar tb = new JToolBar();
		tb.add(new JButton("ha"));
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (UnsupportedLookAndFeelException e)
		{
			// TODO Auto-generated catch block
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
