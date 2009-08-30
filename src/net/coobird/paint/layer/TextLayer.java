package net.coobird.paint.layer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class TextLayer extends ImageLayer
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7077166428729557285L;
	
	private String text;
	private Font textFont;
	private Color textColor;
	private int locX;
	private int locY;
	
	private static final Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN, 12); 

	public TextLayer(BufferedImage image)
	{
		super(image);
	}

	public TextLayer(int width, int height)
	{
		this(width, height, "");
	}
	
	public TextLayer(int width, int height, String text)
	{
		this(width, height, text, DEFAULT_FONT);
	}

	public TextLayer(int width, int height, String text, Font f)
	{
		super(width, height);
		setText(text);
		setTextFont(f);
		
		int x = width / 2;
		int y = height / 2;
		
		setTextLocation(x, y);
		setTextColor(Color.BLACK);
		updateImage();
		
	}

	
	public void setText(String text)
	{
		this.text = text;
		updateImage();
	}
	
	public void setTextLocation(int x, int y)
	{
		this.locX = x;
		this.locY = y;
		updateImage();
	}
	
	public void setTextFont(Font f)
	{
		this.textFont = f;
		updateImage();
	}
	
	public void setTextColor(Color c)
	{
		this.textColor = c;
		updateImage();
	}
	

	
	@Override
	public BufferedImage getImage()
	{
		updateImage();
		// TODO Auto-generated method stub
		return super.getImage();
	}

	private void updateImage()
	{
		Graphics g = getGraphics();
		g.setFont(textFont);
		g.setColor(textColor);
		g.drawString(text, locX, locY);
		super.getThumbImage();
	}
}
