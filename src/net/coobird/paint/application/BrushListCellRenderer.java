package net.coobird.paint.application;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import net.coobird.paint.brush.Brush;

public class BrushListCellRenderer 
	extends JLabel
	implements ListCellRenderer
{
	
	public BrushListCellRenderer()
	{
		super();
		this.setOpaque(true);
	}
	
	@Override
	public Component getListCellRendererComponent(
			JList list,
			Object value,
			int cell,
			boolean isSelected,
			boolean cellHasFocus
	)
	{
		if (value instanceof Brush)
		{
			Brush b = (Brush)value;
			this.setText(b.getName());
			this.setIcon(new ImageIcon(b.getThumbBrush()));
			
			if (isSelected)
			{
				this.setOpaque(true);
				this.setBackground(list.getSelectionBackground());
				this.setForeground(list.getSelectionForeground());
			}
			else
			{
				this.setOpaque(true);
				this.setBackground(list.getBackground());
				this.setForeground(list.getForeground());
			}
		}
		else
		{
			this.setText("Not a Brush.");
		}

		return this;
	}
}
