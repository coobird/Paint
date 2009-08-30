package net.coobird.paint.layer;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import net.coobird.paint.BlendingMode;
import net.coobird.paint.image.Canvas;
import net.coobird.paint.image.ImageRenderer;

/*************************
 * TODO Clean up this class!!
 */

/**
 * Represents a image layer within the Untitled Image Manipulation Application.
 * 
 * @author coobird
 *
 */
public class ImageLayer implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6969332672194331399L;
	
	/**
	 * The scaling factor to shrink the actual image of the layer by when the
	 * thumbnail is displayed. The scaling factor of 8 means that the thumbnail
	 * is 1/8 the size of the actual image in the image layer.
	 */
	protected static final int THUMBNAIL_SCALE = 8;
	
	/**
	 * The default image type used for the BufferedImage which contains the
	 * image information for this image layer.
	 */
	protected static final int DEFAULT_IMAGE_TYPE = BufferedImage.TYPE_INT_ARGB;
	
	protected transient BufferedImage image;
	protected transient BufferedImage thumbImage;
	protected transient Graphics2D g;
	protected String caption;
	protected boolean visible = true;
	protected boolean locked = false;
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	protected float alpha = 1.0f;
	protected BlendingMode.Layer mode = BlendingMode.Layer.NORMAL;
	
	/**
	 * {@code ImageLayer} must be constructed with the width and height
	 * parameters specified.
	 * 
	 * @see ImageLayer(int,int)
	 */
	@SuppressWarnings("unused")
	private ImageLayer() {};
	
	/**
	 * Instantiate a new {@code ImageLayer}.
	 * 
	 * @param width		The width of the ImageLayer.
	 * @param height	The height of the ImageLayer.
	 */
	public ImageLayer(BufferedImage image)
	{
		initInstance();
		setImage(image);

		renderThumbnail();
	}
	
	/**
	 * Instantiate a new {@code ImageLayer} from an existing image.
	 * 
	 * @param width		The width of the ImageLayer.
	 * @param height	The height of the ImageLayer.
	 */
	public ImageLayer(int width, int height)
	{
		initInstance();

		image = new BufferedImage(
				width,
				height,
				DEFAULT_IMAGE_TYPE
		);

		setImage(image);
	
		renderThumbnail();
	}
	
	/**
	 * Initializes the instance of {@code ImageLayer}.
	 */
	private void initInstance()
	{
		this.x = 0;
		this.y = 0;
		this.setCaption("Untitled Layer");
	}

	/**
	 * Creates a thumbnail image.
	 */
	private void renderThumbnail()
	{
		int thumbWidth = this.width / THUMBNAIL_SCALE;
		int thumbHeight = this.height / THUMBNAIL_SCALE;
		
		/*
		 * If either the thumbnail width or height is 0, then upon rendering
		 * the thumbnail, a IllegalArgumentException will be thrown by trying
		 * to instantiate a BufferedImage with any dimension that is 0.
		 * This check will prevent illegal sized images.
		 */
		if (thumbWidth == 0 || thumbHeight == 0)
		{
			thumbWidth = this.width;
			thumbHeight = this.height;
		}
		
		thumbImage = new BufferedImage(
				thumbWidth,
				thumbHeight,
				DEFAULT_IMAGE_TYPE
		);
		
		Graphics2D g = thumbImage.createGraphics();
		
		/*
		 *  Use AlphaComposite.Src as the Composite mode to draw source image
		 *  directly onto thumbImage.
		 *  Else, the original image will not be overwritten, and if there was
		 *  change in alpha, it may not show up.
		 */
		g.setComposite(AlphaComposite.Src);
		
		g.drawImage(
				image,
				0,
				0,
				thumbImage.getWidth(),
				thumbImage.getHeight(),
				null
		);
		
		g.dispose();		
	}
	
	/**
	 * Updates the state of the ImageLayer.
	 */
	public void update()
	{
		// Perform action to keep imagelayer up to date
		// Here, renderThumbnail to synch the image and thumbImage representation
		renderThumbnail();
	}
	
	/**
	 * Returns the {@link Graphics2D} object associated with the image contained
	 * in the {@code ImageLayer} object.
	 * 
	 * @return				The {@code Graphics2D} object associated with the
	 * 						image contained in the {@code ImageLayer} object.
	 */
	public Graphics2D getGraphics()
	{
		return g;
	}

	/**
	 * Checks if this {@code ImageLayer} is set as visible.
	 * 
	 * When an {@code ImageLayer} object is marked as visible, the image layer
	 * will be rendered when it is included in a {@link Canvas} object which is
	 * being rendered by an {@link ImageRenderer}.
	 * 
	 * @return				{@code true} if the {@code ImageLayer} object is
	 * 						visible, {@code false} otherwise.
	 */
	public boolean isVisible()
	{
		return visible;
	}

	/**
	 * TODO
	 * @return the image
	 */
	public BufferedImage getImage()
	{
		return image;
	}

	/**
	 * TODO
	 * @param image the image to set
	 */
	public void setImage(BufferedImage image)
	{
		/*
		 * If the type of the given BufferedImage is not the DEFAULT_IMAGE_TYPE,
		 * then create a new BufferedImage with the DEFAULT_IMAGE_TYPE, and
		 * draw the given image onto the new BufferedImage.
		 */
		if (image.getType() == DEFAULT_IMAGE_TYPE)
		{
			this.image = image;
			this.width = image.getWidth();
			this.height = image.getHeight();
			this.g = image.createGraphics();
			
			renderThumbnail();
		}
		else
		{
			this.width = image.getWidth();
			this.height = image.getHeight();
			
			this.image = new BufferedImage(
					this.width,
					this.height,
					DEFAULT_IMAGE_TYPE
			);
			this.g = this.image.createGraphics();
			
			g.drawImage(image, 0, 0, null);
			
			renderThumbnail();
		}
	}

	/**
	 * TODO
	 * @return the thumbImage
	 */
	public BufferedImage getThumbImage()
	{
		return thumbImage;
	}

	/**
	 * TODO
	 * @param thumbImage the thumbImage to set
	 */
	public void setThumbImage(BufferedImage thumbImage)
	{
		this.thumbImage = thumbImage;
	}

	/**
	 * Sets the visibility of this ImageLayer.
	 * 
	 * @param visible	The visibility of this ImageLayer.
	 */
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
	
	/**
	 * Returns the caption of the ImageLayer.
	 * 
	 * @return	The caption of the ImageLayer.
	 */
	public String getCaption()
	{
		return caption;
	}

	/**
	 * Sets the caption of the ImageLayer.
	 * 
	 * @param caption	The caption to set the ImageLayer to.
	 */
	public void setCaption(String caption)
	{
		this.caption = caption;
	}

	/**
	 * Returns the width of the ImageLayer.
	 * 
	 * @return	The width of the ImageLayer.
	 */
	public int getWidth()
	{
		return image.getWidth();
	}

	/**
	 * Returns the height of the ImageLayer.
	 * 
	 * @return	The height of the ImageLayer.
	 */
	public int getHeight()
	{
		return image.getHeight();
	}

	/**
	 * @return the x
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x)
	{
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y)
	{
		this.y = y;
	}
	
	/**
	 * Sets the ImageLayer offset.
	 * @param x
	 * @param y
	 */
	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * @return the alpha
	 */
	public float getAlpha()
	{
		return alpha;
	}

	/**
	 * <p>
	 * Sets the transparency of his image layer.
	 * </p>
	 * <p>
	 * The value for transparency must be in the range of {@code 0.0f} to 
	 * {@code 1.0f}, where {@code 0.0f} is completely transparent, and 
	 * {@code 1.0f} is completely opaque.
	 * </p>
	 * 
	 * @param alpha 	The transparency level to set the layer to, in the range
	 * 					of {@code 0.0f} to {@code 1.0f}, where {@code 0.0f} is
	 * 					completely transparent, and {@code 1.0f} is completely
	 * 					opaque.
	 * @throws IllegalArgumentException		Thrown when the {@code alpha} is
	 * 										specified outside the range of
	 * 										{@code 0.0f} to {@code 1.0f}.
	 */	
	public void setAlpha(float alpha)
	{
		if (alpha > 1.0f || alpha < 0.0f)
		{
			throw new IllegalArgumentException(
					"Alpha must be in the range of 0.0f to 1.0f."
			);
		}
		
		this.alpha = alpha;
	}
	
	/**
	 * Returns the blending {@link AlphaComposite} used for this layer.
	 * @return
	 */
	public AlphaComposite getAlphaComposite()
	{
		return AlphaComposite.getInstance(
				mode.getComposite().getRule(),
				alpha
		);
	}

	/**
	 * Returns the blending mode for this layer.
	 * 
	 * @return the mode
	 */
	public BlendingMode.Layer getMode()
	{
		return mode;
	}

	/**
	 * Sets the blending mode for this layer.
	 * 
	 * @param mode the mode to set
	 */
	public void setMode(BlendingMode.Layer mode)
	{
		this.mode = mode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		String msg = "Image Layer: " + this.caption + 
			", x: " + this.x + ", y: " + this.y + 
			", width: " + this.width + ", height: " + this.height; 
		
		return msg;
	}

	/**
	 * <p>
	 * Returns the default image type of layers of this class.
	 * </p>
	 * <p>
	 * The value returned is a class constant from the {@link BufferedImage} 
	 * class which defines the type of an image.
	 * </p>
	 * 
	 * @return			The default image type of this class.
	 */
	public static int getDefaultType()
	{
		return DEFAULT_IMAGE_TYPE;
	}

	/**
	 * Sets whether the layer is to be locked from editing.
	 * 
	 * @param locked	When {@code true}, the layer will be locked from
	 * 					editing.
	 */
	public void setLocked(boolean locked)
	{
		this.locked = locked;
	}

	/**
	 * Returns whether the layer is locked from editing.
	 * 
	 * @return			{@code true} if the layer is locked, {@code false}
	 * 					if not.
	 */
	public boolean isLocked()
	{
		return locked;
	}
}
