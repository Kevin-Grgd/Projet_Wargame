package wargame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MenuLoadSave extends JPanel implements IConfig {
    
    private static final long serialVersionUID = 1L;

    public BoutonLoadSave[] aEnsemble_Boutons;
    private transient BoutonMenu aCharger_Sauvegarder;
    private transient BoutonMenu aRetour; 
    private transient int choixSave;
	private transient Image img_BackgroundLoadSave;
	private transient Image  img_LoadSave;
    private transient PanneauJeu aGame;
    private transient JFrame aWindow;
    private transient MenuAccueil aMenuAccueil;
    private transient boolean aCharge; //True Chargement | False : Sauvegarde
    private transient MenuLoadSave vTemp;
    private transient int aAppel;
    private transient Carte aCarte;
    private transient int nbSave;
    private transient NomSave aNomSave;
    private transient int newPosY;
    
    
    public JPanel load() {

        JPanel load = new JPanel();

        int x = LARGEUR_FENETRE/2;
        int y = 180;
        String texte;
        Carte toutesCartes = new Carte();
        
        load.setOpaque(false);
        load.setPreferredSize(new Dimension(LARGEUR_FENETRE, HAUTEUR_FENETRE));
        
        vTemp = this;
        
        if (aCharge) { //Mode chargement de partie : creation  nbSave + 1 boutons
        	
        	aEnsemble_Boutons = new BoutonLoadSave[nbSave];
        	for (int i = 1; i <= nbSave; i++) {
        		toutesCartes.Recharger(i);
    			texte = ""+toutesCartes.getSaveName();
    			//System.out.println("Nom save :"+texte);
        		aEnsemble_Boutons[i-1] = new BoutonLoadSave(x,y,i,texte);
        		y = y + HAUTEUR_BOUTON_LOAD_SAVE + 35;
        	}
        	
        } else { //Mode sauvegarde de partie : creation  nbSave + 1 boutons
        	
        	nbSave++;
        	aEnsemble_Boutons = new BoutonLoadSave[nbSave];
        	
        	for (int i = 1; i <= nbSave; i++) {
        		if (i == nbSave) {
        			System.out.println("ok");
        			texte = " - ";
        		} else {
        			toutesCartes.Recharger(i);
        			texte = ""+toutesCartes.getSaveName();
        		}
        		aEnsemble_Boutons[i-1] = new BoutonLoadSave(x,y,i,texte);
        		y = y + HAUTEUR_BOUTON_LOAD_SAVE + 35;
        	}
        }

        /**
        * GESTION SOURIS
        **/
        this.addMouseListener( new MouseAdapter() {
           @Override
           public void mousePressed(MouseEvent e) {

               if (e.getButton() == MouseEvent.BUTTON1) {

                  for (int i = 0; i < nbSave; i++) {
                      if (aEnsemble_Boutons[i].getBounds().contains(e.getPoint())) {
                          aEnsemble_Boutons[i].setFocus(false);
                          aEnsemble_Boutons[i].setTarget(true);
                      } else {
                          aEnsemble_Boutons[i].setTarget(false);
                      }
                      repaint();
                  }

                  if (aRetour.getBounds().contains(e.getPoint())) {
                      aRetour.setFocus(false);
                      aRetour.setTarget(true);
                      repaint();
                  }

                  if (aCharger_Sauvegarder.getBounds().contains(e.getPoint())) {
                      aCharger_Sauvegarder.setFocus(false);
                      aCharger_Sauvegarder.setTarget(true);
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

        this.addMouseWheelListener(new MouseWheelListener() {
        	@Override
            public void mouseWheelMoved(MouseWheelEvent e) {
        		if (e.getWheelRotation() < 0) {
        			for (int i = 0; i < nbSave; i++) {
        				newPosY = aEnsemble_Boutons[i].getPosY() - 10;
        				if(aEnsemble_Boutons[nbSave-1].getPosY() >= (180+HAUTEUR_BOUTON_LOAD_SAVE)) {
        					aEnsemble_Boutons[i].setPosY(newPosY);
        					repaint();
        				}
        			}
        		} else {
        			for (int i = 0; i < nbSave; i++) {
        				newPosY = aEnsemble_Boutons[i].getPosY() + 10;
        				if(aEnsemble_Boutons[0].getPosY() <= (680-HAUTEUR_BOUTON_LOAD_SAVE)) {
        					aEnsemble_Boutons[i].setPosY(newPosY);
            				repaint();
        				}
        			}
        		}
        	}
        });
        /**
        * FIN GESTION SOURIS
        **/
        
        return load;
    }

    /**
     * Constructeur MenuLoadSave
     * @param pGame
     * @param pWindow
     * @param pMenuAccueil
     * @param pCharge
     */
    public MenuLoadSave(PanneauJeu pGame, JFrame pWindow, MenuAccueil pMenuAccueil, boolean pCharge, int pAppel) {
        aGame = pGame;
        aWindow = pWindow;
        aMenuAccueil = pMenuAccueil;
        aCharge = pCharge ;
        aAppel = pAppel; //Pour savoir si on reviens au menu ou au jeu
        
        if (aCharge == CHARGER) {
            aCharger_Sauvegarder = new BoutonMenu( ((LARGEUR_FENETRE/2)+(LARGEUR_BOUTON/2)+25), (HAUTEUR_FENETRE-50), "Charger");
            aCarte = new Carte();
        } else if (aCharge == SAUVEGARDER) {
            aCharger_Sauvegarder = new BoutonMenu( ((LARGEUR_FENETRE/2)+(LARGEUR_BOUTON/2)+25), (HAUTEUR_FENETRE-50), "Sauvegarder");
            aCarte = pGame.getCarte();
        }

        aRetour = new BoutonMenu( ((LARGEUR_FENETRE/2)-(LARGEUR_BOUTON/2)-25) , (HAUTEUR_FENETRE-50), "Retour");

        try {
            URL urlLoadSave = null;

            URL urlBackground = this.getClass().getResource("/resources/load_save_background.jpg");

            
            if (aCharge) {
                urlLoadSave = this.getClass().getResource("/resources/img_loadGame.png");
            } else {
                urlLoadSave = this.getClass().getResource("/resources/img_saveGame.png");
            }

            img_BackgroundLoadSave = ImageIO.read(urlBackground);
            img_LoadSave = ImageIO.read(urlLoadSave);

            //Redimensionnement img_LoadSave
            int img_LoadSaveWidth = img_LoadSave.getWidth(null);
            int img_LoadSaveHeight = img_LoadSave.getHeight(null);
            int newHeightImgLoadSave = 100;
            int newWidthImgLoadSave = newHeightImgLoadSave*img_LoadSaveWidth/img_LoadSaveHeight;

            img_BackgroundLoadSave = img_BackgroundLoadSave.getScaledInstance(LARGEUR_FENETRE, HAUTEUR_FENETRE, Image.SCALE_SMOOTH);
            img_LoadSave = img_LoadSave.getScaledInstance(newWidthImgLoadSave, newHeightImgLoadSave, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        setLayout(new BorderLayout());
        
        nbSave = getSaveNumber();
        this.add(load());
    }

    @Override
    protected void paintComponent(Graphics g) {
        int imgWidth = img_LoadSave.getWidth(null);
        super.paintComponent(g);
        g.drawImage(img_BackgroundLoadSave,0,0,this);
        g.drawImage(img_LoadSave,( (LARGEUR_FENETRE/2)-(imgWidth/2) ), 20, this);
        
    	for (int i= 0; i < nbSave; i++) {
    		if (aEnsemble_Boutons[i].getPosY() >= 180 && aEnsemble_Boutons[i].getPosY() <= 670) {
    			aEnsemble_Boutons[i].paintComponent(g);
    		}
        }

        aRetour.paintComponent(g);  
        aCharger_Sauvegarder.paintComponent(g);      
    }
    
    /**
	 * Remet le focus sur le bon panel
	 */
	public void focusPanel(){
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    /**
	 * Gestion dynamique des boutons au passage de la souris
	 * @param e
	 */
    private void boutonHover(MouseEvent e) {
        for (int i = 0; i < nbSave; i++) {

            if (this.aEnsemble_Boutons[i].getBounds().contains(e.getX(), e.getY())) {
                this.aEnsemble_Boutons[i].setFocus(true);
            } else {
                this.aEnsemble_Boutons[i].setFocus(false);
            }
        }

        if (this.aRetour.getBounds().contains(e.getX(),e.getY())) {
            this.aRetour.setFocus(true);
        } else {
            this.aRetour.setFocus(false);
        }

        if (this.aCharger_Sauvegarder.getBounds().contains(e.getX(),e.getY())) {
            this.aCharger_Sauvegarder.setFocus(true);
        } else {
            this.aCharger_Sauvegarder.setFocus(false);
        }
    }

    private void actionMenu() {
        for (int i = 0; i < nbSave; i++) {
            if (aEnsemble_Boutons[i].getTarget()) {
                choixSave = i+1;
                //System.out.println("Partie numero "+choixSave+" selectionne");
            }
        }

        if (aRetour.getTarget()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            aRetour.setTarget(false);
            aWindow.remove(vTemp);

            if (aAppel == MENU_ACCUEIL) {
                aWindow.add(aMenuAccueil);
                aMenuAccueil.focusPanel();

            } else if (aAppel == JEU) {
                aWindow.add(aGame);
                aGame.focusPanel();
            }
            
            aWindow.repaint();
            aWindow.pack();
        }

        if (aCharger_Sauvegarder.getTarget()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            aCharger_Sauvegarder.setTarget(false);
            
            if (aCharge == CHARGER) {

            	if (nbSave > 0) {
                    //System.out.println("Chargement partie numero "+choixSave);
                    aCarte.Recharger(choixSave);
                    aGame = new PanneauJeu(aWindow,aCarte);
                    aWindow.remove(vTemp);
                    aWindow.add(aGame);
                    aGame.focusPanel();
                    aWindow.repaint();
                    aWindow.pack();

                } else { //Il n'y a aucune partie sauvegarde, par securite -> Retour menu principal
                    aWindow.remove(vTemp);
                    aWindow.add(aMenuAccueil);
                    aMenuAccueil.focusPanel();
                    aWindow.repaint();
                    aWindow.pack();
                    System.out.println("Il n'y a aucune partie sauvegarde");
                    System.out.println("Retour menu principal");
                }

            } else if (aCharge == SAUVEGARDER) {

                if (choixSave > 0) {
                    //System.out.println("Sauvegarder la partie sur le slot numero "+choixSave);
                    aNomSave = new NomSave(aGame,aWindow,vTemp, aMenuAccueil,aCarte,choixSave);
                    aWindow.remove(vTemp);
                    aWindow.add(aNomSave);
                    aWindow.repaint();
                    aWindow.pack();
                }
            }
        }
    }

    private int getSaveNumber(){
        String usrDirectory = System.getProperty("user.dir");
        File vSauv = new File(usrDirectory + "/saves/");
        if (vSauv.isDirectory()){
            if (vSauv.list().length > 0){
                nbSave = vSauv.listFiles().length;
            }
            else{
                nbSave = 0;
            }
        }
        return nbSave;
    }
}
