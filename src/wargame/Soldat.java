package wargame;

import java.awt.Graphics;
import java.awt.Polygon;

public abstract class Soldat extends Element implements ISoldat {
	private int points;
	private int portee;
	private int puissance;
	private int tir;
	protected ISoldat type;

	public abstract void renderElement(Graphics g, Polygon p);

	/**
	 * Constructeur
	 * @param pos La position du soldat
	 */
	Soldat(Position pos) {
		super(pos);
	}

	/**
	 * @return Son nombre de points de vie
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Choisir son nombre de points de vie
	 * @param points Points de vie à attribuer
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * @return La portée de vision et de tir
	 */
	public int getPortee() {
		return portee;
	}

	/**
	 * Choisir la portée du soldat
	 * @param portee Portée à attribuer
	 */
	public void setPortee(int portee) {
		this.portee = portee;
	}

	/**
	 * @return La puissance au corps-à-corps du soldat
	 */
	public int getPuissance() {
		return puissance;
	}

	/**
	 * Choisir la puissance au corps-à-corps du soldat
	 * @param puissance Puissance à attribuer
	 */
	public void setPuissance(int puissance) {
		this.puissance = puissance;
	}

	/**
	 * @return Le dégât de tir du soldat
	 */
	public int getTir() {
		return tir;
	}

	/**
	 * Choisir la puissance de tir du soldat
	 * @param tir Puissance de tir à attribuer
	 */
	public void setTir(int tir) {
		this.tir = tir;
	}

	public void joueTour(int tour) {
		// a completer
	}

	/**
	 * Fonction de combat inter-sodlats
	 * @param soldat Soldat à combattre
	 */
	public void combat(Soldat soldat) {
		if (this.getPosition().estVoisine(soldat.getPosition())) { // Au corps à corps
			soldat.setPoints(soldat.getPoints() - this.getPuissance());
		} else { // A distance
			soldat.setPoints(soldat.getPoints() - this.getTir());
		}
	}

	/**
	 * Déplacement du soldat
	 * @param newPos Position où le déplacer
	 */
	public void seDeplace(Position newPos) {
		setPosition(newPos);
	}

	/**
	 * Choisir type du soldat
	 * @param type Type de soldat choisi
	 */
	public void setType(ISoldat type) {
		this.type = type;
	}

	/**
	 * 
	 * @return Le type du soldat
	 */
	public ISoldat getTypeSoldat() {
		return this.type;
	}

	/**
	 * Repos du héros sélectionné
	 */
	public void seRepose() {
		if (getPoints() < type.getPoints()) {
			setPoints(getPoints() + type.getPoints() * TAUX_REGEN / 100);
		}
		if (getPoints() > type.getPoints()) {
			setPoints(type.getPoints());
		}
	}

	/**
	 * Le soldat choisi est un élu (Plus fort, plus résistant, ...)
	 */
	public void est_elu() {

	}
}
