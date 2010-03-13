package net.coobird.paint.gui;

import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.JLabel;

public class StatusBar extends JLabel
{
	private static final long serialVersionUID = 7750574233058155542L;
	
	public static final int INFO = 0;
	public static final int ERROR = 1;
	
	public void setStatus(String s)
	{
		this.setText(s);
	}
	
	public void setStatus(String s, int level)
	{
		switch (level)
		{
		case (INFO):
			break;
		
		case (ERROR):
			this.setForeground(Color.red);
			break;
		
		default:
			this.setForeground(SystemColor.textText);
			break;
		}
		
		this.setText(s);
	}
}
