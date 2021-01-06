package wargame;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Heros extends Soldat {
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
	}

	public String toString() {
		return type.toString() + " " + ID;
	}

	@Override
	public void renderElement(Graphics g, Polygon p) {
		new Image(this.aBufferedImage,p).drawHexa(g);
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

	@Override
	public BufferedImage getEnumImage() {
		//Not used
		return null;
	}

}
