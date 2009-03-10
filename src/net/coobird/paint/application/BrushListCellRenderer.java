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
	private static final long serialVersionUID = -2298872884258272114L;
	
	private boolean showLabel;

	/**
	 * Constructs an instance of {@code BrushListCellRenderer}.
	 */
	public BrushListCellRenderer()
	{
		this(true);
	}
	
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
				this.setText(b.getName());
			}
			else
			{
				this.setToolTipText(b.getName());
			}
			
			this.setIcon(new ImageIcon(b.getThumbImage()));
			
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
