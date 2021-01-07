package wargame;
import java.awt.Color;
public interface IConfig {

	public final int LARGEUR_CARTE = 25; 
	public final int HAUTEUR_CARTE = 15; // en nombre de cases
	public final int NB_PIX_CASE = 50;
	public final int LARGEUR_FENETRE = (LARGEUR_CARTE+2)*NB_PIX_CASE; 
	public final int HAUTEUR_FENETRE = (HAUTEUR_CARTE+1)*NB_PIX_CASE;
	public final int POSITION_X = 100; 
	public final int POSITION_Y = 50; // Position de la fen�tre
	public final int NB_HEROS = 6; 
	public final int NB_MONSTRES = 15; 
	public final int NB_OBSTACLES = 20;
	public final int HAUTEUR_BARRE_OUTIL = 50;
	public final int LARGEUR_BARRE_OUTIL = LARGEUR_FENETRE;

	public final int HAUTEUR_PLATEAU = HAUTEUR_FENETRE - HAUTEUR_BARRE_OUTIL;
	public final int LARGEUR_PLATEAU = LARGEUR_FENETRE;

	/* PARTIE INFOBULLE */

	public final int OFFSET_X_INFO = 15;
	public final int OFFSET_Y_INFO = 30;
	public final int ECART_INFO = 20;

	public final int LARGEUR_BARRE_PV = 120;

	public final int OFFSET_X = 50;
	public final int OFFSET_Y_SUP = 20;
	public final int OFFSET_Y_INF = 30;
	
	public final int LARGEUR_INFO_BULLE = LARGEUR_FENETRE / 20 + LARGEUR_BARRE_PV;
	public final int HAUTEUR_INFO_BULLE = HAUTEUR_FENETRE / 20 + (ECART_INFO*4); 


	/* PARTIE BOUTON */

	public final int LARGEUR_BOUTON = LARGEUR_FENETRE/3;
	public final float TAILLE_TEXTE_MENU = 20f;
	public final Color BOUTON_CONTOUR = new Color(75, 50, 30);
	public final Color BOUTON_CONTOUR_SURVOLE = new Color(69,69,69);
	public final Color BOUTON_CONTOUR_SELECTIONNE = new Color(252, 93, 93);
	public final Color BOUTON_NORMAL = new Color(166, 118, 78);
	public final Color BOUTON_SURVOLE = new Color(2, 79, 107);
	public final Color BOUTON_SELECTIONNE = BOUTON_SURVOLE;
	public final Color TEXTE_NORMAL = Color.white;
	public final Color TEXTE_SELECTIONNE = new Color(252, 93, 93);


	public final int NB_TEXTURE_OBSTACLE = 2;

	/* PARTIE COMBAT */
	public final int MINIMAL_TIR_REQUIS = 5;
	
	public final Color COULEUR_VIDE = Color.white;
	public final Color COULEUR_INCONNU = Color.lightGray;
	public final Color COULEUR_TEXTE = Color.black;
	public final Color COULEUR_MONSTRES = Color.black;
	public final Color COULEUR_HEROS = Color.red;
	public final Color COULEUR_HEROS_DEJA_JOUE = Color.pink;
	public final Color COULEUR_EAU = Color.blue; 
	public final Color COULEUR_FORET = Color.green; 
	public final Color COULEUR_ROCHER = Color.gray;

	/* PARTIE MUSIQUE */
	public final int MUSIC_1 = 1;
	public final int MUSIC_2 = 2;


	/* PARTIE SAUVEGARDE et CHARGEMENT */
	public final boolean CHARGER = true;
	public final boolean SAUVEGARDER = false;
	public final int JEU = 0;
	public final int MENU_ACCUEIL = 1;
	public final int NB_MAX_SAVE = 5;
	public final int LARGEUR_BOUTON_LOAD_SAVE = LARGEUR_FENETRE - (LARGEUR_FENETRE/2);
	public final int HAUTEUR_BOUTON_LOAD_SAVE = 80;
	public final float TAILLE_TEXTE_LOAD_SAVE = 25f;
	public final Color BOUTON_LOAD_CONTOUR = new Color(68, 92, 60);
	public final Color BOUTON_LOAD_CONTOUR_SURVOLE = new Color(40, 60, 35);
	public final Color BOUTON_LOAD_CONTOUR_SELECTIONNE = new Color(214, 203, 0);
	public final Color BOUTON_LOAD = new Color(138, 106, 39);
	public final Color BOUTON_LOAD_SURVOLE = new Color(92, 71, 28);
	public final Color BOUTON_LOAD_SELECTIONNE = new Color(59, 45, 18);
	public final Color TEXTE_LOAD = new Color(186, 186, 186);
	public final Color TEXTE_LOAD_SELECTIONNE = new Color(214, 203, 0);
	
}
	