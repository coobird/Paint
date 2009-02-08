package net.coobird.paint.io;

import java.io.File;
import java.util.List;

import javax.swing.filechooser.FileFilter;

import net.coobird.paint.image.Canvas;

/**
 *  The {@code ImageInput} class is the base abstract class for image input
 *  filters. Image format reading support will be in the form of subclasses
 *  extending the {@code ImageInput} abstract class.
 * @author coobird
 *
 */
public abstract class ImageInput
{
	protected List<FileFilter> filterList;
	private String name;

	@SuppressWarnings("unused")
	private ImageInput() {}
	
	protected ImageInput(String name)
	{
		if (name != null)
		{
			this.name = name;
		}
		else
		{
			name = "Unidentified ImageInput Filter";
		}
	}
	
	/**
	 * 
	 * @param f
	 * @return
	 */
	public abstract Canvas read(File f);
	
	/**
	 * 
	 * @param f
	 * @return
	 */
	public abstract boolean supportsFile(File f);
	
	/**
	 * Returns the file filter associated with this {@code ImageInput}.
	 * @return
	 */
	public List<FileFilter> getFileFilters()
	{
		return filterList;
	}
	
	/**
	 * Gets the file extension of the given {@link File} object.
	 * @param f				The {@code File} object to determine the extension
	 * 						for.
	 * @return				The file extension.
	 */

	protected static String getExtension(File f)
	{
		int lastIndex = f.getName().lastIndexOf('.');
		
		if (lastIndex == -1)
		{
			return "";
		}
		
		return f.getName().substring(lastIndex + 1);
	}
}
