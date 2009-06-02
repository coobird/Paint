package net.coobird.paint.driver;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.coobird.paint.image.Canvas;
import net.coobird.paint.image.ClippableImageRenderer;
import net.coobird.paint.io.DefaultImageInput;
import net.coobird.paint.io.ImageInputOutputException;

/**
 * A picture view for Canvas in a Jar(name TBD)
 * 
 * Literally about 30 lines to implement a viewer.
 * Excluding the refactoring time for the panel, the implementation of the
 * viewer was just a few minutes -- should show the simplicity of using the 
 * framework in creating new apps.
 * 
 * @author coobird
 *
 */
public class ViewerDemo
{
	private void makeGUI()
	{
		JFrame f = new JFrame("Viewer Demo");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Canvas c = new Canvas(400, 400);
		JFileChooser fc = new JFileChooser();
		int option = fc.showOpenDialog(f);
		
		if (option == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				c = new DefaultImageInput().read(fc.getSelectedFile());
			}
			catch (ImageInputOutputException e1)
			{
				e1.printStackTrace();
			}
		}
		
		CanvasViewPanel p = new CanvasViewPanel(new ClippableImageRenderer(), c);
		
		final JScrollPane sp = new JScrollPane(p);
		sp.getViewport().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e)
			{
				sp.repaint();
			}
		});
		
		f.getContentPane().add(sp);
		
		f.setLocation(200, 200);
		f.setSize(
				c.getWidth() + f.getInsets().left + f.getInsets().right + 1,
				c.getHeight() + f.getInsets().top + f.getInsets().bottom + 1
		);
		f.validate();
		f.setVisible(true);
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				new ViewerDemo().makeGUI();
			}
		});
	}
}
