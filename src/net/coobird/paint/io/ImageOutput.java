package net.coobird.paint.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileFilter;

import net.coobird.paint.image.Canvas;

/**
 *  The {@code ImageOutput} class is the base abstract class for image output
 *  filters. Image format writing support will be in the form of subclasses
 *  extending the {@code ImageOutput} abstract class.
 * @author coobird
 *
 */
public abstract class ImageOutput
{
	private static List<FileFilter> filterList;
	private String name;
	
	static
	{
		filterList = new ArrayList<FileFilter>();
	}
	
	@SuppressWarnings("unused")
	private ImageOutput() {}
	
	/**
	 * 
	 * @param name
	 */
	protected ImageOutput(String name)
	{
		if (name != null)
		{
			this.name = name;
		}
		else
		{
			name = "Unidentified ImageOutput Filter";
		}
	}

	
	/**
	 * Writes the contents of the {@link Canvas} object to file specified in
	 * the {@link File} object in  the format defined by the {@code ImageOutput}
	 * filter. 
	 * @param c				The {@code Canvas} object to write.
	 * @param f				The {@code File} object to write to.
	 * @throws ImageInputOutputException			add description
	 */
	public abstract void write(Canvas c, File f)
		throws ImageInputOutputException;

	/**
	 * Writes the contents of the {@link Canvas} object to file specified in
	 * the {@link File} object in  the format defined by the {@code ImageOutput}
	 * filter. 
	 * @param c				The {@code Canvas} object to write.
	 * @param f				The {@code File} object to write to.
	 * @param format		A {@code String} holding the name of the format to
	 * 						write the image in.
	 * @throws ImageInputOutputException			add description
	 */
	public abstract void write(Canvas c, File f, String format)
		throws ImageInputOutputException;
	
	/**
	 * 
	 * @param f
	 * @return
	 */
	public abstract boolean supportsFile(File f);
	
	/**
	 * Returns a {@link List} of {@link FileFilter}s supported by this
	 * {@code ImageOutput}.
	 * @return				A {@code List} of {@code FileFilter}s supported
	 * 						by this {@code ImageOutput}.
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
