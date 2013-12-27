package net.coobird.paint.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import net.coobird.paint.layer.ImageLayer;

/**
 * The {@code ImageLayerListCellRenderer} class is the {@link ListCellRenderer}
 * for displaying {@link ImageLayer} objects in a {@link JList}.
 * @author coobird
 *
 */
public class ImageLayerListCellRenderer
	extends JPanel
	implements ListCellRenderer
{
	/**
	 * Version string used for serialization.
	 */
	private static final long serialVersionUID = 8390058918154536766L;
	
	private static final int GAP = 10;

	private JLabel captionLabel;
	private JLabel thumbnailLabel;

	/**
	 * Constructs an instance of {@code ImageLayerListCellRenderer}.
	 */
	public ImageLayerListCellRenderer()
	{
		super();
		this.setOpaque(true);
		this.setBackground(SystemColor.text);
		
		captionLabel = new JLabel();
		thumbnailLabel = new JLabel();
		thumbnailLabel.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createEmptyBorder(GAP, GAP, GAP, GAP),
						BorderFactory.createLineBorder(Color.black)
				)
		);

		this.setLayout(new BorderLayout(GAP, GAP));
		this.add(thumbnailLabel, BorderLayout.WEST);
		this.add(captionLabel, BorderLayout.CENTER);
		
		captionLabel.setOpaque(true);
		thumbnailLabel.setOpaque(false);
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
			final ImageLayer il = (ImageLayer)value;
			captionLabel.setText(il.getCaption());
			thumbnailLabel.setIcon(new ImageIcon(il.getThumbImage()));

			if (!((ImageLayer)value).isVisible())
			{
				captionLabel.setText(captionLabel.getText() + " - invisible");
			}
			
			if (isSelected)
			{
				this.setBackground(SystemColor.textHighlight);
				this.setForeground(SystemColor.textHighlightText);
				captionLabel.setBackground(SystemColor.textHighlight);
				captionLabel.setForeground(SystemColor.textHighlightText);
			}
			else
			{
				this.setBackground(SystemColor.text);
				this.setForeground(SystemColor.textText);
				captionLabel.setBackground(SystemColor.text);
				captionLabel.setForeground(SystemColor.textText);
			}
		}
		else
		{
			captionLabel.setText("Not an ImageLayer");
		}
		
		return this;
	}
}
