package wargame;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

public class Monstre extends Soldat {
	private static int numMonstre = 0;
	private final int ID;

	/**
	 * Constructeur du monstre
	 * @param pos Position où l'insérer
	 */
	Monstre() {
		super(TypesM.getTypeMAlea());
		ID = ++numMonstre;
	}

	public String toString() {
		return type.toString() + " " + ID;
	}

	@Override
	public void renderElement(Graphics g, Polygon p) {
		new Image(this.aBufferedImage,p).drawHexa(g);
	}

	@Override
	public BufferedImage getEnumImage() {
		// Never go here
		return null;
	}
}
