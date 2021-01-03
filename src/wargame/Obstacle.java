package wargame;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;

public class Obstacle extends Element {
	private Position pos;
	
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
			int vRand = new Random().nextInt(NB_TEXTURE_OBSTACLE)+1;
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
	
	private TypeObstacle TYPE;
	private Image aImage;
	
	/**
	 * Constructeur
	 * @param type Le type de l'obstacle
	 * @param pos Sa position
	 */
	Obstacle(TypeObstacle type, Position pos) {
		super(pos);
		TYPE = type;
	}

	/**
	 * Constructeur
	 * @param vPos Sa position
	 * @param p Le polygone (Pour y insérer l'image)
	 */
	Obstacle(Position vPos,Polygon p){
		super(vPos);
		TYPE = TypeObstacle.getObstacleAlea();
		this.aBufferedImage = TYPE.getImage();
		this.aImage = new Image(this.aBufferedImage,p);
	}

	@Override
	public void renderElement(Graphics g,Polygon p){
        this.aImage.drawHexa(g);
	}

	public BufferedImage getObstacleBufferedImage(){
		return this.TYPE.IMAGE;
	}
	@Override
	public Position getPosition(){
		return this.pos;
	}

	public String toString() {
		return ""+TYPE;
	}

	
}