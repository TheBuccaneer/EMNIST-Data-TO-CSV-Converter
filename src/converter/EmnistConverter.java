package converter;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EmnistConverter
{

	private void convert(final String labelInputFile, final String imageInputFile, final String outputFileName,
			Map<Integer, String> map)
	{
		try (DataInputStream labelStream = new DataInputStream(new FileInputStream(new File(labelInputFile)));
				DataInputStream imageStream = new DataInputStream(new FileInputStream(new File(imageInputFile)));
				BufferedWriter bfw = new BufferedWriter(new FileWriter(outputFileName + ".csv")))
		{

			final boolean IS_MAP_EMPTY = map.isEmpty();

			// No need for magic number
			labelStream.readInt();
			imageStream.readInt();

			// number of images
			final int labelNumberOfImages = labelStream.readInt();
			final int ImageNumberOfImages = imageStream.readInt();
			if (labelNumberOfImages != ImageNumberOfImages)
				throw new Exception("Numbers are not even");

			// image width * image heigth
			final int length = imageStream.readInt() * imageStream.readInt();

			for (int i = 0; i < labelNumberOfImages; i++)
			{
				int labelValue = labelStream.readByte();

				String valueToWrite = IS_MAP_EMPTY ? labelValue + "" : map.get(labelValue);

				bfw.write(valueToWrite);
				bfw.write(",");

				for (int j = 0; j < length; j++)
				{
					int pixelValue = imageStream.readByte();
					bfw.write((pixelValue < 0 ? 256 + pixelValue : pixelValue) + "");
					bfw.write(",");
				}
				bfw.write("0");
				bfw.write("\n");
			}
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * Invoke this method, if you don't want balanced or merged data set mappend OR
	 * if you use any other dataset like class or letter
	 * 
	 * @param labelInputFile Path to label file. For instance:
	 *                       /home/thomas/Desktop/EMNIST
	 *                       Dataset/emnist-balanced-train-labels-idx1-ubyte
	 * @param imageInputFile Path to image file. For instance:
	 *                       /home/thomas/Desktop/EMNIST
	 *                       Dataset/emnist-balanced-train-images-idx3-ubyte
	 * @param outputFileName Path to outputfile, which will be created as *.csv file
	 * 
	 */
	public void convertWithOUTMapping(final String labelInputFile, final String imageInputFile,
			final String outputFileName)
	{
		this.convert(labelInputFile, imageInputFile, outputFileName, new HashMap<Integer, String>());
	}

	/**
	 * Invoke this method, if you want the balanced or merged data set to get mapped
	 * from class numbers to letters. ONLY FOR BALANCED OR MERGED!!!!
	 * 
	 * @param labelInputFile Path to label file. For instance:
	 *                       /home/thomas/Desktop/EMNIST
	 *                       Dataset/emnist-balanced-train-labels-idx1-ubyte
	 * @param imageInputFile Path to image file. For instance:
	 *                       /home/thomas/Desktop/EMNIST
	 *                       Dataset/emnist-balanced-train-images-idx3-ubyte
	 * @param outputFileName Path to outputfile, which will be created as *.csv file
	 * 
	 */
	public void convertWithMapping(final String labelInputFile, final String imageInputFile,
			final String outputFileName)
	{
		final int AMOUNT_CLASSES = 47;
		// EMNIST: an extension of MNIST to handwritten letters Page 5 Fig. 2
		final Map<Integer, String> BALANCED = new HashMap<Integer, String>();
		String[] smallLetters = { "a", "b", "d", "e", "f", "g", "h", "n", "q", "r", "t", };

		for (int i = 0; i < AMOUNT_CLASSES; i++)
			if (i < 10)
				BALANCED.put(i, i + "");
			else if (i < 36)
				BALANCED.put(i, ((char) (i + 55)) + "");
			else
				BALANCED.put(i, smallLetters[i - 36]);

		this.convert(labelInputFile, imageInputFile, outputFileName, BALANCED);
	}

	public static void main(String[] args)
	{
		EmnistConverter ec = new EmnistConverter();
//		ec.convertWithMapping("/home/thomas/Desktop/EMNIST Dataset/emnist-bymerge-test-labels-idx1-ubyte",
//				"/home/thomas/Desktop/EMNIST Dataset/emnist-bymerge-test-images-idx3-ubyte",
//				"/home/thomas/Desktop/EMNIST-MERGE-test-output");
//		ec.convertWithMapping("/home/thomas/Desktop/EMNIST Dataset/emnist-bymerge-train-labels-idx1-ubyte",
//				"/home/thomas/Desktop/EMNIST Dataset/emnist-bymerge-train-images-idx3-ubyte",
//				"/home/thomas/Desktop/EMNIST-MERGE-train-output");
		System.out.println("Done");
	}
}
