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
     * 
     * @return Indique si le bouton a la souris sur lui
     */
	public boolean getFocus() {
		return this.isFocus;
	}
	
	 /**
    * Choisi si le bouton est survole
    * @param isFocus S'il l'est ou non
    */
	public void setFocus(boolean isFocus) {
		this.isFocus = isFocus;
	}
	
	/**
     * 
     * @return S'il est selectionne
     */
	public boolean getTarget() {
		return this.isTarget;
	}
	
	/**
     * Choisi si le bouton est concerne par un clique de souris
     * @param isTarget Vrai ou faux 
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
		
		if (getFocus()) {
			//Affichage des boutons
			g.setColor(BOUTON_CONTOUR_SURVOLE); //Couleur Contour Bouton
			g.fillRoundRect( (posX-(widthBouton/2)-3) , (posY-(heightBouton/2)-3), (widthBouton+6), (heightBouton+6),12,12);
			g.setColor(BOUTON_SURVOLE); //Couleur Bouton
			g.fillRoundRect( (posX-(widthBouton/2)) , (posY-(heightBouton/2)), widthBouton, heightBouton,10,10);
			//Affichage texte
			g.setColor(TEXTE_NORMAL);
			g.setFont(boutonFont);
			g.drawString(texte,(posX-(widthTexte/2)),(posY+midTexte));
		} else if (getTarget()) {
			//Affichage des boutons
			g.setColor(BOUTON_CONTOUR_SELECTIONNE); //Couleur Contour Bouton
			g.fillRoundRect( (posX-(widthBouton/2)-3) , (posY-(heightBouton/2)-3), (widthBouton+6), (heightBouton+6),12,12);
			g.setColor(BOUTON_SELECTIONNE); //Couleur Bouton
			g.fillRoundRect( (posX-(widthBouton/2)) , (posY-(heightBouton/2)), widthBouton, heightBouton,10,10);
			//Affichage texte
			g.setColor(TEXTE_SELECTIONNE);
			g.setFont(boutonFont);
			g.drawString(texte,(posX-(widthTexte/2)),(posY+midTexte));
		} else {
			//Affichage des boutons
			g.setColor(BOUTON_CONTOUR); //Couleur Contour Bouton
			g.fillRoundRect( (posX-(widthBouton/2)-3) , (posY-(heightBouton/2)-3), (widthBouton+6), (heightBouton+6),12,12);
			g.setColor(BOUTON_NORMAL); //Couleur Bouton
			g.fillRoundRect( (posX-(widthBouton/2)) , (posY-(heightBouton/2)), widthBouton, heightBouton,10,10);
			//Affichage texte
			g.setColor(TEXTE_NORMAL);
			g.setFont(boutonFont);
			g.drawString(texte,(posX-(widthTexte/2)),(posY+midTexte));
		}
	}
}