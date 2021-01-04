package wargame;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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


public class Accueil extends JPanel implements IConfig, LineListener {
	
	private static final long serialVersionUID = 1L;
	private transient Image accueilBackground;
	private transient Image logoJeu;
	private transient int lancer = 0;
	private transient PanneauJeu aGame;
	private transient JFrame aWindow;
	private transient BoutonMenu aJouer = null;
	private transient BoutonMenu aQuitter = null;
	private transient BoutonMenu aCharger = null;
	private transient int choixMusic;
	private transient boolean playOnce = false;
	private transient Clip audioClip;
	
	public JPanel menu() {
		
		JPanel menu = new JPanel();
		
		menu.setOpaque(false);
		menu.setPreferredSize(new Dimension(LARGEUR_FENETRE, HAUTEUR_FENETRE));
		menu.setLayout(null);
		Accueil vTemp = this;
		this.addMouseListener( new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	//System.out.println("Coordoonee :"+e.getPoint());
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
            
            @Override
            public void mouseReleased(MouseEvent e) {
            	if (aJouer.getTarget() ) {
            		try {
						Thread.sleep(50);
					} catch(InterruptedException ex) {
					    Thread.currentThread().interrupt();
					}
            		aWindow.remove(vTemp);
            		aWindow.add(aGame);
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
				}
            	if (aQuitter.getTarget()) {
            		try {
						Thread.sleep(50);
					} catch(InterruptedException ex) {
					    Thread.currentThread().interrupt();
					}
            		System.exit(0);
            	}
            }
		});
		
		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				//System.out.println("Coordoonee :"+e.getPoint());
				boutonHover(e);
				repaint();
			}
		});

		return menu;		
	}

	
	public Accueil(PanneauJeu pGame,JFrame pWindow) {
		this.aGame = pGame;
		this.aWindow = pWindow;
		/*
		aJouer = new BoutonMenu(LARGEUR_FENETRE/2, 300, "Jouer");
		aCharger = new BoutonMenu(LARGEUR_FENETRE/2, 375, "Charger une partie");
		aQuitter = new BoutonMenu(LARGEUR_FENETRE/2, 450, "Quitter");
		*/

		aJouer = new BoutonMenu(375, 500, "Jouer");
		aCharger = new BoutonMenu(375, 575, "Charger une partie");
		aQuitter = new BoutonMenu(375, 650, "Quitter");
		
		choixMusic = MUSIC_1;

		try {
			//System.out.println("Try image");
			URL url = this.getClass().getResource("/resources/accueil_background_2.jpg");
			URL urlLogo = this.getClass().getResource("/resources/wargame_logo.png");
			accueilBackground = ImageIO.read(url);
			logoJeu = ImageIO.read(urlLogo);			
			accueilBackground = accueilBackground.getScaledInstance(LARGEUR_FENETRE, HAUTEUR_FENETRE, Image.SCALE_SMOOTH);
			
		} catch (IOException e) {
			e.printStackTrace();			
		}
		
		setLayout(new BorderLayout());
		this.add(menu());
		playMusic();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		int logoWidth = logoJeu.getWidth(null);
		super.paintComponent(g);
		g.drawImage(accueilBackground,0,0,this);
		g.drawImage(logoJeu, ((LARGEUR_FENETRE/2)-(logoWidth/2)), 25,this);

		aJouer.paintComponent(g);
		aQuitter.paintComponent(g);
		aCharger.paintComponent(g);
	}

	public int getLancer(){
		return this.lancer;
	}

	public void setLancer(int pLancer){
		this.lancer = pLancer;
	}
	
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

	public void playMusic() {
		try {
			//System.out.println("Try music");
			URL urlMusic = null;
			AudioInputStream stream; 
			AudioFormat format;
			DataLine.Info info;
			Clip audioClip;
			
			if(!playOnce) {
				//System.out.println("Choix music :"+choixMusic);
				switch(choixMusic) {
				case MUSIC_1 : urlMusic = this.getClass().getResource("/resources/Music/music_1.wav");
				//System.out.println("Music_1 played");
				break;
				case MUSIC_2: urlMusic = this.getClass().getResource("/resources/Music/music_2.wav");
				//System.out.println("Music_2 played");
				break;
				default : System.out.println("Erreur switch choixMusic");
				System.exit(0);
				}
					
				stream = AudioSystem.getAudioInputStream(urlMusic);
				format = stream.getFormat();
				info = new DataLine.Info(Clip.class, format);
				audioClip = (Clip)AudioSystem.getLine(info);
				audioClip.addLineListener(this);
				audioClip.open(stream);
				audioClip.start();
				if (choixMusic == MUSIC_1) {
					choixMusic = MUSIC_2;
				} else {
					choixMusic = MUSIC_1;
				}
			}
			//System.out.println("Play Once :"+playOnce);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

}
