package net.coobird.paint.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author coobird
 *
 */
public class FormatManager
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
		//TODO implement!
		return null;		
	}
}
