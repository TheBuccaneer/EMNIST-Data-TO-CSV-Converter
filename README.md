# EMNIST-Data-TO-CSV-Converter
Converts the binarys downloadable at https://www.nist.gov/itl/iad/image-group/emnist-dataset into a *.csv file


Create Object of EmnistConverter and invoke method 
"convertWithOUTMapping(final String labelInputFile, final String imageInputFile, final String outputFileName)" OR 
"public void convertWithMapping(final String labelInputFile, final String imageInputFile, final String outputFileName)".

Converter needs path + filenames as arguments. For more information about parameters plz look at src code of the EmnistConverter class. 
