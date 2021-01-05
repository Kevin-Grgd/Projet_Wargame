package wargame;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Color;

public class FenetreJeu extends JPanel implements IConfig{

	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {

		// test Position
		/*
		 * Position pos1 = new Position(4, 5); Position pos2 = new Position(3, 4);
		 * Position pos3 = new Position(6, 0); Heros heros1 = new Heros(pos1);
		 * System.out.println(heros1); heros1.seDeplace(pos2);
		 * System.out.println(heros1); Heros heros2 = new Heros(pos3);
		 * System.out.println(heros2); heros1.setPoints(5); System.out.println(heros1);
		 * heros1.seRepose(); System.out.println(heros1); heros1.seRepose();
		 * System.out.println(heros1); heros1.seRepose(); System.out.println(heros1);
		 * heros1.seRepose(); System.out.println(heros1); heros1.seRepose();
		 * System.out.println(heros1); heros1.seRepose(); System.out.println(heros1);
		 * heros1.seRepose(); System.out.println(heros1);
		 * 
		 * Monstre monstre1 = new Monstre(pos1); Monstre monstre2 = new Monstre(pos3);
		 * System.out.println(monstre1); System.out.println(monstre2);
		 */

		JFrame fenetre = new JFrame("Wargame");
		fenetre.setSize(LARGEUR_FENETRE, HAUTEUR_FENETRE);
		fenetre.setLocation(POSITION_X, POSITION_Y);
		fenetre.setBackground(Color.GRAY);
	
		PanneauJeu vPanneau = new PanneauJeu();
		Accueil vAccueil = new Accueil(vPanneau,fenetre);
		fenetre.add(vAccueil);
		
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
