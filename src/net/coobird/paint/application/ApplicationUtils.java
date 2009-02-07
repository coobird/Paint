package net.coobird.paint.application;

import javax.swing.JOptionPane;

public class ApplicationUtils
{
	public static void showExceptionMessage(Exception e)
	{
		String s = "There was an Exception.\n" + e.toString() + "\n" + e.getMessage();
		// log exception
		e.printStackTrace(System.out);
		
		JOptionPane.showMessageDialog(null, s, e.getClass().toString(), JOptionPane.ERROR_MESSAGE);
	}
}
