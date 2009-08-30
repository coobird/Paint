package net.coobird.paint.layer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

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
public class ReferenceLayer extends ImageLayer
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
		
		if (layer == null)
		{
			refreshFields();
		}
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
	
	
}
