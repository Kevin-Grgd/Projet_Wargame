package wargame;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;

public class FenetreJeu extends JPanel implements IConfig{

	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {

		
		JFrame fenetre = new JFrame("Wargame");
		fenetre.setSize(LARGEUR_FENETRE, HAUTEUR_FENETRE);
		fenetre.setLocation(POSITION_X, POSITION_Y);
		fenetre.setBackground(Color.GRAY);
		

		PanneauJeu vPanneau = new PanneauJeu(fenetre,null);
		MenuAccueil vMenuAccueil = new MenuAccueil(vPanneau,fenetre);
		fenetre.add(vMenuAccueil);
		vMenuAccueil.focusPanel();
		//vMenuAccueil.setMusicPlay(false); //Au lancement la musique n'est pas encore jouee
																//La detection se fait toute seule dans la classe

		fenetre.pack();
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setVisible(true);
		
		
		/*
		fenetre.add(vPanneau);
		fenetre.repaint();
		fenetre.pack();
		*/
		/*
		while(vPanneau.getCarte().getHerosRestant() != 0 && vPanneau.getCarte().getMonstreRestant() != 0){
			System.out.println(""+vPanneau.getCarte().getHerosRestant());
		}
 
		if (vPanneau.getCarte().getHerosRestant() == 0){
            System.out.println("Perdu");
        }
        if (vPanneau.getCarte().getMonstreRestant() == 0){
            System.out.println("Gagné");
		}*/
		
	}

}
