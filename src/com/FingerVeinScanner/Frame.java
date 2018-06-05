package com.FingerVeinScanner;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;

public class Frame
{

    //////////////////////////////// Fields ///////////////////////////////////

    /**
     * Name of the project.
     */
    private String projectName = " - Project One";
    PictureFrame picture1;
    PictureFrame picture2;
    /**
     * Main window used as the frame.
     */
    JFrame frame = new JFrame();
    JPanel main = new JPanel();
    //////////////////////////////// Constructors /////////////////////////////

    /**
     * A constructor that takes no arguments.
     */
    public Frame()
    {
        // Set up the frame
        initFrame();
    }

    public Frame(SimplePicture pictureP, SimplePicture picture2P)
    {
        // Set the current object's picture to the picture provided.
        picture1 = new PictureFrame(pictureP);
        picture2 = new PictureFrame(picture2P);
        // Set up the frame.
        initFrame();
    }

    ////////////////////////////////// Methods ////////////////////////////////

    /**
     * Sets the Picture to show in this PictureFrame.
     *
     * @param picture The new picture to use.
     */
    public void setPicture1(Picture picture)
    {
        this.picture1 = new PictureFrame(picture);
        setTitle(picture.getTitle());
        frame.pack();
        frame.repaint();
    }

    public void setPicture2(Picture picture)
    {
        this.picture2 = new PictureFrame(picture);
        frame.pack();
        frame.repaint();
    }

    /**
     * Updates the PictureFrame image with the image in
     * the associated DigitalPicture.
     */
    public void updateImage()
    {
        picture1.updateImage();
        picture2.updateImage();
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
        frame.setVisible(true);
    }


    /**
     * Sets the title for this PictureFrame.
     *
     * @param title The title to use for this PictureFrame.
     */
    public void setTitle(String title)
    {
        frame.setTitle(title + projectName);
    }

    /**
     * Forces this PictureFrame to repaint (redraw).
     */
    public void repaint()
    {
        // Make the frame visible.
        frame.setVisible(true);

        // Update the image from the picture.
        updateImage();

        // Tell the JFrame to handle the repaint.
        frame.repaint();
    }

    /**
     * Initializes this PictureFrame.
     */
    public void initFrame()
    {
        main.removeAll();
        GridLayout layout = new GridLayout(2,1);
        main.setLayout(layout);
        main.setSize(800,800);
        // Set the image for the picture frame.
        updateImage();
        picture1.getLabel().setText("Image 1");
        picture2.getLabel().setText("Image 2");
        picture1.getLabel().setBounds(50,0, (int)picture1.getLabel().getPreferredSize().getWidth() , (int)picture1.getLabel().getPreferredSize().getHeight());
        picture2.getLabel().setBounds(400,0,(int)picture2.getLabel().getPreferredSize().getWidth() , (int)picture2.getLabel().getPreferredSize().getHeight());
        Border border = BorderFactory.createLineBorder(Color.WHITE, 5);
        picture1.getLabel().setBorder(border);
        picture2.getLabel().setBorder(border);
        picture1.getLabel().setBackground(Color.WHITE);
        picture1.getLabel().setOpaque(true);
        picture2.getLabel().setBackground(Color.WHITE);
        picture2.getLabel().setOpaque(true);

        // Add the label to the frame.

        main.add(picture1.getLabel());
        main.add(picture2.getLabel());
        main.updateUI();
        // Pack the frame (set the size to as big as it needs to be).
        frame.add(main);
        frame.pack();

        // Make the frame visible.
        frame.setVisible(true);
    }

    public JFrame getFrame()
    {
        return frame;
    }

} // End of PictureFrame class.
