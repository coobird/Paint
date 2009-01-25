package net.coobird.paint.image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Canvas
{
	private List<ImageLayer> layers;
	private int width;
	private int height;
	// private LayerNamer --> names the new layer
	
	public Canvas(int width, int height)
	{
		// TODO
		// Determine appropriate List to use.
		layers = new ArrayList<ImageLayer>();
		this.width = width;
		this.height = height;
	}

	/**
	 * @return the width
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width)
	{
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height)
	{
		this.height = height;
	}

	/**
	 * @return the layers
	 */
	public List<ImageLayer> getLayers()
	{
		return layers;
	}
	
	public List<ImageLayer> getRenderOrder()
	{
		ImageLayer[] array = new ImageLayer[1];
		array = layers.toArray(array);
		
		List<ImageLayer> renderOrderList = new ArrayList<ImageLayer>();
		
		for (int i = array.length - 1; i >= 0; i--)
			renderOrderList.add(array[i]);
		
		return renderOrderList; 
	}
	
	/**
	 * Adds a specified layer to the canvas.
	 * @param layer	The ImageLayer to add to the canvas.
	 */
	public void addLayer(ImageLayer layer)
	{
		layers.add(layer);
	}
	
	/**
	 * Removes a specified layer from the canvas.
	 * @param layer	The ImageLayer to remove from the canvas.
	 */
	public void removeLayer(ImageLayer layer)
	{
		layers.remove(layer);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		String msg = "Canvas: items: " + layers.size();
		
		return msg;
	}
	
	
}
