package net.coobird.paint;

import java.awt.AlphaComposite;

public enum BlendingMode
{
	LAYER_NORMAL (AlphaComposite.SrcOver),
	LAYER_SRC_OVER (AlphaComposite.SrcOver);
	
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
