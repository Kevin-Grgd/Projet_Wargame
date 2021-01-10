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
	
	/**
	 * Donne le numero de l'image
	 * @return skinNumber
	 */
	public int getSkinNumber() {
	   	 return skinNumber;
	}
	
	/**
	 * Mutateur du numero de l'image
	 * @param skinNumber Numéro du skin à afficher
	 */
	public void setSkinNumber(int skinNumber) {
	   	this.skinNumber = skinNumber;
	}
	
	/**
	 * Donne le le chemin de l'image
	 * @return url
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * Mutateur du chemin de l'image
	 * @param url URL de l'image
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * Donne à l'élément son image
	 */
	public void setImage() {
		try {
			URL vUrl = getClass().getResource(this.url);
			img = ImageIO.read(vUrl); 
		} catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Retourne l'image de l'element
	 * @return img
	 */
	public BufferedImage getImage() {
			return img;
	}
}
