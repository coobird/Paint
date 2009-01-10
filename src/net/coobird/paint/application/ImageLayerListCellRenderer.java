package net.coobird.paint.application;

import java.awt.Component;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import net.coobird.paint.image.ImageLayer;

public class ImageLayerListCellRenderer implements ListCellRenderer
{

	@Override
	public Component getListCellRendererComponent(
			JList list,
			Object value,
			int cell,
			boolean isSelected,
			boolean cellHasFocus)
	{
		JPanel p = new JPanel();
		
		if (value instanceof ImageLayer)
		{
			ImageLayer il = (ImageLayer)value;
			JLabel l = new JLabel(il.getCaption());
			l.setIcon(new ImageIcon(il.getThumbImage()));
			
			if (isSelected)
			{
				l.setOpaque(true);
				l.setBackground(SystemColor.textHighlight);
				l.setForeground(SystemColor.textHighlightText);
			}
			else
			{
				l.setOpaque(true);
				l.setBackground(SystemColor.text);
				l.setForeground(SystemColor.textText);
			}
			
			
			p.add(l);
		}
		else
		{
			p.add(new JLabel("Not an ImageLayer"));
		}
		
		return p;
	}

}
