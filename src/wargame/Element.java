package wargame;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

public abstract class Element{
	private Position pos;
	protected BufferedImage aBufferedImage;
	
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
	public void renderElement(Graphics g, Polygon p) {
		new Image(aBufferedImage,p).drawHexa(g);
	}
	
	public abstract void reloadData();
}
