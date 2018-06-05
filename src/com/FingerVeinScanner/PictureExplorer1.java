package com.FingerVeinScanner;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PictureExplorer1 implements ActionListener
{

	// Current x- and y-index.
	private int xIndex = 0;
	private int yIndex = 0;

	// Main GUI variables.
	private JFrame pictureFrame;
    com.FingerVeinScanner.Frame frame;
    private JScrollPane scrollPane;
    boolean matches;
	// Information bar variables.

	// Menu components.
	private JMenuBar menuBar;

	// File menu.
	private JMenu fileMenu;
	private JMenuItem openMenuItem;
	private JMenuItem openMenuItem2;
	private JMenuItem resetMenuItem;
	private JMenuItem saveMenuItem;
	private JMenuItem saveAsMenuItem;
	private JMenuItem exitMenuItem;

	// Strings used in file menu items.
	private static final String STR_FILE = "File";
	private static final String STR_OPEN = "Open Image 1";
	private static final String STR_OPEN2 = "Open Image 2";
	private static final String STR_RESET = "Reset Picture";
	private static final String STR_SAVE = "Save";
	private static final String STR_SAVEAS = "Save As...";
	private static final String STR_EXIT = "Exit";


	private JMenu compareMenu;
	private JMenuItem compareMenuItem;

	private static final String STR_COMPARE = "Compare Both Images";


	/** The Picture being explored. */
	private Picture picture;
	private Picture picture2;


	/** The ImageDisplay. */


    /** The zoom factor (amount to zoom). */
	private double zoomFactor;

	/** The number system to use.
	 * 	0 means starting at 0, 1 means starting at 1. */
	private int numberBase = 0;

	/**
	 * Public constructor.
	 *
	 * @param picture The Picture to explore.
	 */
	public PictureExplorer1(Picture picture, Picture picture2) {
		this.picture = picture;
		this.picture2 = picture2;
		this.zoomFactor = 1;

		// Create the window and set things up.
		this.createWindow();
	}

	/**
	 * Changes the number system to start at base one.
	 */
	public void changeToBaseOne() {
		this.numberBase = 1;
	}

	/**
	 * Sets the title of the frame.
	 * 
	 * @param title The title to use.
	 */
	public void setTitle(String title) {
		this.pictureFrame.setTitle(title);
	}

	/**
	 * Creates and initializes the picture frame.
	 */
	private void createAndInitPictureFrame() {
        frame = new com.FingerVeinScanner.Frame(picture,picture2);
		this.pictureFrame = frame.getFrame(); // Create the JFrame.
		this.pictureFrame.setResizable(true);  // Allow the user to resize it.
		this.pictureFrame.getContentPane().
		setLayout(new BorderLayout()); // Use border layout.
		this.pictureFrame.setDefaultCloseOperation
		(JFrame.DISPOSE_ON_CLOSE); // When closed, stop.
		this.pictureFrame.setTitle(picture.getTitle());
		//PictureExplorerFocusTraversalPolicy newPolicy =
		//	new PictureExplorerFocusTraversalPolicy();
		//this.pictureFrame.setFocusTraversalPolicy(newPolicy);
	}
	
	/**
	 * Creates the menu bar, menus, and menu items.
	 */
	private void setUpMenuBar() {
		// Create menu bar.
		this.menuBar = new JMenuBar();
		
		// Create each sub-menu in the menu bar
		this.setUpMenuBar_FileMenu();
		//this.setUpMenuBar_ZoomMenu();
		this.setUpMenuBar_CompareMenu();	
		this.pictureFrame.setJMenuBar(menuBar);
	}

	/**
	 * Creates the File Menu
	 */
	private void setUpMenuBar_FileMenu(){
		// Add the file menu.
		this.fileMenu = new JMenu(STR_FILE);
		this.openMenuItem = new JMenuItem(STR_OPEN);
		this.openMenuItem2 = new JMenuItem(STR_OPEN2);
		this.resetMenuItem = new JMenuItem(STR_RESET);
		this.saveMenuItem = new JMenuItem(STR_SAVE);
		this.saveAsMenuItem = new JMenuItem(STR_SAVEAS);
		this.exitMenuItem = new JMenuItem(STR_EXIT);
		
		// Add the action listeners for the file menu.
		this.openMenuItem.addActionListener(this);
		this.openMenuItem2.addActionListener(this);
		this.resetMenuItem.addActionListener(this);
		this.saveMenuItem.addActionListener(this);
		this.saveAsMenuItem.addActionListener(this);
		this.exitMenuItem.addActionListener(this);
		
		// Add the menu items to the file menu.
		this.fileMenu.add(openMenuItem);
		this.fileMenu.add(openMenuItem2);
		this.fileMenu.add(resetMenuItem);
		this.fileMenu.addSeparator();
		this.fileMenu.add(saveMenuItem);
		this.fileMenu.add(saveAsMenuItem);
		this.fileMenu.addSeparator();
		this.fileMenu.add(exitMenuItem);
		this.menuBar.add(fileMenu);
	}

	/**
	 * Creates the Zoom Menu
	 */

	/**
	 * Creates the Flip Menu
	 */
	private void setUpMenuBar_CompareMenu(){
		// Add the rotate flip menu.
		this.compareMenu 		 = new JMenu("Compare");
		this.compareMenuItem 		 = new JMenuItem(STR_COMPARE);
		// Add the action listeners for the rotate/flip effects menu.
		this.compareMenuItem.addActionListener(this);
		this.compareMenu.add(compareMenuItem);
		this.menuBar.add(compareMenu);

	}
	
	/**
	 * Creates and initializes the scrolling image.
	 */
	private void createAndInitScrollingImage() {
		if (this.scrollPane != null) {
			this.pictureFrame.getContentPane().remove(this.scrollPane);

        }

		this.scrollPane = new JScrollPane();
		this.pictureFrame.getContentPane().add(this.scrollPane, BorderLayout.CENTER);
		this.pictureFrame.validate();
	}

	/**
	 * Creates the JFrame and sets everything up.
	 */
	private void createWindow() {
		// Create the picture frame and initializes it.
		this.createAndInitPictureFrame();

		// Set up the menu bar.
		this.setUpMenuBar();

		// Create the information panel.
		//this.createInfoPanel();

		// Create the scrollpane for the picture.
		this.createAndInitScrollingImage();

		// Show the picture in the frame at the size it needs to be.
		this.pictureFrame.pack();
		this.pictureFrame.setVisible(true);
		pictureFrame.setSize(728,560);
	}

	/**
	 * Sets up the next and previous buttons for the pixel location
	 * information.
	 */

	public void actionPerformed(ActionEvent a) {
		// Actions reset the zoom (so update the zoom menu)

		if (a.getActionCommand().equals(STR_OPEN)) {
			String fileName = FileChooser.pickAFile(FileChooser.OPEN);

			if (fileName != null) {
				try {
					this.picture.loadOrFail(fileName);
					frame.setPicture1(picture);
					frame.initFrame();
					pictureFrame = frame.getFrame();
					this.pictureFrame.setTitle(this.picture.getTitle());
					this.createAndInitScrollingImage();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(pictureFrame,
						    "Could not open file: " + fileName,
						    "Open Error",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		if (a.getActionCommand().equals(STR_OPEN2)) {
			String fileName = FileChooser.pickAFile(FileChooser.OPEN);

			if (fileName != null) {
				try {
					this.picture2.loadOrFail(fileName);
                    frame.setPicture2(picture2);
                    frame.initFrame();
                    pictureFrame = frame.getFrame();
					this.createAndInitScrollingImage();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(pictureFrame,
						    "Could not open file: " + fileName,
						    "Open Error",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
		if (a.getActionCommand().equals(STR_RESET)) {
			/*//System.out.println("RESET");
			String fileName = this.picture.getFileName();
			if (fileName != null) {
				//System.out.println("non null");
				try {
					this.picture.loadOrFail(fileName);
					this.createAndInitScrollingImage();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(pictureFrame,
						    "Could not open file: " + fileName,
						    "Open Error",
						    JOptionPane.ERROR_MESSAGE);
				}
			} else {
				System.out.println("There was no filename associated with this Picture.");
			}*/
			picture = Picture.loadPicture(picture1String);
			picture2 = Picture.loadPicture(picture2String);
			frame.setPicture1(picture);
			frame.setPicture2(picture);
			frame.initFrame();
			pictureFrame = frame.getFrame();
			this.pictureFrame.setTitle(this.picture.getTitle());
			this.createAndInitScrollingImage();
		}
		if (a.getActionCommand().equals(STR_SAVE)) {
			this.saveFile();
		}
		
		if (a.getActionCommand().equals(STR_SAVEAS)) {
			String fileName = FileChooser.pickAFile(FileChooser.SAVE);
			
			if (fileName != null) {
				try {
					String extension = this.picture.getExtension();

					// User may have provided a file extension.
					int posDot = fileName.indexOf('.');
					if (posDot >= 0)
						extension = fileName.substring(posDot + 1);
					else
						fileName = fileName + "." + extension;

					File file = new File(fileName);
					
					ImageIO.write(this.picture.getBufferedImage(),
							this.picture.getExtension(), file);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(this.pictureFrame,
						    "Could not save file. If you have added an " +
						    "image extension (such as JPG or BMP), please" +
						    "ensure that the extension is a vaild one.",
						    "Save Error",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		if (a.getActionCommand().equals(STR_EXIT)) {
			System.exit(0);
		}

		if (a.getActionCommand().equals(STR_COMPARE)) {
			String string = "";
			if (picture.equals(picture2)||FingerVein.compareResults(picture.getFileName(),picture2.getFileName())) {
				matches = true;
				string = "Match";
				System.out.println("match");
			} else {
				string = "Don't Match";
				System.out.println("don't match");
			}
			Result dialog = new Result();
			dialog.disp_percen(string);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
	}

	private BufferedImage toImage(Picture picture)
	{
		try {
			File in = new File(picture.getFileName());
			BufferedImage newImage;// = new BufferedImage();//(picture.getWidth(), picture.getHeight(), BufferedImage.TYPE_INT_RGB);
			newImage = ImageIO.read(in);
			return newImage;
		}
		catch(IOException e)
		{
			System.out.println("error");
			return null;
		}
	}
	/**
	 * Saves the file corresponding to the current picture.
	 */
	private void saveFile() {
		File file = new File(this.picture.getFileName());

		try {
			ImageIO.write(this.picture.getBufferedImage(),
					this.picture.getExtension(), file);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this.pictureFrame,
				    "Could not save file.",
				    "Save Error",
				    JOptionPane.WARNING_MESSAGE);
		}

	}

	public static String picture1String = "001_L_1.png";
	public static String picture2String = "001_L_2.png";
	public static void main(String[] args) {
		Picture pic = Picture.loadPicture(picture1String);
		Picture pic2 = Picture.loadPicture(picture2String);
		PictureExplorer1 explore = new PictureExplorer1(pic, pic2);
	}
} // End of PictureExplorer class.
