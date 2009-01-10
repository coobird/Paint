package net.coobird.paint.io;

import java.io.File;

import net.coobird.paint.image.Canvas;

/**
 *  Base abstract class for ImageOutput
 * @author coobird
 *
 */
public abstract class ImageOutput
{
	public abstract void write(Canvas c, File f);
}
