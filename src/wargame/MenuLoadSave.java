package wargame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuLoadSave extends JPanel implements IConfig {
    
    private static final long serialVersionUID = 1L;
    public BoutonLoadSave[] aEnsemble_Boutons;
	private transient Image img_BackgroundLoadSave;
    private transient PanneauJeu aGame;
    private transient JFrame aWindow;
    private transient MenuAccueil aMenuAccueil;
    private transient boolean aCharge; //True Chargement | False : Sauvegarde
    private transient MenuLoadSave vTemp;

    public JPanel load() {

        JPanel load = new JPanel();

        int x = LARGEUR_FENETRE/2;
        int y = 150;
        String texte;
        
        load.setOpaque(false);
        load.setPreferredSize(new Dimension(LARGEUR_FENETRE, HAUTEUR_FENETRE));
        
        vTemp = this;

        for (int i = 1; i <= NB_MAX_SAVE; i++) {
            texte = "Partie "+i;

            aEnsemble_Boutons[i-1] = new BoutonLoadSave(x,y,i,texte);

            y = y + HAUTEUR_BOUTON_LOAD_SAVE + 35;
        }
        
        return load;
    }

    public MenuLoadSave(PanneauJeu pGame, JFrame pWindow, MenuAccueil pMenuAccueil, boolean pCharge) {
        this.aGame = pGame;
        this.aWindow = pWindow;
        this.aMenuAccueil = pMenuAccueil;
        this.aCharge = pCharge ;
        this.aEnsemble_Boutons = new BoutonLoadSave[NB_MAX_SAVE];

        try {
            URL urlLoadSave = null;

            URL urlBackground = this.getClass().getResource("/resources/load_save_background.jpg");

            /*
            if (aCharge) {
                urlLoadSave = this.getClass().getResource("/resources/load_game.png");
            } else {
                urlLoadSave = this.getClass().getResource("/resources/save_game.png");
            }*/

            img_BackgroundLoadSave = ImageIO.read(urlBackground);
            //img_LoadSave = ImageIO.read(urlLoadSave);

            img_BackgroundLoadSave = img_BackgroundLoadSave.getScaledInstance(LARGEUR_FENETRE, HAUTEUR_FENETRE, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        this.add(load());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img_BackgroundLoadSave,0,0,this);

        for (int i = 0; i < NB_MAX_SAVE; i++) {
        	aEnsemble_Boutons[i].paintComponent(g);
        } 
        
    }
    
    /**
	 * Remet le focus sur le bon panel
	 */
	public void focusPanel(){
        this.setFocusable(true);
        this.requestFocusInWindow();
    }
}
