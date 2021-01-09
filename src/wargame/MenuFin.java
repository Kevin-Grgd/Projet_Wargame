package wargame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuFin extends JPanel implements IConfig {

    private static final long serialVersionUID = 1L;
    private transient Image img_End;
    private transient Image img_BackgroundFin;
    private transient PanneauJeu aGame;
    private transient JFrame aWindow;
    private transient MenuAccueil aMenuAccueil;
    private transient BoutonMenu aRejouer = null;
    private transient BoutonMenu aMainMenu = null;
    private transient BoutonMenu aQuitter = null;
    private transient boolean aVictory;
    private transient MenuFin vTemp;
    private transient int keyDetected;
    private transient boolean keyPushedOnce;
    private transient Carte aCarte;

    public JPanel endScreen() {
        JPanel endScreen = new JPanel();
        
        endScreen.setOpaque(false);
        endScreen.setPreferredSize(new Dimension(LARGEUR_FENETRE,HAUTEUR_FENETRE));

        vTemp = this;

        /**
		* GESTION SOURIS
		**/
        this.addMouseListener( new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if (e.getButton() == MouseEvent.BUTTON1) {

                    if (aRejouer.getBounds().contains(e.getPoint())) {
                        aRejouer.setFocus(false);
                        aRejouer.setTarget(true);
                        repaint();
                    }

                    if (aMainMenu.getBounds().contains(e.getPoint())) {
                        aMainMenu.setFocus(false);
                        aMainMenu.setTarget(true);
                        repaint();
                    }

                    if (aQuitter.getBounds().contains(e.getPoint())) {
                        aQuitter.setFocus(false);
                        aQuitter.setTarget(true);
                        repaint();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                actionMenu();                
            }
        });

        this.addMouseMotionListener(new MouseAdapter () {

            @Override
            public void mouseMoved(MouseEvent e) {
                boutonHover(e);
                repaint();
            }
        });
        /**
		* FIN GESTION SOURIS
		**/

		/**
		* GESTION CLAVIER
		**/
		this.addKeyListener(new KeyListener () {

			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				keyDetected = e.getKeyCode();

				while (!keyPushedOnce) {
					switch( keyDetected ) { 

        				case KeyEvent.VK_DOWN: //Vers le bas
							//System.out.println("FLECHE BAS");
							if (!(aRejouer.getFocus()) && !(aQuitter.getFocus()) && !(aMainMenu.getFocus())) {
                                aRejouer.setFocus(true);
                            } else if (aRejouer.getFocus()) {
                                aRejouer.setFocus(false);
                                aQuitter.setFocus(true);
                            } else if (aQuitter.getFocus()) {
                                aQuitter.setFocus(false);
                                aMainMenu.setFocus(true);
                            } else if (aMainMenu.getFocus()) {
                                aMainMenu.setFocus(false);
                                aRejouer.setFocus(true);
                            }
							removeTarget();
							keyPushedOnce = true;
							break;


						case KeyEvent.VK_UP: //Vers le haut
							//System.out.println("FLECHE HAUT");
							if ( !(aRejouer.getFocus()) && !(aQuitter.getFocus()) && !(aMainMenu.getFocus())) {
                                aMainMenu.setFocus(true);
                            } else if (aRejouer.getFocus()) {
                                aRejouer.setFocus(false);
                                aMainMenu.setFocus(true);
                            } else if (aQuitter.getFocus()) {
                                aQuitter.setFocus(false);
                                aRejouer.setFocus(true);
                            } else if (aMainMenu.getFocus()) {
                                aMainMenu.setFocus(false);
                                aQuitter.setFocus(true);
                            }
							removeTarget();
							keyPushedOnce = true;
							break;


						case KeyEvent.VK_ENTER: //Entree
							//System.out.println("ENTREE");
							if (aRejouer.getFocus()) {
								aRejouer.setTarget(true);
							} else if (aMainMenu.getFocus()) {
								aMainMenu.setTarget(true);
							} else if (aQuitter.getFocus()) {
								aQuitter.setTarget(true);
							}
							removeFocus();
							keyPushedOnce = true;
							break;
					}
					repaint();
				}
			}

			
			@Override
			public void keyReleased(KeyEvent e) {
				keyDetected = e.getKeyCode();
				keyPushedOnce = false;

				if (keyDetected == KeyEvent.VK_ENTER) {
					actionMenu();
				}
			}
		});
		/**
		* FIN GESTION CLAVIER
		**/

        return endScreen;
    }

    public MenuFin(PanneauJeu pGame, JFrame pWindow, MenuAccueil pMenuAccueil, boolean pVictory) {
        this.aGame = pGame;
        this.aWindow = pWindow;
        this.aMenuAccueil = pMenuAccueil;
        this.aVictory = pVictory;

        aRejouer = new BoutonMenu(LARGEUR_FENETRE/2, 500, "Rejouer");
        aQuitter = new BoutonMenu(LARGEUR_FENETRE/2, 575, "Quitter");
        aMainMenu = new BoutonMenu(LARGEUR_FENETRE/2, 650, "Retour au menu principal");

        try {
            URL urlBackground = null;
            URL urlEnd = null;

            if(aVictory) {
                urlBackground = this.getClass().getResource("/resources/win_background.jpg");
                urlEnd = this.getClass().getResource("/resources/img_endWin.png");
            } else {
                urlBackground = this.getClass().getResource("/resources/loose_background.jpg");
                urlEnd = this.getClass().getResource("/resources/img_endLoose.png");
            }

            img_BackgroundFin = ImageIO.read(urlBackground);
            img_End = ImageIO.read(urlEnd);
            
            int imgEndWidth = img_End.getWidth(null);
            int imgEndHeight = img_End.getHeight(null);
            int newWidthImgEnd = 700;
            int newHeightImgEnd = newWidthImgEnd*imgEndHeight/imgEndWidth;
            img_BackgroundFin = img_BackgroundFin.getScaledInstance(LARGEUR_FENETRE, HAUTEUR_FENETRE, Image.SCALE_SMOOTH);
            img_End = img_End.getScaledInstance(newWidthImgEnd, newHeightImgEnd, Image.SCALE_SMOOTH);
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        this.add(endScreen());
    }

    @Override
    protected void paintComponent(Graphics g) {
        int imgWidth = img_End.getWidth(null);
        super.paintComponent(g);
        g.drawImage(img_BackgroundFin,0,0,this);
        g.drawImage(img_End, ((LARGEUR_FENETRE/2)-(imgWidth/2)), 25, this);

        aRejouer.paintComponent(g);
        aMainMenu.paintComponent(g);
        aQuitter.paintComponent(g);
    }

    private void boutonHover(MouseEvent e) {
		if (this.aRejouer.getBounds().contains(e.getX(),e.getY())) {
			this.aRejouer.setFocus(true);
		} else {
			this.aRejouer.setFocus(false);
		}
		if (this.aMainMenu.getBounds().contains(e.getX(),e.getY())) {
			this.aMainMenu.setFocus(true);
		} else {
			this.aMainMenu.setFocus(false);
		}
		if (this.aQuitter.getBounds().contains(e.getX(),e.getY())) {
			this.aQuitter.setFocus(true);
		} else {
			this.aQuitter.setFocus(false);
		}
	}

    public void actionMenu() {
        if (aRejouer.getTarget()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            aRejouer.setTarget(false);
            aWindow.remove(vTemp);
            aCarte = new Carte();
            aGame = new PanneauJeu(aWindow,aCarte);
            aWindow.add(aGame);
            aGame.focusPanel();
            aWindow.repaint();
            aWindow.pack();
        }

        if (aMainMenu.getTarget()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            aMainMenu.setTarget(false);
            aWindow.remove(vTemp);
            aWindow.add(aMenuAccueil);
            aMenuAccueil.focusPanel();
            aWindow.repaint();
            aWindow.pack();
        }

        if (aQuitter.getTarget()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            aQuitter.setTarget(false);
            System.exit(0);
        }
    }

    /**
	 * Remet le focus sur le bon panel
	 */
	public void focusPanel(){
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    /**
	 * Supprime le target de tous les boutons
	 */
	private void removeTarget() {
		aRejouer.setTarget(false);
		aMainMenu.setTarget(false);
		aQuitter.setTarget(false);
	}

	/**
	 * Supprime le focus de tous les boutons
	 */
	private void removeFocus() {
		aRejouer.setFocus(false);
		aMainMenu.setFocus(false);
		aQuitter.setFocus(false);
	}
}