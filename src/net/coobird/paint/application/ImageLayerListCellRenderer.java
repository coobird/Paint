package net.coobird.paint.application;

import java.awt.Component;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import net.coobird.paint.image.ImageLayer;

public class ImageLayerListCellRenderer
	extends JLabel
	implements ListCellRenderer
{
	/**
	 * Version string used for serialization.
	 */
	private static final long serialVersionUID = 8390058918154536766L;

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
			
			abstract class MyActionListener implements ActionListener
			{
				final ImageLayer layer;
				
				public MyActionListener(ImageLayer layer)
				{
					this.layer = layer;
				}
			}
			
			final PopupMenu menu = new PopupMenu();
			MenuItem show = new MenuItem("show");
			MenuItem hide = new MenuItem("hide");
			show.addActionListener(new MyActionListener((ImageLayer)value){
				public void actionPerformed(ActionEvent e)
				{
					layer.setVisible(true);
				}
			});
			
			this.addMouseListener(new MouseAdapter()
			{
			    public void mousePressed(MouseEvent e) {
			        maybeShowPopup(e);
			    }

			    public void mouseReleased(MouseEvent e) {
			        maybeShowPopup(e);
			    }

			    private void maybeShowPopup(MouseEvent e) {
			        if (e.isPopupTrigger()) {
			            menu.show(e.getComponent(),
			                       e.getX(), e.getY());
			        }
			    }
			});
			
			hide.addActionListener(new MyActionListener((ImageLayer)value){
				public void actionPerformed(ActionEvent e)
				{
					layer.setVisible(false);
				}
			});
			menu.add(show);
			menu.add(hide);
			
			this.add(menu);
		}
		else
		{
			this.setText("Not an ImageLayer");
		}
		
		return this;
	}

}
