package net.coobird.paint.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;

public class BrushComposer extends JPanel
{
	/*
	 * Use JLayeredPane to overlap two color panels?
	 */
	private Color fgColor;
	private Color bgColor;
	
	public BrushComposer()
	{
		this.setBackground(Color.white);
		
		fgColor = Color.red;
		bgColor = Color.blue;
		
		JComponent colorBox = new JComponent() {
			{
				setPreferredSize(new Dimension(100, 100));
			}

			@Override
			protected void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				Dimension d = getSize();
				
				int cx = d.width / 2;
				int cy = d.height / 2;
				
				g.setColor(Color.white);
				g.fillRect(cx - 10, cy - 10, 40, 40);
				g.setColor(bgColor);
				g.fillRect(cx - 10+2, cy - 10+2, 40-3, 40-3);
				g.setColor(Color.black);
				g.drawRect(cx - 10, cy - 10, 40, 40);

				g.setColor(Color.white);
				g.fillRect(cx - 30, cy - 30, 40, 40);
				g.setColor(fgColor);
				g.fillRect(cx - 30+2, cy - 30+2, 40-3, 40-3);
				g.setColor(Color.black);
				g.drawRect(cx - 30, cy - 30, 40, 40);
				
			}
		};
		
		JList brushList = new JList();
		DefaultListModel listModel = new DefaultListModel();
		listModel.addElement("ha");
		listModel.addElement("he");
		listModel.addElement("ho");
		brushList.setModel(listModel);

		this.setLayout(new BorderLayout());
		this.add(colorBox, BorderLayout.NORTH);
		this.add(brushList, BorderLayout.CENTER);
	}
}
