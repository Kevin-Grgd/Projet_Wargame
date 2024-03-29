package wargame;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public abstract class Element implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 6708915742292972554L;
	private Position pos;
	protected transient BufferedImage aBufferedImage;
	
	/**
	 * Constructeur de l'élément
	 * @param pos Sa position
	 */
	Element(Position pos){
		this.pos = pos;
	}
	
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
	 * Renvoie l'image de cet élément
	 * @return Image de l'élément
	 */
	public BufferedImage getBufferedImage(){
		return aBufferedImage;
	}

	/**
	 * Méthode abstraite pour "dessiner" les éléments
	 * @param g Graphique où dessiner
	 * @param p Polygone à dessiner
	 */
	public abstract void renderElement(Graphics g,Polygon p);

	public abstract void reloadData();
}
