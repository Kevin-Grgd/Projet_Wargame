package wargame;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JComponent;

public class BoutonMenu extends JComponent implements IConfig{

	private static final long serialVersionUID = 1L;
	public int widthBouton = LARGEUR_BOUTON;
	public int heightBouton;
	public int posX;
	public int posY;
	private String texte;
	private boolean isTarget = false;
	private boolean isFocus = false;
	private Font boutonFont;
	

	/**
	 * Constructeur BoutonMenu
	 * @param pX Coordonnée en abscisse
	 * @param pY Coordonnée en ordonnée
	 * @param txt Texte à afficher
	 */
	public BoutonMenu( int pX, int pY,  String txt) {
		posX = pX;
		posY = pY;
		texte = txt;
		
		try {
			InputStream isFont = this.getClass().getResourceAsStream("/resources/Font/aniron.ttf");
			boutonFont = Font.createFont(Font.TRUETYPE_FONT, isFont);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(boutonFont);
			boutonFont = boutonFont.deriveFont(TAILLE_TEXTE_MENU);
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}

	}
	
	/**
     * Indique si le bouton est survole
     * @return isFocus
     */
	public boolean getFocus() {
		return this.isFocus;
	}
	
	 /**
    * Le bouton est survole
    * @param isFocus Choisi si un bouton est visé
    */
	public void setFocus(boolean isFocus) {
		this.isFocus = isFocus;
	}
	
	/**
     * Indique si le bouton est selectionne
     * @return isTarget
     */
	public boolean getTarget() {
		return this.isTarget;
	}
	
	/**
     * Le bouton est selectionne
     * @param isTarget Choisi si un bouton est ciblé
     */
	public void setTarget(boolean isTarget) {
		this.isTarget = isTarget;
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int widthTexte;
		int heightTexte;
		int midTexte;
		//Recuperation taille texte
		FontMetrics metrics = g.getFontMetrics(boutonFont);
		widthTexte = metrics.stringWidth(texte);
		heightTexte = metrics.getHeight()-metrics.getLeading();
		midTexte = heightTexte/6;
		heightBouton = heightTexte + (heightTexte*15/100);
		
		this.setBounds((posX-(widthBouton/2)-3), (posY-(heightBouton/2)-3), widthBouton, heightBouton);
		g.setFont(boutonFont);
		
		if (getFocus()) { //Bouton survole

			//Affichage des boutons
			g.setColor(BOUTON_CONTOUR_SURVOLE); //Couleur Contour Bouton
			g.fillRoundRect( (posX-(widthBouton/2)-3) , (posY-(heightBouton/2)-3), (widthBouton+6), (heightBouton+6),12,12);
			g.setColor(BOUTON_SURVOLE); //Couleur Bouton
			g.fillRoundRect( (posX-(widthBouton/2)) , (posY-(heightBouton/2)), widthBouton, heightBouton,10,10);

			//Affichage texte
			g.setColor(TEXTE_NORMAL);
			g.drawString(texte,(posX-(widthTexte/2)),(posY+midTexte));

		} else if (getTarget()) { //Bouton selectionne

			//Affichage des boutons
			g.setColor(BOUTON_CONTOUR_SELECTIONNE); //Couleur Contour Bouton
			g.fillRoundRect( (posX-(widthBouton/2)-3) , (posY-(heightBouton/2)-3), (widthBouton+6), (heightBouton+6),12,12);
			g.setColor(BOUTON_SELECTIONNE); //Couleur Bouton
			g.fillRoundRect( (posX-(widthBouton/2)) , (posY-(heightBouton/2)), widthBouton, heightBouton,10,10);
			
			//Affichage texte
			g.setColor(TEXTE_SELECTIONNE);
			g.drawString(texte,(posX-(widthTexte/2)),(posY+midTexte));
		
		} else { //Bouton normal
			
			//Affichage des boutons
			g.setColor(BOUTON_CONTOUR); //Couleur Contour Bouton
			g.fillRoundRect( (posX-(widthBouton/2)-3) , (posY-(heightBouton/2)-3), (widthBouton+6), (heightBouton+6),12,12);
			g.setColor(BOUTON_NORMAL); //Couleur Bouton
			g.fillRoundRect( (posX-(widthBouton/2)) , (posY-(heightBouton/2)), widthBouton, heightBouton,10,10);
			
			//Affichage texte
			g.setColor(TEXTE_NORMAL);
			g.drawString(texte,(posX-(widthTexte/2)),(posY+midTexte));
		}
	}
}
