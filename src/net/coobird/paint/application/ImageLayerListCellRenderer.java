package net.coobird.paint.application;

import java.awt.Component;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import net.coobird.paint.image.ImageLayer;

/**
 * The {@code ImageLayerListCellRenderer} class is the {@link ListCellRenderer}
 * for displaying {@link ImageLayer} objects in a {@link JList}.
 * @author coobird
 *
 */
public class ImageLayerListCellRenderer
	extends JLabel
	implements ListCellRenderer
{
	/**
	 * Version string used for serialization.
	 */
	private static final long serialVersionUID = 8390058918154536766L;

	/**
	 * Constructs an instance of {@code ImageLayerListCellRenderer}.
	 */
	public ImageLayerListCellRenderer()
	{
		super();
		this.setOpaque(true);
	}

	/**
	 * Generates a {@link ImageLayerListCellRenderer} object to display the
	 * information about a {@link ImageLayer}.
	 */
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
			
			if (!((ImageLayer)value).isVisible())
			{
				this.setText(this.getText() + " - invisible");
			}
			
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
