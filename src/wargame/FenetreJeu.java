package wargame;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;
import java.io.File;

public class FenetreJeu extends JPanel implements IConfig{

	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {

		
		JFrame fenetre = new JFrame("Wargame");
		fenetre.setSize(LARGEUR_FENETRE, HAUTEUR_FENETRE);
		fenetre.setLocation(POSITION_X, POSITION_Y);
		fenetre.setBackground(Color.GRAY);
		
		
		File vDossierSave = new File(System.getProperty("user.dir") + "/saves/");
		System.out.println(System.getProperty("user.dir"));
		// Si le dossier existe ou n'est pas un dossier
		if (!vDossierSave.exists() || !vDossierSave.isDirectory()){
			vDossierSave.mkdir();
		}

		MenuAccueil vMenuAccueil = new MenuAccueil(fenetre);
		fenetre.add(vMenuAccueil);
		vMenuAccueil.focusPanel();
		//vMenuAccueil.setMusicPlay(false); //Au lancement la musique n'est pas encore jouee
																//La detection se fait toute seule dans la classe

		fenetre.pack();
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setVisible(true);
		
	}

}
