package wargame;

public abstract class Soldat extends Element implements ISoldat {
	private static final long serialVersionUID = 6135340252482111515L;
	private int points;
	private int portee;
	private int puissance;
	private int tir;
	protected ISoldat type;

	/**
	 * Constructeur
	 * @param type La position du soldat
	 */
	Soldat(ISoldat type) {
		setType(type);
		//aBufferedImage = type.getEnumImage();
		setPoints(type.getPoints());
		setPortee(type.getPortee());
		setPuissance(type.getPuissance());
		setTir(type.getTir());
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
			soldat.setPoints(soldat.getPoints() - getPuissance());
		} else { // A distance
			soldat.setPoints(soldat.getPoints() - getTir());
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
		return type;
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

	public void est_elu() {

	}
	
}
