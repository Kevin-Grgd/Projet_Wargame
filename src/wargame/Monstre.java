package wargame;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

public class Monstre extends Soldat {
	private static int numMonstre = 0;
	private final int ID_MONSTRE;

	/**
	 * Constructeur du monstre
	 * @param pos Position où l'insérer
	 */
	Monstre(Position pos) {
		super(pos);
		setType(TypesM.getTypeMAlea());
		this.aBufferedImage = this.type.getEnumImage();
		setPoints(type.getPoints());
		setPortee(type.getPortee());
		setPuissance(type.getPuissance());
		setTir(type.getTir());
		ID_MONSTRE = ++numMonstre;
	}

	public String toString() {
		return getPosition() + " " + type.toString() + " " + ID_MONSTRE + " PV:" + getPoints() + '/' + type.getPoints();
	}

	@Override
	public void renderElement(Graphics g, Polygon p) {
		//g.setColor(Color.RED);
		//g.fillPolygon(p);
		new Image(this.aBufferedImage,p).drawHexa(g);
	}

	@Override
	public BufferedImage getEnumImage() {
		// Never go here
		return null;
	}
}
