package net.coobird.paint.image;

public class ImageRendererFactory
{
	private static ImageRenderer renderer;
	
	/**
	 * Disallow instantiation of this class.
	 */
	private ImageRendererFactory() {}
	
	/**
	 * Returns an instance of ImageRenderer.
	 * (Implementation specific -- this factory returns a DefaultImageRenderer.)
	 * @return	An instance of ImageRenderer.
	 */
	public static ImageRenderer getInstance()
	{
		if (renderer == null)
		{
			renderer = new DefaultImageRenderer();
		}
		
		return renderer;
	}
}
