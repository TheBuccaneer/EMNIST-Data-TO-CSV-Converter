# EMNIST-Data-TO-CSV-Converter
Converts the binarys downloadable at https://www.nist.gov/itl/iad/image-group/emnist-dataset into a *.csv file


Create Object of EmnistConverter and invoke method:

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
