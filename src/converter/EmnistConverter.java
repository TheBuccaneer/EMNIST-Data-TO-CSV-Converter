package converter;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class EmnistConverter
{

	/**
	 * 
	 * @param labelInputFile Path to label file. For instance:
	 *                       /home/thomas/Desktop/EMNIST
	 *                       Dataset/emnist-balanced-train-labels-idx1-ubyte
	 * @param imageInputFile Path to image file. For instance:
	 *                       /home/thomas/Desktop/EMNIST
	 *                       Dataset/emnist-balanced-train-images-idx3-ubyte
	 * @param outputFileName Path to outputfile, which will be created as *.csv file
	 */
	public void convert(final String labelInputFile, final String imageInputFile, final String outputFileName)
	{
		try (DataInputStream labelStream = new DataInputStream(new FileInputStream(new File(labelInputFile)));
				DataInputStream imageStream = new DataInputStream(new FileInputStream(new File(imageInputFile)));
				BufferedWriter bfw = new BufferedWriter(new FileWriter(outputFileName + ".csv")))
		{

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
				int value2 = labelStream.readByte();
				bfw.write(value2 + "");
				bfw.write(",");

				for (int j = 0; j < length; j++)
				{
					int value = imageStream.readByte();
					bfw.write((value < 0 ? 256 + value : value) + "");
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

	public static void main(String[] args)
	{
		EmnistConverter ec = new EmnistConverter();
		ec.convert("/home/thomas/Desktop/EMNIST Dataset/emnist-balanced-train-labels-idx1-ubyte",
				"/home/thomas/Desktop/EMNIST Dataset/emnist-balanced-train-images-idx3-ubyte",
				"/home/thomas/Desktop/EMNIST_DATA_Balanced");
		System.out.println("Done");
	}
}
