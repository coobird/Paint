package net.coobird.paint.application;

import java.awt.Component;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import net.coobird.paint.image.ImageLayer;

public class ImageLayerListCellRenderer
	extends JLabel
	implements ListCellRenderer
{
	public ImageLayerListCellRenderer()
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
		if (value instanceof ImageLayer)
		{
			ImageLayer il = (ImageLayer)value;
			this.setText(il.getCaption());
			this.setIcon(new ImageIcon(il.getThumbImage()));
			
			if (isSelected)
			{
				this.setOpaque(true);
				this.setBackground(SystemColor.textHighlight);
				this.setForeground(SystemColor.textHighlightText);
			}
			else
			{
				this.setOpaque(true);
				this.setBackground(SystemColor.text);
				this.setForeground(SystemColor.textText);
			}
		}
		else
		{
			this.setText("Not an ImageLayer");
		}
		
		return this;
	}

}
