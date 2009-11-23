package net.coobird.paint.layer.event;

import java.util.EventListener;

import net.coobird.paint.layer.ImageLayer;

public interface LayerChangeListener extends EventListener
{
	public void layerChanged(ImageLayer source, LayerChangeEventType event);
}
