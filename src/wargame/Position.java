package wargame;

import java.util.ArrayList;

public class Position implements IConfig {
	private int x;
	private int y;
	private Element elem;
	/**
	 * Constructeur
	 * @param x Coordonnée en abscisse
	 * @param y Coordonnée en ordonné
	 */
	Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * 
	 * @return Coordonnée en abscisse
	 */
	public int getX() {
		return x;
	}

	/**
	 * 
	 * @return Coordonnée en ordonné
	 */
	public int getY() {
		return y;
	}

	/**
	 * Indique la coordonnée en abscisse
	 * @param x Coordonnée en abscisse
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * Indique la coordonnée en ordonné
	 * @param y Coordonnée en ordonné
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * 
	 * @return Element de la position
	 */
	public Element getElement() {
		return elem;
	}
	
	/**
	 * Definit l'element de la position
	 * @param elem
	 */
	public void setElement(Element elem) {
		this.elem = elem;
		if(elem != null)
			elem.setPosition(this);
	}

	/**
	 * Indique si une position est valide
	 * @return Si la position est valide ou non
	 */
	public boolean estValide() {
		if (x < 0 || x >= LARGEUR_CARTE || y < 0 || y >= HAUTEUR_CARTE)
			return false;
		else
			return true;
	}

	public String toString() {
		return "(" + x + "," + y + ")";
	}

	/**
	 * Indique si des positions sont voisines
	 * @param pos Position supposée voisine
	 * @return true Si elles le sont
	 */
	public boolean estVoisine(Position pos) {
		if (pos.estValide()) {
			// Elimination des cas non souhaités
			if(x == pos.getX() && y == pos.getY()) return false; //coordonnees de pos
			
			if (y % 2 == 0) {
				if (x == pos.getX() - 1 && y == pos.getY() - 1) {
					return false;
				}
				if (x == pos.getX() - 1 && y == pos.getY() + 1) {
					return false;
				}
			} else {
				if (x == pos.getX() + 1 && y == pos.getY() - 1) {
					return false;
				}
				if (x == pos.getX() + 1 && y == pos.getY() + 1) {
					return false;
				}
			}
			// Cas non souhaités éliminés, on peut y aller
			return ((Math.abs(x - pos.x) <= 1) && (Math.abs(y - pos.y) <= 1));
		} else {
			return false;
		}
	}

	/**
	 * Donne la distance entre deux positions
	 * @param p Position dont il faut regarder la distance
	 * @return La distance entre les deux positions
	 */
	public double distance(Position p) {
		double vX =  Math.pow((this.x - p.x), 2);
		double vY =  Math.pow((this.y - p.y), 2);
		return Math.sqrt(vX + vY);
	}
	
	/**
	 * Donne un tableau des cases adjacentes de cette position
	 * @return Position[]
	 */
	public Position[] getAdjacents() { //retourne les 6 cases adjacentes
		ArrayList<Position> posAdjacentes = new ArrayList<Position>();
		Position pos;
		for(int i = x-1 ; i <= x+1 ; i++) {
			for(int j = y-1 ; j <= y+1 ; j++) {
				pos = new Position(i, j);
				if(estVoisine(pos)) {
					posAdjacentes.add(pos);
				}
			}
		}
		return posAdjacentes.toArray(new Position[0]);
	}
}