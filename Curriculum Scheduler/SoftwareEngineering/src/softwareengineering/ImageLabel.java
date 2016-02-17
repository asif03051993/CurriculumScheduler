/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package softwareengineering;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

  /* 
 *
 * Function is written by 
 * 	Asif Ahammed	cs10b034@iith.ac.in
 *	Siva Krishna	cs10b028@iith.ac.in
 *  Himakar			cs10b039@iith.ac.in
 *
 */
class ImageLabel extends JLabel {
    Image image;
    ImageObserver imageObserver;
    ImageLabel(String filename) throws IOException {
        //BufferedImage myPicture = ImageIO.read(new File(filePath));
        //ImageIcon icon = new ImageIcon(myPicture);
        ImageIcon icon = new ImageIcon(ImageIO.read((getClass().getResourceAsStream(filename))));
        image = icon.getImage();
        imageObserver = icon.getImageObserver();
    }
    void SetImageLabel(String filename) throws IOException {
        //BufferedImage myPicture = ImageIO.read(new File(filePath));
        //ImageIcon icon = new ImageIcon(myPicture);
        ImageIcon icon = new ImageIcon(ImageIO.read((getClass().getResourceAsStream(filename))));
        image = icon.getImage();
        imageObserver = icon.getImageObserver();
        return;
    }
    public void paint( Graphics g ) {
        super.paint( g ) ;
        g.drawImage(image,  0 , 0 , getWidth() , getHeight() , imageObserver);
    }
}

