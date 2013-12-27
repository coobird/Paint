package net.coobird.paint.io;

import java.io.File;
import java.util.ArrayList;
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
	private static List<FileFilter> filterList;
	private String name;
	
	static
	{
		filterList = new ArrayList<FileFilter>();
	}

	@SuppressWarnings("unused")
	private ImageInput() {}
	
	/**
	 * 
	 * @param name
	 */
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
	 * @throws ImageInputOutputException
	 */
	public abstract Canvas read(File f)
		throws ImageInputOutputException;
	
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
	 * 
	 * @param f
	 */
	protected static void addFilter(FileFilter f)
	{
		filterList.add(f);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return this.name;
	}
}
