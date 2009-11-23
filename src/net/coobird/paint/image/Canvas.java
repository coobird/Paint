package net.coobird.paint.image;

import java.awt.Rectangle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.coobird.paint.layer.ImageLayer;

/**
 * 
 * @author coobird
 *
 */
public final class Canvas implements Serializable
{
	private static final long serialVersionUID = 1975814667741951677L;

	private transient List<ImageLayer> layerList;
	private int width;
	private int height;
	private double zoom;
	private int numLayers;
	
	/**
	 * Cannot instantiate a {@code Canvas} object without specification of size.
	 */
	@SuppressWarnings("unused")
	private Canvas() {};
	
	/**
	 * Instantiates a {@code Canvas} object with the specified dimensions.
	 * @param width			The width of the canvas in pixels.
	 * @param height		The height of the canvas in pixels.
	 */
	public Canvas(int width, int height)
	{
		layerList = new ArrayList<ImageLayer>();
		
		setWidth(width);
		setHeight(height);
		setZoom(1);
		
		numLayers = 1;
	}
	
	/**
	 * Returns the layer name to use for the next ImageLayer.
	 * @return
	 */
	public String getNextLayerName()
	{
		return "Layer " + numLayers;
	}

	/**
	 * @return the zoom
	 */
	public double getZoom()
	{
		return zoom;
	}

	/**
	 * @param zoom the zoom to set
	 * @throws IllegalArgumentException
	 */
	public void setZoom(double zoom)
	{
		if (zoom <= 0)
		{
			String msg = "Zoom must be greater than 0.";
			throw new IllegalArgumentException(msg);
		}
		
		this.zoom = zoom;
	}

	/**
	 * Returns the width of the {@link Canvas} object.
	 * @return 				The width of the canvas in pixels.
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * Sets the width of the {@link Canvas} object.
	 * @param width 		The width in pixels to set the canvas to.
	 * @throws IllegalArgumentException
	 */
	public void setWidth(int width)
	{
		if (width <= 0)
		{
			String msg = "Width must be at least 1 pixel.";
			throw new IllegalArgumentException(msg);
		}
		
		this.width = width;
	}

	/**
	 * Returns the height of the {@link Canvas} object.
	 * @return 				The height in pixels of the canvas.
	 */
	public int getHeight()
	{
		return height;
	}

	/**
	 * Sets the height of the {@link Canvas} object.
	 * @param height 		The height in pixels to set the canvas to.
	 */
	public void setHeight(int height)
	{
		if (height <= 0)
		{
			String msg = "Height must be at least 1 pixel.";
			throw new IllegalArgumentException(msg);
		}
		
		this.height = height;
	}
	
	/**
	 * 
	 * @param width
	 * @param height
	 */
	public void setSize(int width, int height)
	{
		setWidth(width);
		setHeight(height);
	}
	
	/**
	 * Returns a {@link List} of {@link ImageLayer} objects which are contained
	 * in the {@link Canvas} object.
	 * @return				A {@code List} containing {@code ImageLayer} objects
	 * 						contained in the {@code Canvas} object.
	 */
	public List<ImageLayer> getLayers()
	{
		return layerList;
	}
	
	/**
	 * <p>
	 * Returns a {@link List} containing the {@link ImageLayer} objects present
	 * in the {@link Canvas} object, ordered by the rendering order used by
	 * an {@link ImageRenderer} during rendering.
	 * </p>
	 * <p>
	 * The returned {@code List} is in reverse order of the {@code List}
	 * returned by calling the {@link getLayers()} method.
	 * </p>
	 * @return				A {@code List} containing {@code ImageLayer} objects
	 * 						in the rendering order.
	 */
	public List<ImageLayer> getRenderOrder()
	{
		List<ImageLayer> renderOrderList = new ArrayList<ImageLayer>(layerList);
		Collections.reverse(renderOrderList);
		
		return renderOrderList; 
	}
	
	/**
	 * Adds a new {@link ImageLayer} to the {@code Canvas} with a default
	 * assigned image layer caption.
	 */
	public void addLayer()
	{
		ImageLayer il = new ImageLayer(width, height);
		il.setCaption(getNextLayerName());
		
		addLayer(il);
	}
	
	/**
	 * Adds a specified layer to the canvas.
	 * @param layer			The ImageLayer to add to the canvas.
	 */
	public void addLayer(ImageLayer layer)
	{
		addLayer(layer, layerList.size());
	}

	/**
	 * Adds a specified layer to the canvas.
	 * @param layer			The ImageLayer to add to the canvas.
	 */
	public void addLayer(ImageLayer layer, int index)
	{
		if (layer == null)
		{
			String msg = "Cannot add an ImageLayer that is null.";
			throw new NullPointerException(msg);
		}
		
		/*
		 * TODO determine whether to silently eat the problematic index, or
		 * throw an exception, such as IndexOutOfBoundsException
		 */
		if (index < layerList.size())
		{
			layerList.add(index, layer);
		}
		else
		{
			layerList.add(layer);
		}
		
		numLayers++;
	}
	
	/**
	 * Removes a specified layer from the canvas.
	 * @param layer			The ImageLayer to remove from the canvas.
	 */
	public void removeLayer(ImageLayer layer)
	{
		if (layer == null)
		{
			String msg = "Cannot remove a null ImageLayer.";
			throw new NullPointerException(msg);
		}
		
		layerList.remove(layer);
	}
	
	/**
	 * Move a layer from one position to another.
	 * @param fromIndex		The index of the layer to more.
	 * @param toIndex		The index to which the layer is to be moved.
	 */
	public void moveLayer(int fromIndex, int toIndex)
	{
		ImageLayer il = layerList.remove(fromIndex);
		layerList.add(toIndex, il);
	}
	
	/**
	 * Resizes the canvas to fit all layers.
	 * TODO Test this method to see if this works correctly.
	 */
	public void pack()
	{
		Rectangle r = new Rectangle();
		
		for (ImageLayer il : layerList)
		{
			r.add(new Rectangle(
					il.getX(),
					il.getY(),
					il.getWidth(),
					il.getHeight()
			));
		}
		
		setSize(r.width, r.height);
	}
	
	/**
	 * Clears the current contents of the {@link Canvas} object.
	 */
	public void clear()
	{
		layerList.clear();
	}
	
	/**
	 * 
	 */
	private void readObject(ObjectInputStream in)
		throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		layerList = new ArrayList<ImageLayer>();
	}

	/**
	 * Returns a {@code String} representation of the {@code Canvas} object. 
	 * @return				A {@code String} representation of the
	 * 						{@code Canvas} object.
	 */
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		String msg = "Canvas: items: " + layerList.size() +
				" " + Arrays.toString(layerList.toArray());
		
		return msg;
	}
}
