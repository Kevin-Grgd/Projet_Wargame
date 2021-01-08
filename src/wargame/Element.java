package wargame;

import java.io.Serializable;

public abstract class Element implements Serializable{
	private static final long serialVersionUID = 4761294017398466739L;
	private Position pos;
	private int skinNumber;
	private String url;
	//protected transient BufferedImage aBufferedImage;
	
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
}
