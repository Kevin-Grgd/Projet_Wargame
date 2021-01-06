package wargame;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Obstacle extends Element {
	private TypeObstacle TYPE;
	
	public enum TypeObstacle implements IConfig  {
		ROCHEIMAGE ("/resources/roche#.png"), ARBREIMAGE ("/resources/arbre#.png"), WATERIMAGE ("/resources/water#.png");
		
		private String URL_IMAGE;
		private BufferedImage IMAGE;
		TypeObstacle(String pIMAGE) {
			URL_IMAGE = pIMAGE;
		}

		public static TypeObstacle getObstacleAlea() {
			return values()[(int)(Math.random()*values().length)];
		}

		/**
		 * Défini l'image de l'obstacle
		 */
		public void setImage(){
			int vRand = (int) Math.random() * NB_TEXTURE_OBSTACLE + 1;
			String vIMAGE = URL_IMAGE.replace("#",  Integer.toString(vRand));
			URL url = this.getClass().getResource(vIMAGE);
			try {
				IMAGE = ImageIO.read(url);
			} catch (IOException e) {
				IMAGE = null;
				e.printStackTrace();
			}
		}
		/**
		 * 
		 * @return L'image de l'obstacle
		 */
		public BufferedImage getImage(){
			setImage();
			return this.IMAGE;
		}
	}
	
	/**
	 * Constructeur
	 * @param type Le type de l'obstacle
	 * @param pos Sa position
	 */
	Obstacle(TypeObstacle type) {
		TYPE = type;
	}

	/**
	 * Constructeur
	 * @param vPos Sa position
	 * @param p Le polygone (Pour y insérer l'image)
	 */
	Obstacle(){
		this(TypeObstacle.getObstacleAlea());
		this.aBufferedImage = TYPE.getImage();
	}

	@Override
	public void renderElement(Graphics g,Polygon p){
        new Image(this.aBufferedImage,p).drawHexa(g);
	}

	public BufferedImage getObstacleBufferedImage(){
		return this.TYPE.IMAGE;
	}

	public String toString() {
		return getPosition() + " " + TYPE;
	}

	
}