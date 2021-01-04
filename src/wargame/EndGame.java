package wargame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class EndGame extends JPanel implements IConfig {

    private static final long serialVersionUID = 1L;
    private transient Image img_End;
    private transient Image img_Background;
    private transient PanneauJeu aGame;
    private transient JFrame aWindow;
    private transient Accueil aAccueil;
    private transient BoutonMenu aRejouer = null;
    private transient BoutonMenu aMainMenu = null;
    private transient BoutonMenu aQuitter = null;
    private transient boolean aVictory;

    public JPanel endScreen() {
        JPanel endScreen = new JPanel();
        
        endScreen.setOpaque(false);
        endScreen.setPreferredSize(new Dimension(LARGEUR_FENETRE,HAUTEUR_FENETRE));

        EndGame vTemp = this;

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

                if (aRejouer.getTarget() ) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    aRejouer.setTarget(false);
                    aWindow.remove(vTemp);
                    aWindow.add(aGame);
                    aWindow.repaint();
                    aWindow.pack();
                }

                if (aMainMenu.getTarget() ) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    aMainMenu.setTarget(false);
                    aWindow.remove(vTemp);
                    aWindow.add(aAccueil);
                    aWindow.repaint();
                    aWindow.pack();
                }

                if (aQuitter.getTarget() ) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                    aQuitter.setTarget(false);
                    System.exit(0);
                }
            }
        });

        this.addMouseMotionListener(new MouseAdapter () {

            @Override
            public void mouseMoved(MouseEvent e) {
                boutonHover(e);
                repaint();
            }
        });

        return endScreen;
    }

    public EndGame(PanneauJeu pGame, JFrame pWindow, Accueil pAccueil, boolean pVictory) {
        this.aGame = pGame;
        this.aWindow = pWindow;
        this.aAccueil = pAccueil;
        this.aVictory = pVictory;

        aRejouer = new BoutonMenu(LARGEUR_FENETRE/2, 500, "Rejouer");
        aQuitter = new BoutonMenu(LARGEUR_FENETRE/2, 575, "Quitter");
        aMainMenu = new BoutonMenu(LARGEUR_FENETRE/2, 650, "Retour au menu principal");

        try {
            URL urlBackground = null;
            URL urlEnd = null;

            if(aVictory == true) {
                urlBackground = this.getClass().getResource("/resources/win_background.jpg");
                urlEnd = this.getClass().getResource("/resources/img_endWin.png");
            } else if (aVictory == false) {
                urlBackground = this.getClass().getResource("/resources/loose_background.jpg");
                urlEnd = this.getClass().getResource("/resources/img_endLoose.png");
            } else {
                System.out.println("Erreur switch victory");
                System.exit(0);
            }

            img_Background = ImageIO.read(urlBackground);
            img_End = ImageIO.read(urlEnd);
            
            int imgEndWidth = img_End.getWidth(null);
            int imgEndHeight = img_End.getHeight(null);
            int newWidthImgEnd = 700;
            int newHeightImgEnd = newWidthImgEnd*imgEndHeight/imgEndWidth;
            img_Background = img_Background.getScaledInstance(LARGEUR_FENETRE, HAUTEUR_FENETRE, Image.SCALE_SMOOTH);
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
        g.drawImage(img_Background,0,0,this);
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
}