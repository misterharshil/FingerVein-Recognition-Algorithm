package com.FingerVeinScanner;
import java.awt.*;
import java.net.URL; 

public class Picture extends SimplePicture 
{
	/////////////////////////// Static Variables //////////////////////////////

	// Different axes available to flip a picture.
	public static final int HORIZONTAL = 1;
	public static final int VERTICAL = 2;
	public static final int FORWARD_DIAGONAL = 3;
	public static final int BACKWARD_DIAGONAL = 4;


	//////////////////////////// Constructors /////////////////////////////////

	/**
	 * A constructor that takes no arguments.
	 */
	public Picture () {
		super();  
	}

	/**
	 * Creates a Picture from the file name provided.
	 * 
	 * @param fileName The name of the file to create the picture from.
	 */
	public Picture(String fileName) {
		// Let the parent class handle this fileName.
		super(fileName);
	}

	/**
	 * Creates a Picture from the width and height provided.
	 * 
	 * @param width the width of the desired picture.
	 * @param height the height of the desired picture.
	 */
	public Picture(int width, int height) {
		// Let the parent class handle this width and height.
		super(width, height);
	}

	/**
	 * Creates a copy of the Picture provided.
	 * 
	 * @param pictureToCopy Picture to be copied.
	 */
	public Picture (Picture pictureToCopy) {
		// Let the parent class do the copying.
		super(pictureToCopy);
	}

	/**
	 * Creates a copy of the SimplePicture provided.
	 * 
	 * @param pictureToCopy SimplePicture to be copied.
	 */
	public Picture (SimplePicture pictureToCopy) {
		// Let the parent class do the copying.
		super(pictureToCopy);
	}

	/////////////////////////////// Methods ///////////////////////////////////

	//////////////////////////// Provided Methods /////////////////////////////////

	/**
	 * Helper method to determine if a x and y coordinate is valid (within the image) 
	 * 
	 * @param ix is the x value that might be outside of the image
	 * @param iy is the y value that might be outside of the image
	 * @return true if the x and y values are within the image and false otherwise
	 */
	@SuppressWarnings("unused")
	private boolean inImage(int ix, int iy) {
		return ix >= 0 && ix < this.getWidth() && iy >= 0
				&& iy < this.getHeight();
	}
	
	/**
	 * @return A string with information about the picture, such as 
	 * 	filename, height, and width.
	 */
	public String toString() {
		String output = "Picture, filename = " + this.getFileName() + "," + 
		" height = " + this.getHeight() + ", width = " + this.getWidth();
		return output;
	}
	/**
	 * Equals method for two Picture objects. 
	 * 
	 * @param obj is an Object to compare to the current Picture object
	 * @return true if obj is a Picture object with the same size as the
	 *         original and with the same color at each Pixel
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof Picture)) {
			return false;
		}

		Picture p = (Picture) obj;
		// Check that the two pictures have the same dimensions.
		if ((p.getWidth() != this.getWidth()) ||
				(p.getHeight() != this.getHeight())) {
			return false;
		}

		// Check each pixel.
		for (int x = 0; x < this.getWidth(); x++) {
			for(int y = 0; y < this.getHeight(); y++) {
				if (!this.getPixel(x, y).equals(p.getPixel(x, y))) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Helper method for loading a picture in the current directory.
	 */
	protected static Picture loadPicture(String pictureName) {
		URL url = Picture.class.getResource(pictureName);
		return new Picture(url.getFile().replaceAll("%20", " "));
	}
	
	/**
	 * Prints a two dimensional array of ints
	 * @param array
	 */
	public void printArray(int[][] array) {
		int height = array.length;
		int width = array[0].length;
		for (int r = 0; r < width; r++) {
			for (int c = 0; c < height; c++) {
				System.out.print(array[c][r] + "\t");
			}
			System.out.println();
		}
	}

	public Picture rotateRight() {
		int pictureHeight = this.getHeight();
		int pictureWidth = this.getWidth();
		Picture myPicture = new Picture(pictureHeight, pictureWidth);
		for (int x = 0; x < pictureWidth; x++) {
			for (int y = 0; y < pictureHeight; y++) {
				Pixel currentPixel = this.getPixel(x, y);
				myPicture.getPixel(pictureHeight-y-1,x).setColor(currentPixel.getColor());
			}
		}
		return myPicture;
	}
	
	/**
	 * This method can be used like the other Picture methods, to create a
	 * Picture that shows what Pixels are different between two Picture objects.
	 * 
	 * @param picture2 is a Picture to compare the current Picture to
	 * @return returns a new Picture with red pixels indicating differences between 
	 * 			the two Pictures
	 */
	public Picture showDifferences(Picture picture2){
		Picture newPicture = new Picture(this);

		int pictureHeight = this.getHeight();
		int pictureWidth = this.getWidth();
		Color red = new Color(255, 0, 0);
		for(int x = 0; x < pictureWidth; x++) {
			for(int y = 0; y < pictureHeight; y++) {
				if (!this.getPixel(x, y).equals(picture2.getPixel(x, y))) {
					Pixel p = newPicture.getPixel(x, y);
					p.setColor(red);
				}
			}
		}
		return newPicture;
	}
}

