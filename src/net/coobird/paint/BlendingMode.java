package net.coobird.paint;

import java.awt.AlphaComposite;

public enum BlendingMode
{
	LAYER_NORMAL (AlphaComposite.SrcOver),
	LAYER_SRC_OVER (AlphaComposite.SrcOver),
	LAYER_SRC_IN (AlphaComposite.SrcIn),
	LAYER_SRC_OUT (AlphaComposite.SrcOut),
	LAYER_DST_OVER (AlphaComposite.DstOver),
	LAYER_DST_IN (AlphaComposite.DstIn),
	LAYER_DST_OUT (AlphaComposite.DstOut),
	BRUSH_NORMAL (AlphaComposite.SrcOver),
	BRUSH_ERASER (AlphaComposite.DstOut)
	;
	
	
	private AlphaComposite ac;
	
	private BlendingMode(AlphaComposite ac)
	{
		this.ac = ac;
	}
	
	public AlphaComposite getComposite()
	{
		return ac;
	}
}
