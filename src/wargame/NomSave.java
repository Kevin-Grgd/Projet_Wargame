package wargame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NomSave extends JPanel implements IConfig {

	private static final long serialVersionUID = 1L;
	
	private transient BoutonMenu aValider;
    private transient BoutonMenu aRetour;
    private transient PanneauJeu aGame;
    private transient JFrame aWindow;
    private transient MenuLoadSave aMenuLoadSave;
    private transient MenuAccueil aMenuAccueil;
    private transient NomSave vTemp;
    private transient JTextField aSaveNameSaisie;
    private transient String texte;
    private transient Image img_BackgroundLoadSave;
    private transient Font textFieldFont;
    private transient int choixSave;
    private transient Carte aCarte;
    
    public JPanel saveName() {

        JPanel vSaveName = new JPanel();

        vSaveName.setOpaque(false);
        vSaveName.setPreferredSize(new Dimension(LARGEUR_FENETRE, HAUTEUR_FENETRE));
        
        int textFieldWidth;
        int textFieldHeight;

        //Recuperation taille texte
        FontMetrics metrics = aSaveNameSaisie.getFontMetrics(textFieldFont);
        textFieldWidth = metrics.stringWidth(texte);
        textFieldHeight = metrics.getHeight()-metrics.getLeading();

        //Modification taille textField
        aSaveNameSaisie.setBounds( ((LARGEUR_FENETRE/2)-(textFieldWidth/2)) , ((HAUTEUR_FENETRE/2)-(textFieldHeight/2)-70), (textFieldWidth+8), (textFieldHeight+5));
        aSaveNameSaisie.setFont(textFieldFont);
        aSaveNameSaisie.setHorizontalAlignment(JTextField.CENTER);

        this.add(aSaveNameSaisie);
        vSaveName.repaint();

        vTemp = this;

        /**
		* GESTION SOURIS
		**/
		this.addMouseListener( new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if (e.getButton() == MouseEvent.BUTTON1) {
					
					if(aValider.getBounds().contains(e.getPoint())){
						aValider.setFocus(false);
						aValider.setTarget(true);
						repaint();
					}

					if(aRetour.getBounds().contains(e.getPoint())){
						aRetour.setFocus(false);
						aRetour.setTarget(true);
						repaint();
					}
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                actionMenu();
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				boutonHover(e);
				repaint();
			}
		});
        /**
		* FIN GESTION SOURIS
		**/

        return vSaveName;
    }

    public NomSave(PanneauJeu pGame, JFrame pWindow, MenuAccueil pMenuAccueil,  Carte pCarte, int pChoixSave) {
    	aGame = pGame;
        aWindow = pWindow;
        aMenuAccueil = pMenuAccueil;
        aCarte = pCarte;
        choixSave = pChoixSave;
        
        texte = "Nom de la sauvegarde";

        aValider = new BoutonMenu(((LARGEUR_FENETRE/2)+(LARGEUR_BOUTON/2)+25), (HAUTEUR_FENETRE-250), "Valider");
        aRetour = new BoutonMenu(((LARGEUR_FENETRE/2)-(LARGEUR_BOUTON/2)-25), (HAUTEUR_FENETRE-250), "Retour");
        aSaveNameSaisie = new JTextField(50);

        try {
            //Image background
            URL urlBackground = this.getClass().getResource("/resources/load_save_background.jpg");
            img_BackgroundLoadSave = ImageIO.read(urlBackground);
            img_BackgroundLoadSave = img_BackgroundLoadSave.getScaledInstance(LARGEUR_FENETRE, HAUTEUR_FENETRE, Image.SCALE_SMOOTH);

            //Font perso
            InputStream isFont = this.getClass().getResourceAsStream("/resources/Font/Breathe-Fire.otf");
            textFieldFont = Font.createFont(Font.TRUETYPE_FONT, isFont);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(textFieldFont);
            textFieldFont = textFieldFont.deriveFont(TAILLE_TEXTE_FIELD);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        
        this.add(saveName());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img_BackgroundLoadSave,0,0,this);
        aValider.paintComponent(g);
        aRetour.paintComponent(g);
        
        textFieldFont = textFieldFont.deriveFont(80f);
        g.setFont(textFieldFont);
      //Recuperation taille texte
        FontMetrics metrics = g.getFontMetrics();
        String txt = "Nom de la sauvegarde :";
        int textWidth = metrics.stringWidth(txt);
        g.setColor(Color.WHITE);
        g.drawString(txt, ((LARGEUR_FENETRE/2)-(textWidth/2)), 250);
    }

    /**
	 * Gestion dynamique des boutons au passage de la souris
	 * @param e
	 */
    private void boutonHover(MouseEvent e) {
        if (aValider.getBounds().contains(e.getX(),e.getY())) {
            aValider.setFocus(true);
        } else {
            aValider.setFocus(false);
        }

        if (aRetour.getBounds().contains(e.getX(),e.getY())) {
            aRetour.setFocus(true);
        } else {
            aRetour.setFocus(false);
        }
    }

    private void actionMenu() {
        String saveName;
        if (aValider.getTarget()) {
            try {
				Thread.sleep(50);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
            saveName = aSaveNameSaisie.getText();

            if (saveName != null && !saveName.trim().isEmpty()) {
                aCarte.Sauvegarde(choixSave,saveName);
			    aValider.setTarget(false);
			    aWindow.remove(vTemp);
			    aMenuLoadSave = new MenuLoadSave(aGame, aWindow,aMenuAccueil,SAUVEGARDER,JEU);
		        aWindow.add(aMenuLoadSave);
		        aMenuLoadSave.focusPanel();
		        aWindow.repaint();
		        aWindow.pack();
            }
        }

        if (aRetour.getTarget()) {
            try {
                Thread.sleep(50);
            } catch(InterruptedException ex) {
            	Thread.currentThread().interrupt();
            }
            aRetour.setTarget(false);
            aWindow.remove(vTemp);
            aWindow.add(aMenuLoadSave);
            aMenuLoadSave.focusPanel();
            aWindow.repaint();
            aWindow.pack();
        }
    }
}