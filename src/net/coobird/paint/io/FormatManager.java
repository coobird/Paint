package net.coobird.paint.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author coobird
 *
 */
public final class FormatManager
{
	/**
	 * Manage the supported formats
	 * 
	 * What is this class really for?
	 * Perhaps tell it a desired format and return an approriate I/O filter?
	 */
	
	private static List<ImageInput> inputList;
	private static List<ImageOutput> outputList;
	
	static
	{
		inputList = new ArrayList<ImageInput>();
		outputList = new ArrayList<ImageOutput>();
		
		inputList.add(new DefaultImageInput());
		inputList.add(new JavaSupportedImageInput());
		
		outputList.add(new DefaultImageOutput());
		outputList.add(new JavaSupportedImageOutput());
	}
	
	/**
	 * Returns an {@link ImageInput} object which can handle the file format of
	 * {@link File} object.
	 * @param f				{@code File} object for which to find an
	 * 						{@code ImageInput} for.
	 * @return				The {@code ImageInput} which can read the given
	 * 						file, or {@code null} if an adaquate
	 * 						{@code ImageInput} cannot be found.
	 */
	public static ImageInput getImageInput(File f)
	{
		for (ImageInput imgInput : inputList)
		{
			if (imgInput.supportsFile(f))
			{
				return imgInput;
			}
		}
		
		return null;
	}
	
	public static ImageOutput getImageOutput(File f)
	{
		for (ImageOutput imgOutput : outputList)
		{
			if (imgOutput.supportsFile(f))
			{
				return imgOutput;
			}
		}
		return null;		
	}
}
