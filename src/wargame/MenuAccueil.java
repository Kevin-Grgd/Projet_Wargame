package wargame;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.net.URL;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;


public class MenuAccueil extends JPanel implements IConfig, LineListener {
	
	private static final long serialVersionUID = 1L;
	private transient Image img_BackgroundAccueil;
	private transient Image logoJeu;
	private transient PanneauJeu aGame;
	private transient JFrame aWindow;
	//private transient MenuFin aMenuFin;
	private transient MenuLoadSave aMenuLoadSave;
	private transient BoutonMenu aJouer = null;
	private transient BoutonMenu aQuitter = null;
	private transient BoutonMenu aCharger = null;
	private transient int choixMusic;
	private transient boolean playOnce;
	private transient Clip audioClip;
	private transient int keyDetected;
	private transient boolean keyPushedOnce = false;
	private transient MenuAccueil vTemp;
	
	/**
	 * 	
	 * @return Le menu d'MenuAccueil
	 */
	public JPanel menu() {
		
		JPanel menu = new JPanel();
		
		
		menu.setOpaque(false);
		menu.setPreferredSize(new Dimension(LARGEUR_FENETRE, HAUTEUR_FENETRE));
		vTemp = this;
		//aMenuFin = new MenuFin(aGame,aWindow,vTemp,false);
		aMenuLoadSave = new MenuLoadSave(aGame,aWindow,vTemp,CHARGER,MENU_ACCUEIL);

		
		/**
		* GESTION SOURIS
		**/
		this.addMouseListener( new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

				if (e.getButton() == MouseEvent.BUTTON1) {
					
					if(aJouer.getBounds().contains(e.getPoint())){
						aJouer.setFocus(false);
						aJouer.setTarget(true);
						repaint();
					}

					if(aCharger.getBounds().contains(e.getPoint())){
						aCharger.setFocus(false);
						aCharger.setTarget(true);
						repaint();
					}

					if(aQuitter.getBounds().contains(e.getPoint())){
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

		/**
		* GESTION CLAVIER
		**/
		this.addKeyListener(new KeyListener () {

			@Override
			public void keyTyped(KeyEvent e) {
				// Empty
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				keyDetected = e.getKeyCode();

				while (!keyPushedOnce) {
					switch( keyDetected ) { 

        				case KeyEvent.VK_DOWN: //Vers le bas
							//System.out.println("FLECHE BAS");
							if ( !(aJouer.getFocus()) && !(aCharger.getFocus()) && !(aQuitter.getFocus())) {
								aJouer.setFocus(true);
							} else if (aJouer.getFocus()) {
								aJouer.setFocus(false);
								aCharger.setFocus(true);
							} else if (aCharger.getFocus()) {
								aCharger.setFocus(false);
								aQuitter.setFocus(true);
							} else if (aQuitter.getFocus()) {
								aQuitter.setFocus(false);
								aJouer.setFocus(true);
							}
							removeTarget();
							keyPushedOnce = true;
							break;


						case KeyEvent.VK_UP: //Vers le haut
							//System.out.println("FLECHE HAUT");
							if ( !(aJouer.getFocus()) && !(aCharger.getFocus()) && !(aQuitter.getFocus())) {
								aQuitter.setFocus(true);
							} else if (aJouer.getFocus()) {
								aJouer.setFocus(false);
								aQuitter.setFocus(true);
							} else if (aCharger.getFocus()) {
								aCharger.setFocus(false);
								aJouer.setFocus(true);
							
							} else if (aQuitter.getFocus()) {
								aQuitter.setFocus(false);
								aCharger.setFocus(true);
							}
							removeTarget();
							keyPushedOnce = true;
							break;


						case KeyEvent.VK_ENTER: //Entree
							//System.out.println("ENTREE");
							if (aJouer.getFocus()) {
								aJouer.setTarget(true);
							} else if (aCharger.getFocus()) {
								aCharger.setTarget(true);
							} else if (aQuitter.getFocus()) {
								aQuitter.setTarget(true);
							}
							removeFocus();
							keyPushedOnce = true;
							break;
							
						default:
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

		return menu;		
	}

	/**
	 * Constructeur MenuAccueil
	 * @param pGame
	 * @param pWindow
	 */
	public MenuAccueil(JFrame pWindow) {
		aWindow = pWindow;
		aGame = new PanneauJeu(aWindow,null);
		
		/*
		//Background : Aragorn
		aJouer = new BoutonMenu(LARGEUR_FENETRE/2, 500, "Jouer");
		aCharger = new BoutonMenu(LARGEUR_FENETRE/2, 575, "Charger une partie");
		aQuitter = new BoutonMenu(LARGEUR_FENETRE/2, 650, "Quitter");
		String urlBackground = "/resources/accueil_background.jpg";
		*/

		/*
		//Background : Gollum
		aJouer = new BoutonMenu(375, 500, "Jouer");
		aCharger = new BoutonMenu(375, 575, "Charger une partie");
		aQuitter = new BoutonMenu(375, 650, "Quitter");
		String urlBackground = "/resources/accueil_background_2.jpg";
		*/
		
		
		//Backgroung : Village
		aJouer = new BoutonMenu(950, 550, "Jouer");
		aCharger = new BoutonMenu(950, 625, "Charger une partie");
		aQuitter = new BoutonMenu(950, 700, "Quitter");
		String urlBackground = "/resources/accueil_background_3.jpg";
		
		
		choixMusic = MUSIC_1;
		
		//Chargement des  images
		try {
			URL url = this.getClass().getResource(urlBackground);
			URL urlLogo = this.getClass().getResource("/resources/wargame_logo.png");
			img_BackgroundAccueil = ImageIO.read(url);
			logoJeu = ImageIO.read(urlLogo);			
			img_BackgroundAccueil = img_BackgroundAccueil.getScaledInstance(LARGEUR_FENETRE, HAUTEUR_FENETRE, Image.SCALE_SMOOTH);
			
		} catch (IOException e) {
			e.printStackTrace();			
		}
		
		setLayout(new BorderLayout());
		this.add(menu());
		/*
		if (!playOnce) {
			playMusic();
			playOnce = true;
		}*/
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		int logoWidth = logoJeu.getWidth(null);
		super.paintComponent(g);
		g.drawImage(img_BackgroundAccueil,0,0,this);
		g.drawImage(logoJeu, ((LARGEUR_FENETRE/2)-(logoWidth/2)), 25,this);

		aJouer.paintComponent(g);
		aQuitter.paintComponent(g);
		aCharger.paintComponent(g);
	}
	
	/**
	 * Gestion dynamique des boutons au passage de la souris
	 * @param e
	 */
	private void boutonHover(MouseEvent e) {
		if (this.aJouer.getBounds().contains(e.getX(),e.getY())) {
			this.aJouer.setFocus(true);
		} else {
			this.aJouer.setFocus(false);
		}

		if (this.aQuitter.getBounds().contains(e.getX(),e.getY())) {
			this.aQuitter.setFocus(true);
		} else {
			this.aQuitter.setFocus(false);
		}
		
		if (this.aCharger.getBounds().contains(e.getX(),e.getY())) {
			this.aCharger.setFocus(true);
		} else {
			this.aCharger.setFocus(false);
		}
	}

	public void actionMenu() {
		if (aJouer.getTarget()) {
			try {
				Thread.sleep(50);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			aJouer.setTarget(false);
            aWindow.remove(vTemp);
            aWindow.add(aGame);
			aGame.focusPanel();
            aWindow.repaint();
            aWindow.pack();
		}
				
           if (aCharger.getTarget()) {
			try {
				Thread.sleep(50);
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			aCharger.setTarget(false);
			aWindow.remove(vTemp);
			aWindow.add(aMenuLoadSave);
			aMenuLoadSave.focusPanel();
			aWindow.repaint();
			aWindow.pack();
		}

        if (aQuitter.getTarget()) {
        	try {
				Thread.sleep(50);
			} catch(InterruptedException ex) {
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
	 * Detection musique finie
	 */
	@Override
	public void update(LineEvent e) {
		LineEvent.Type type = e.getType();

		if (type == LineEvent.Type.START) {
			playOnce = true;
		} else if (type == LineEvent.Type.STOP) {
			audioClip.close();
			playOnce = false;
			playMusic();
		}
	}

	/**
	 * Joue la musique en fonction de la precedente
	 * (principe d'une playlist en boucle)
	 */
	public void playMusic() {
		try {
			URL urlMusic = null;
			AudioInputStream stream; 
			AudioFormat format;
			DataLine.Info info;
			
			if(!playOnce) { //Lance la musique une seule fois
				switch(choixMusic) {
				case MUSIC_1 :
					 urlMusic = this.getClass().getResource("/resources/Music/music_1.wav");
					break;
				case MUSIC_2: 
					urlMusic = this.getClass().getResource("/resources/Music/music_2.wav");
					break;
				default : 
					System.out.println("Erreur switch choixMusic");
					System.exit(0);
				}
					
				stream = AudioSystem.getAudioInputStream(urlMusic);
				format = stream.getFormat();
				info = new DataLine.Info(Clip.class, format);
				audioClip = (Clip) AudioSystem.getLine(info);
				audioClip.addLineListener(this);
				audioClip.open(stream);
				audioClip.start();

				if (choixMusic == MUSIC_1) {
					choixMusic = MUSIC_2;
				} else {
					choixMusic = MUSIC_1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Supprime le target de tous les boutons
	 */
	private void removeTarget() {
		aJouer.setTarget(false);
		aCharger.setTarget(false);
		aQuitter.setTarget(false);
	}

	/**
	 * Supprime le focus de tous les boutons
	 */
	private void removeFocus() {
		aJouer.setFocus(false);
		aCharger.setFocus(false);
		aQuitter.setFocus(false);
	}
}
