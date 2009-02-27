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
		/**
		 * Normal blending mode for rendering {@link ImageLayer} objects.
		 */
		NORMAL (AlphaComposite.SrcOver),
		
		/**
		 * Blending mode using {@link AlphaComposite.SrcOver} for rendering
		 * {@link ImageLayer} objects.
		 * Expected only for temporary use during development.
		 */
		SRC_OVER (AlphaComposite.SrcOver),
		
		/**
		 * Blending mode using {@link AlphaComposite.SrcIn} for rendering
		 * {@link ImageLayer} objects.
		 * Expected only for temporary use during development.
		 */
		SRC_IN (AlphaComposite.SrcIn),
		
		/**
		 * Blending mode using {@link AlphaComposite.SrcOut} for rendering
		 * {@link ImageLayer} objects.
		 * Expected only for temporary use during development.
		 */
		SRC_OUT (AlphaComposite.SrcOut),
		
		/**
		 * Blending mode using {@link AlphaComposite.DstOver} for rendering
		 * {@link ImageLayer} objects.
		 * Expected only for temporary use during development.
		 */
		DST_OVER (AlphaComposite.DstOver),
		
		/**
		 * Blending mode using {@link AlphaComposite.DstIn} for rendering
		 * {@link ImageLayer} objects.
		 * Expected only for temporary use during development.
		 */
		DST_IN (AlphaComposite.DstIn),
		
		/**
		 * Blending mode using {@link AlphaComposite.DstOut} for rendering
		 * {@link ImageLayer} objects.
		 * Expected only for temporary use during development.
		 */
		DST_OUT (AlphaComposite.DstOut)
		;
		
		/**
		 * {@link AlphaComposite} used for the {@link ImageLayer}.
		 */
		private AlphaComposite ac;
		
		/**
		 * Instantiates an {@code enum} for the {@link ImageLayer} with the
		 * specified {@link AlphaComposite} object.
		 * @param ac		The {@code AlphaComposite} for this enumeration.
		 */		private Layer(AlphaComposite ac)
		{
			this.ac = ac;
		}
		
		/**
		 * Returns an {@link AlphaComposite} object associated with this
		 * enumeration.
		 * @return			An {@code AlphaComposite} object associated with
		 * 					this enumeration.
		 */
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
		/**
		 * Normal blending mode for rendering {@link Brush} objects.
		 */
		NORMAL (AlphaComposite.SrcOver),
		
		/**
		 * Blending mode for rendering a {@link Brush} objects as an eraser.
		 */
		ERASER (AlphaComposite.DstOut)
		;
		
		/**
		 * {@link AlphaComposite} used for the {@link Brush}.
		 */
		private AlphaComposite ac;
		
		/**
		 * Instantiates an {@code enum} for the {@link Brush} with the
		 * specified {@link AlphaComposite} object.
		 * @param ac		The {@code AlphaComposite} for this enumeration.
		 */
		private Brush(AlphaComposite ac)
		{
			this.ac = ac;
		}
		
		/**
		 * Returns an {@link AlphaComposite} object associated with this
		 * enumeration.
		 * @return			An {@code AlphaComposite} object associated with
		 * 					this enumeration.
		 */
		public AlphaComposite getComposite()
		{
			return ac;
		}
	}
}
