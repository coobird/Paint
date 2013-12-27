package net.coobird.paint.application;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import net.coobird.paint.brush.Brush;

/**
 * The {@code BrushListCellRenderer} class is the {@link ListCellRenderer} for
 * displaying {@link Brush} objects in a {@link JList}.
 * @author coobird
 *
 */
public class BrushListCellRenderer 
	extends JLabel
	implements ListCellRenderer
{
	/**
	 * Version string for this class, used for serialization. 
	 */
	private static final long serialVersionUID = -1935391746701897141L;
	
	/**
	 * Specifies whether or not to display the brush name.
	 */
	private boolean showLabel;

	/**
	 * Constructs an instance of {@code BrushListCellRenderer}.
	 * The brush name will be displayed.
	 */
	public BrushListCellRenderer()
	{
		this(true);
	}
	
	/**
	 * Constructor which specifies whether or not to display the name of the
	 * brush on the cell.
	 *  
	 * @param showLabel		Whether or not to display the brush name.
	 */
	public BrushListCellRenderer(boolean showLabel)
	{
		super();
		this.showLabel = showLabel;
		this.setOpaque(true);
	}
	
	/**
	 * Generates a {@link BrushListCellRenderer} object to display the
	 * information about a {@link Brush}.
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
		if (value instanceof Brush)
		{
			Brush b = (Brush)value;
			
			if (showLabel)
			{
				this.setToolTipText("");
				this.setText(b.getName());
			}
			else
			{
				this.setToolTipText(b.getName());
				this.setText("");
			}
			
			this.setIcon(new ImageIcon(b.getThumbImage()));
			
		}
		else
		{
			this.setIcon(null);
			this.setText("Not a Brush.");
		}
		
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

		return this;
	}
}
