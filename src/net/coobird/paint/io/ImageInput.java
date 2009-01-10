package net.coobird.paint.io;

import java.io.File;

import net.coobird.paint.image.Canvas;

/**
 * Base abstract class for ImageInput.
 * @author coobird
 *
 */
public abstract class ImageInput
{
	public abstract Canvas read(File f);
}
