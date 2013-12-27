package net.coobird.paint.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.coobird.paint.application.BrushListCellRenderer;
import net.coobird.paint.brush.RegularCircularBrush;
import net.coobird.paint.brush.RegularEllipticalBrush;
import net.coobird.paint.brush.SolidCircularBrush;
import net.coobird.paint.brush.SolidEllipticalBrush;
import net.coobird.paint.brush.StagedBrush;

/**
 * Area for creating an brush from color and brush type.
 * 
 * @author coobird
 *
 */
public class BrushComposer extends JPanel
{
	private static final long serialVersionUID = -8529626322597188153L;
	
	/*
	 * Use JLayeredPane to overlap two color panels?
	 */
	private Color fgColor;
	private Color bgColor;
	
	
	private List<BrushComposerListener> listeners = 
		new ArrayList<BrushComposerListener>();
	
	
	public void addBrushComposerListener(BrushComposerListener listener)
	{
		listeners.add(listener);
	}
	
	public boolean removeBrushComposerListener(BrushComposerListener listener)
	{
		return listeners.remove(listener);
	}
	
	private void notifyBrushComposerListeners()
	{
		for (BrushComposerListener listener : listeners)
		{
			listener.brushChanged(0);
		}
	}
	
	public BrushComposer()
	{
		this.setBackground(Color.white);
		
		fgColor = Color.red;
		bgColor = Color.blue;
		
		final JLayeredPane colorBox = new JLayeredPane();
		
		Border colorSurroundingBorder = BorderFactory.createCompoundBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(Color.black, 1), 
						BorderFactory.createLineBorder(Color.white, 2)
				),
				BorderFactory.createLineBorder(Color.black, 1)
		);
		
		
		final JPanel fgColorPanel = new JPanel();
		fgColorPanel.setBackground(fgColor);
		fgColorPanel.setPreferredSize(new Dimension(100,100));
		fgColorPanel.setBorder(colorSurroundingBorder);
		
		
		final JPanel bgColorPanel = new JPanel();
		bgColorPanel.setBackground(bgColor);
		bgColorPanel.setPreferredSize(new Dimension(100,100));
		bgColorPanel.setBorder(colorSurroundingBorder);
		
		
		fgColorPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				super.mouseClicked(e);
				System.out.println("1");
			}
		});
		bgColorPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				super.mouseClicked(e);
				System.out.println("2");
			}
		});
		
		
		fgColorPanel.setBounds(20, 20, 40, 40);
		bgColorPanel.setBounds(40, 40, 40, 40);
		
		
		colorBox.setLayer(bgColorPanel, JLayeredPane.DEFAULT_LAYER);
		colorBox.setLayer(fgColorPanel, JLayeredPane.DEFAULT_LAYER, 0);
		colorBox.add(fgColorPanel);
		colorBox.add(bgColorPanel);
		colorBox.setPreferredSize(new Dimension(200,200));
		
		
		JList brushList = new JList();
		brushList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		brushList.setVisibleRowCount(-1);
		
		brushList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				notifyBrushComposerListeners();
				System.out.println("changed");
			}
		});
		
		
		brushList.setCellRenderer(new ListCellRenderer() {
			
			ListCellRenderer renderer = new BrushListCellRenderer(false);
			
			@Override
			public Component getListCellRendererComponent(JList list, Object value,
					int index, boolean isSelected, boolean cellHasFocus)
			{
				Component c = renderer.getListCellRendererComponent(list, value, index, false, cellHasFocus);
				
				JPanel d;
				
				if (isSelected)
				{
					d = new JPanel();
					d.add(c);
					d.setOpaque(false);
					d.setBorder(BorderFactory.createLineBorder(Color.blue, 2));
				}
				else
				{
					d = new JPanel();
					d.add(c);
					d.setOpaque(false);
					d.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
				}
				
				return d;
			}
		});
		
		
		DefaultListModel listModel = new DefaultListModel();
		
		final int PREVIEW_SIZE = 20;
		final Color PREVIEW_COLOR = Color.black;
		
		listModel.addElement(
				new SolidCircularBrush("Solid Circular Brush", PREVIEW_SIZE)
		);
		
		listModel.addElement(
				new SolidEllipticalBrush(
						"Solid Elliptical Brush",
						PREVIEW_SIZE,
						0, 
						1.0, 
						PREVIEW_COLOR
				)
		);
		
		listModel.addElement(
				new RegularCircularBrush(
						"Regular Circular Brush", 
						PREVIEW_SIZE, 
						PREVIEW_COLOR
				)
		);
		
		listModel.addElement(
				new RegularEllipticalBrush(
						"Regular Elliptical Brush",
						PREVIEW_SIZE,
						0,
						1.0d,
						PREVIEW_COLOR
				)
		);
		
		listModel.addElement(
				new StagedBrush("Staged Brush", PREVIEW_SIZE, Color.black)
		);
		
		
		brushList.setModel(listModel);
		
		this.setLayout(new BorderLayout());
		this.add(colorBox, BorderLayout.NORTH);
		this.add(new JScrollPane(brushList), BorderLayout.CENTER);
	}
}
