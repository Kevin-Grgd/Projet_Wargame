
package wargame;

public class Obstacle extends Element {
	private static final long serialVersionUID = 8277957568171064957L;
	private TypeObstacle TYPE;
	
	public enum TypeObstacle implements IConfig  {
		ROCHEIMAGE ("/resources/roche#.png"), ARBREIMAGE ("/resources/arbre#.png"), WATERIMAGE ("/resources/water#.png");
		
		private String URL_IMAGE;
		TypeObstacle(String pIMAGE) {
			URL_IMAGE = pIMAGE;
		}

		public static TypeObstacle getObstacleAlea() {
			return values()[(int)(Math.random()*values().length)];
		}
		
		public String getUrl() {
			return URL_IMAGE;
		}
	}
	
	/**
	 * Constructeur
	 * @param type Le type de l'obstacle
	 */
	Obstacle(TypeObstacle type) {
		TYPE = type;
		setSkinNumber(1 + (int) (Math.random()*2));
		setUrl(type.getUrl());
	}

	/**
	 * Constructeur
	 * @param p Le polygone (Pour y insérer l'image)
	 */
	Obstacle(){
		this(TypeObstacle.getObstacleAlea());
	}

	public String toString() {
		return getPosition() + " " + TYPE;
	}
	
}