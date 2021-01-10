package wargame;

public class Heros extends Soldat {
	private static final long serialVersionUID = -8042494773455062650L;
	private static int numHeros = 64;
	private final char ID;
	private boolean aSelected;
	private boolean aJoue = false;

	/**
	 * Constructeur du héros
	 * @param pos Sa position sur la carte
	 */
	Heros() {
		super(TypesH.getTypeHAlea());
		aSelected = false;
		ID = (char) ++numHeros;
		setSkinNumber((int) (Math.random()*2));
		setUrl(type.getUrl().replace('#',  Integer.toString(getSkinNumber()).charAt(0)));
		setImage();
	}

	public String toString() {
		return type.toString() + " " + ID + " " + getPosition();
	}

	/**
	 * Indique si le héros est sélectionné
	 * @return Vrai ou faux selon s'il est sélectionné
	 */
	public boolean isSelected() {
		return this.aSelected;
	}

	/**
	 * Choisi de sélectionner le héros
	 * @param pSelected Choisi si le héros est sélectionner ou non
	 */
	public void setSelected(boolean pSelected) {
		this.aSelected = pSelected;
	}

	/**
	 * Choisi si le héros a déjà joué ou non
	 * @param pJoue Booléen pour dire s'il a déjà joué ou non
	 */
	public void setJoue(boolean pJoue) {
		this.aJoue = pJoue;
	}

	/**
	 * Indique si le héros a déjà joué
	 * @return Vrai ou faux selon s'il a déjà joué
	 */
	public boolean getJoue() {
		return this.aJoue;
	}
}
