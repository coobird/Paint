package net.coobird.paint;

import java.awt.AlphaComposite;

/**
 * An enumeration of the blending modes use for rendering for {@link Brush}
 * objects and {@link ImageLayer} objects.
 * @author coobird
 *
 */
public class BlendingMode
{
	/**
	 * An enumeration for the blending mode of {@link ImageLayer} objects.
	 * @author coobird
	 *
	 */
	public enum Layer
	{
		NORMAL (AlphaComposite.SrcOver),
		SRC_OVER (AlphaComposite.SrcOver),
		SRC_IN (AlphaComposite.SrcIn),
		SRC_OUT (AlphaComposite.SrcOut),
		DST_OVER (AlphaComposite.DstOver),
		DST_IN (AlphaComposite.DstIn),
		DST_OUT (AlphaComposite.DstOut)
		;
		
		private AlphaComposite ac;
		
		private Layer(AlphaComposite ac)
		{
			this.ac = ac;
		}
		
		public AlphaComposite getComposite()
		{
			return ac;
		}
	}

	/**
	 * An enumeration for the blending mode of {@link Brush} objects.
	 * @author coobird
	 *
	 */
	public enum Brush
	{
		NORMAL (AlphaComposite.SrcOver),
		ERASER (AlphaComposite.DstOut)
		;
		
		private AlphaComposite ac;
		
		private Brush(AlphaComposite ac)
		{
			this.ac = ac;
		}
		
		public AlphaComposite getComposite()
		{
			return ac;
		}
	}
}
