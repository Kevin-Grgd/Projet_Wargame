package wargame;

public class Monstre extends Soldat {
	private static final long serialVersionUID = 8882225921225738227L;
	private static int numMonstre = 0;
	private final int ID;

	/**
	 * Constructeur du monstre
	 * @param pos Position où l'insérer
	 */
	Monstre() {
		super(TypesM.getTypeMAlea());
		ID = ++numMonstre;
		setUrl(type.getUrl().replace('#',  Integer.toString((int) (Math.random()*6)).charAt(0)));
	}

	public String toString() {
		return type.toString() + " " + ID;
	}
}
