package wargame;

public interface ISoldat {
	int TAUX_REGEN = 20; //quand un soldat se repose, il regagne 20% de sa vie totale
	
	public enum TypesH implements ISoldat {
		NINJA (40,4,30,10,"/resources/Avatar/Heros/Ninja/Ninja#.png"), SOLDAT (80,1,20,0,"/resources/Avatar/Heros/Soldat/Soldat#.png"), SPADASSIN (70,3,25,0,"/resources/Avatar/Heros/Spadassin/Spadassin#.png"), TANK (120,2,5,0,"/resources/Avatar/Heros/Tank/Tank#.png"), TIREUR(20, 4, 5, 25, "/resources/Avatar/Heros/Tireur/Tireur#.png");
		private final int POINTS_DE_VIE; 
		private final int PORTEE_VISUELLE; 
		private final int PUISSANCE; 
		private final int TIR;
		private final String URL_IMAGE;
		TypesH(int points, int portee, int puissance, int tir,String pUrl) {
			POINTS_DE_VIE = points;
			PORTEE_VISUELLE = portee;
			PUISSANCE = puissance;
			TIR = tir;
			URL_IMAGE = pUrl;
		}
		public int getPoints() {
			return POINTS_DE_VIE;
		}
		public int getPortee() {
			return PORTEE_VISUELLE;
		}
		public int getPuissance() {
			return PUISSANCE;
		}
		public int getTir() {
			return TIR;
		}
		public String getUrl() { //chemin de l'image
			return URL_IMAGE;
		}
		
		public static TypesH getTypeHAlea() {
			return values()[(int)(Math.random()*values().length)];
		}
	}
	public enum TypesM implements ISoldat {
		BERSERK (150,1,30,0,"/resources/Avatar/Monstre/Berserk/Berserk#.png"), BETE (40,3,10,3,"/resources/Avatar/Monstre/Bete/Bete#.png"), MAGE (20,3,5,35,"/resources/Avatar/Monstre/Mage/Mage#.png"), GOBELIN (40, 2, 20, 0, "/resources/Avatar/Monstre/Gobelin/Gobelin#.png");
		private final int POINTS_DE_VIE;
		private final int PORTEE_VISUELLE;
		private final int PUISSANCE;
		private final int TIR;
		private final String URL_IMAGE;

		TypesM(int points, int portee, int puissance, int tir,String pUrl) {
			POINTS_DE_VIE = points;
			PORTEE_VISUELLE = portee;
			PUISSANCE = puissance;
			TIR = tir;
			URL_IMAGE = pUrl;
		}
		public int getPoints() {
			return POINTS_DE_VIE;
		}
		public int getPortee() {
			return PORTEE_VISUELLE;
		}
		public int getPuissance() {
			return PUISSANCE;
		}
		public int getTir() {
			return TIR;
		}
		public String getUrl() {
			return URL_IMAGE;
		}
		public static TypesM getTypeMAlea() {
			return values()[(int)(Math.random()*values().length)];
		}
	}
	int getPoints();
	int getPortee();
	int getPuissance();
	int getTir();
	String getUrl();
}