package net.coobird.paint.application;

import java.awt.Component;

import javax.swing.JOptionPane;

public class ApplicationUtils
{
	private static Component mainComponent = null;
	
	public static void showExceptionMessage(Exception e)
	{
		String msg = "An exception has occurred.\n\n" + e.toString() +
				"\n\nIf problems persist, please contact your local administrator.";
		// log exception
		e.printStackTrace(System.out);
		
		JOptionPane.showMessageDialog(
				mainComponent,
				msg,
				"Exception",
				JOptionPane.ERROR_MESSAGE
		);
	}
	
	public static void setMainComponent(Component c)
	{
		mainComponent = c;
	}
}
