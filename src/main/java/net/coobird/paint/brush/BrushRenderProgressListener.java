package net.coobird.paint.brush;

public interface BrushRenderProgressListener
{
	public void drawComplete();
	public void drawProgress(int stepsLeft);
}