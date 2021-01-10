package wargame;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

import javax.imageio.ImageIO;


public abstract class Element implements Serializable{
	private static final long serialVersionUID = 4761294017398466739L;
	private Position pos;
	private int skinNumber;
	private String url;
	private transient BufferedImage img;
	
	/**
	 * Donne la position de l'élément
	 * @return La position de l'élément
	 */
	public Position getPosition() {
		return pos;
	}

	/**
	 * Choisie la position de l'élément
	 * @param pos Position où mettre l'élément
	 */
	public void setPosition(Position pos) {
		this.pos = pos;
	}
	
	public int getSkinNumber() {
	   	 return skinNumber;
	    }
	    
	public void setSkinNumber(int skinNumber) {
	   	this.skinNumber = skinNumber;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setImage() {
		try {
			URL url = getClass().getResource(this.url);
			img = ImageIO.read(url); 
		} catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public BufferedImage getImage() {
			return img;
	}
}
