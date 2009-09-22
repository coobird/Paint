package net.coobird.paint.layer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import net.coobird.paint.layer.event.LayerChangeEventType;
import net.coobird.paint.layer.event.LayerChangeListener;

/*
 * 
 * reference to a deleted layer --> how to detect a layer is delted?
 * -> use a listener that indicates that layer is going to be deleted?
 * 
 * this can lead to memory leaks where reference to the origianl layer
 * which ahs been deleted is retained.
 * 
 * 
 */

/**
 * Representation of a reference layer, which is a reference to another layer.
 * 
 * The current implementation will allow editing of the reference layer which
 * will also apply the changes to the original image.
 * 
 * Consider:
 * --> What to do when the original layer was removed?
 * --> Create a new layer from Reference layer?
 * 
 * Problems:
 * --> When to perform an update on the thumbnail?
 * --> Changing the referenced layer won't immediately change the this thumbnail
 * and vice versa. <<Check behavior from driver.>>
 * 
 * 
 * @author coobird
 *
 */
public class ReferenceLayer extends ImageLayer implements LayerChangeListener
{
	
	private ImageLayer referencedLayer = null;
	
	public ReferenceLayer(ImageLayer layer)
	{
		super(layer.getWidth(), layer.getHeight());
		setReferencedLayer(layer);
	}

	public void setReferencedLayer(ImageLayer layer)
	{
		this.referencedLayer = layer;
		setImage(layer.getImage());
		this.setCaption("reference of " + layer.getCaption());
		layer.addLayerChangeListener(this);
		
//		if (layer == null)
//		{
//			refreshFields();
//		}
	}

	public ImageLayer getReferencedLayer()
	{
		return referencedLayer;
	}
	
	private void refreshFields()
	{
		BufferedImage img = new BufferedImage(
				width, height, DEFAULT_IMAGE_TYPE
		);
		
		Graphics g = img.createGraphics();
		
		int msgX = 10;
		int msgY = 10;
		
		g.drawString("No reference", msgX, msgY);
		
		setImage(img);
	}

	
	@Override
	public Graphics2D getGraphics()
	{
		// TODO Auto-generated method stub
		if (referencedLayer != null)
		{
			referencedLayer.update();
		}
		
		return super.getGraphics();
	}

	@Override
	public void layerChanged(ImageLayer source, LayerChangeEventType event)
	{
		if (source == referencedLayer)
		{
			// Reference changed, so update this layer's contents.
			update();
		}
	}
}
