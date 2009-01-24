package net.coobird.paint.application;

import java.awt.Component;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import net.coobird.paint.brush.Brush;
import net.coobird.paint.brush.RegularCircularBrush;

public class BrushListCellRenderer 
	extends JPanel
	implements ListCellRenderer
{
	@Override
	public Component getListCellRendererComponent(
			JList list,
			Object value,
			int cell,
			boolean isSelected,
			boolean cellHasFocus
	)
	{
//		JPanel p = new JPanel();
		this.removeAll();
		
		if (value instanceof Brush)
		{
			Brush b = (Brush)value;
			JLabel l = new JLabel(b.getName());
			l.setIcon(new ImageIcon(b.getThumbBrush()));
			
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
			
//			p.add(l);
			add(l);
		}
		else
		{
//			p.add(new JLabel("Not a Brush."));
			add(new JLabel("Not a Brush."));
		}
		
//		return p;
		return this;
	}

}
