package net.coobird.paint.io;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ImageFileFilter extends FileFilter
{
	private String description;
	private String[] extensions;
	
	public ImageFileFilter(String description, String[] extensions)
	{
		this.description = description;
		this.extensions = extensions.clone();
	}
	
	@Override
	public boolean accept(File f)
	{
		if (f.isDirectory())
		{
			return true;
		}
		else
		{
			String fileExtension = getExtension(f).toLowerCase();
			
			for (String ext : extensions)
			{
				if (fileExtension.equals(ext))
				{
					return true;
				}
			}
		}
		
		return false;
	}

	@Override
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * Returns the default extension for this {@code ImageFileFilter} object.
	 * @return
	 */
	public String getDefaultExtension()
	{
		if (extensions[0] == null)
			return "";
		
		return extensions[0];
	}
	
	/*
	 * TODO Refactor?
	 * Possibly refactor this method to another util method?
	 * This method is being called by other ImageInput/ImageOutput classes.
	 */
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
