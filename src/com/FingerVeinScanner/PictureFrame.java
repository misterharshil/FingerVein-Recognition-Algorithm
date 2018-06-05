package com.FingerVeinScanner;

import javax.swing.*;

public class PictureFrame
{

	//////////////////////////////// Fields ///////////////////////////////////

	/**
	 * ImageIcon used to display the picture in the label.
	 */
	ImageIcon imageIcon = new ImageIcon();

	/**
	 * Label used to display the picture.
	 */
	private JLabel label = new JLabel(imageIcon);

	/**
	 * SimplePicture to display.
	 */
	private SimplePicture picture;


	//////////////////////////////// Constructors /////////////////////////////

	/**
	 * A constructor that takes no arguments.
	 */

	/**
	 * A constructor that takes a SimplePicture to display.
	 * 
	 * @param picture The SimplePicture to display in the PictureFrame.
	 */
	public PictureFrame(SimplePicture picture)
	{
		// Set the current object's picture to the picture provided.
		this.picture = picture;
		// Set up the frame.
	}

	////////////////////////////////// Methods ////////////////////////////////

	/**
	 * Sets the Picture to show in this PictureFrame.
	 * 
	 * @param picture The new picture to use.
	 */
	public void setPicture(Picture picture)
	{
		this.picture = picture;
		imageIcon.setImage(picture.getImage());
	}

	/**
	 * Updates the PictureFrame image with the image in
	 * the associated DigitalPicture.
	 */
	public void updateImage()
	{
		// Only do this if there is a picture.
		if (picture != null)
		{
			// Sets the image for the image icon from the picture.
			imageIcon.setImage(picture.getImage());

			// Sets the title of the frame to the title of the picture.
		}

	}

	public JLabel getLabel()
	{
		return label;
	}

	/**
	 * Updates the PictureFrame image with the image in
	 * the associated DigitalPicture and shows it.
	 */
	public void updateImageAndShowIt()
	{
		// First, update the image.
		updateImage();
		// Now, make sure it is shown.
	}
} // End of PictureFrame class.
