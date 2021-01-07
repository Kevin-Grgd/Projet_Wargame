package wargame;

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

}
