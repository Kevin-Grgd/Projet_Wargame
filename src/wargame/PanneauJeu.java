package wargame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
//import java.awt.Graphics2D;
import java.awt.Polygon;
//import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import javax.swing.SwingWorker;

public class PanneauJeu extends JPanel implements IConfig {

    private static final long serialVersionUID = -5368279323161208181L;
    private transient Carte aCarte;
    private transient InfoBulle aInfo;
    private transient Position aPressedPosition;
    private transient Position aPressedTarget;
    private transient Polygon aDraggedUnit;
   

    /**
     * Création de boutons
     * @param s Chaine à afficher sur le bouton
     * @return Un bouton
     */
    public static JButton Bouton(String s) {
        return new JButton(s);
    }

    /**
     * 
     * @return La barre d'outil
     */
    public JToolBar toolBar() {

        JToolBar barreoutil = new JToolBar();
        barreoutil.setPreferredSize(new Dimension(LARGEUR_BARRE_OUTIL, HAUTEUR_BARRE_OUTIL));
        barreoutil.setBackground(new Color(128, 79, 54, 250));
        setLayout(new BorderLayout());
        // Ajout des boutons
        JButton button_fin_tour = Bouton("Fin du Tour");
        JButton repos = Bouton("Se reposer");
        JButton saveButton = Bouton("Sauvegarder");
        JButton restartButton = Bouton("Restaurer");
        JButton loadButton = Bouton("Charger");//à enlever
        
        //Couleur de fond des boutons
        button_fin_tour.setBackground(BOUTON_NORMAL);
        repos.setBackground(BOUTON_NORMAL);
        saveButton.setBackground(BOUTON_NORMAL);
        restartButton.setBackground(BOUTON_NORMAL);
        loadButton.setBackground(BOUTON_NORMAL);
        
        //Couleur du texte des boutons
        button_fin_tour.setForeground(Color.white);
        repos.setForeground(Color.white);
        saveButton.setForeground(Color.white);
        restartButton.setForeground(Color.white);
        loadButton.setForeground(Color.white);
        
        
        
        JLabel tour = new JLabel();
        tour.setForeground(Color.white);
        tour.setText("Tour " + aCarte.getTours());
        
        // Action des boutons        
        button_fin_tour.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                aCarte.setTours(aCarte.getTours() + 1);
                tour.setText("Tour " + aCarte.getTours()); // Fin du tour, on incrémente de 1
                joueIA(); // A l'IA de jouer
            }

        });

        repos.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                herosRepos();
            }

        });
        
        saveButton.addActionListener(new ActionListener() {
        	public void actionPerformed(final ActionEvent e) {
        		aCarte.Sauvegarde();
        	}
        });
        
        restartButton.addActionListener(new ActionListener() { //à enlever
        	public void actionPerformed(final ActionEvent e) {
        		aCarte = new Carte();
        		repaintThread();
        	}
        });
        
        loadButton.addActionListener(new ActionListener() { //à enlever
        	public void actionPerformed(final ActionEvent e) {
        		aCarte.Recharger();
        		repaintThread();
        	}
        });
        
        
        barreoutil.addSeparator();
        barreoutil.add(button_fin_tour);
        barreoutil.addSeparator(); // Sépare les deux boutons
        barreoutil.add(repos);
        barreoutil.addSeparator();
        barreoutil.addSeparator(new Dimension(450, 0));
        barreoutil.add(tour);
        barreoutil.add(Box.createHorizontalGlue());
        barreoutil.add(saveButton);
        barreoutil.addSeparator();
        barreoutil.add(loadButton);
        barreoutil.addSeparator();
        barreoutil.add(restartButton);
        barreoutil.addSeparator();
        
        barreoutil.setFloatable(false); // Rend possible le déplacement de la barre d'outils
        return barreoutil;
    }

    /**
     * 
     * @return Le plateau de jeu
     */
    public JPanel plateau() {
        JPanel plat = new JPanel();
        plat.setLocation(0, HAUTEUR_BARRE_OUTIL);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (int x = 0; x < LARGEUR_CARTE; x++) {
                    for (int y = 0; y < HAUTEUR_CARTE; y++) {
                        if (aCarte.getHexagones()[x][y].isContain(e.getX(), e.getY())
                                && aCarte.getCarte()[x][y].getElement() instanceof Heros) {
                        	if(!((Heros) aCarte.getCarte()[x][y].getElement()).getJoue())
                        		aPressedPosition = new Position(x, y);
                        }
                    }
                }

                repaintThread();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                repaintThread();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                for (int x = 0; x < LARGEUR_CARTE; x++) {
                    for (int y = 0; y < HAUTEUR_CARTE; y++) {
                        if (aCarte.getHexagones()[x][y].isContain(e.getX(), e.getY()) && aPressedPosition != null
                                && (new Position(x, y).estVoisine(aPressedPosition)
                                        || aCarte.getCarte()[x][y].getElement() instanceof Monstre)) {
                            aCarte.actionHeros(
                                    (Heros) aCarte.getCarte()[aPressedPosition.getX()][aPressedPosition.getY()].getElement(),
                                    new Position(x, y));
                        }
                    }
                }
                aPressedPosition = null;
                repaintThread();
                aDraggedUnit = null;
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                aPressedTarget = null;
                for (int x = 0; x < LARGEUR_CARTE ; x++) {
                    for (int y = 0; y < HAUTEUR_CARTE ; y++) {
                        aCarte.getCarte()[x][y].setTarget(false); // On clear la portée d'attaque
                        if (aCarte.getHexagones()[x][y].isContain(e.getX(), e.getY())
                                && aCarte.getCarte()[x][y].getElement() instanceof Heros) {
                            aPressedTarget = new Position(x, y);
                        }
                    }
                }
                herosTarget(true);
                repaintThread();
            }

        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                markHasHover(e);
                repaintThread();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                herosTarget(false);
                repaintThread();
                markHasHover(e);
                int xCenter = e.getX();
                int yCenter = e.getY();
                if (aPressedPosition != null) {
                	aDraggedUnit = new Polygon();
                	for(int i = 0 ; i < 6 ; i++) {
                		int x = (int) (xCenter + (NB_PIX_CASE/2+3) * Math.sin(i*2*Math.PI/6));
            			int y = (int) (yCenter + (NB_PIX_CASE/2) * Math.cos(i*2*Math.PI/6));
            			aDraggedUnit.addPoint(x, y);
                	}
                }
                repaintThread();
            }
        });

        return plat;
    }

    /**
     * Constructeur PanneauJeu
     */
    public PanneauJeu() {
    	
    	if(aCarte == null) {
    		aCarte = new Carte();
    	}
        

        setLayout(new BorderLayout());
        JToolBar outil = toolBar();
        this.add(outil, BorderLayout.NORTH);
        this.add(plateau(), BorderLayout.SOUTH);
        this.addKeyListener(new Clavier());
       
        repaintThread();
        /*
         * addKeyListener(new KeyAdapter(){
         * 
         * @Override public void keyTyped(KeyEvent e) { // TODO Auto-generated method
         * stub int key = e.getKeyChar(); System.out.println(key); if (key == 'a') {
         * System.out.println("Test"); }
         * 
         * }
         * 
         * @Override public void keyPressed(KeyEvent e) { // TODO Auto-generated method
         * stub int key = e.getKeyCode(); if (key == KeyEvent.VK_E) {
         * System.out.println("Réussite"); }
         * 
         * }
         * 
         * @Override public void keyReleased(KeyEvent e) { // TODO Auto-generated method
         * stub int key = e.getKeyCode(); System.out.print(key); if (key ==
         * KeyEvent.VK_B) { System.out.println("Zoulou"); }
         * 
         * } });
         */

    }

    /**
     * 
     * @return La carte du jeu
     */
    public Carte getCarte(){
        return aCarte;
    }

    /**
     * Défini la carte du jeu
     * @param pCarte Carte à définir
     */
    public void setCarte(Carte pCarte){
        aCarte = pCarte;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Antialazing mais drop fps
        //Graphics2D g2d = (Graphics2D) g;
        //g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g.setColor(Color.WHITE);

        aCarte.toutDessiner(g);

        if (aDraggedUnit != null) {
            new Hexagone(aDraggedUnit ,aPressedPosition.getX() , aPressedPosition.getY()).seDessiner(g, aCarte.getCarte()[aPressedPosition.getX()][aPressedPosition.getY()]);
        }

        if (aInfo != null) {
            aInfo.paintComponent(g);
        }

        

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(LARGEUR_PLATEAU, HAUTEUR_PLATEAU);
    }

    /**
     * Affiche l'info bulle et grise la case en cas de survol
     * @param e MouseEvent
     */
    private void markHasHover(MouseEvent e) {
        for (int x = 0; x < LARGEUR_CARTE; x++) {
            for (int y = 0; y < HAUTEUR_CARTE; y++) {
                if (aCarte.getHexagones()[x][y].isContain(e.getX(), e.getY())) {
                    aCarte.getCarte()[x][y].setFocus(true);
                    if (aCarte.getCarte()[x][y].getElement() instanceof Soldat && aCarte.getCarte()[x][y].getVisible()) {
                        aInfo = new InfoBulle((Soldat) aCarte.getCarte()[x][y].getElement(), e.getX(), e.getY());
                    } else {
                        aInfo = null;
                    }
                } else {
                    aCarte.getCarte()[x][y].setFocus(false);
                }
            }
        }
    }

    /**
     * Sélectionne un héros
     * @param pSet Choisi si on le sélectionne ou non
     */
    public void herosTarget(boolean pSet) {

        if (aPressedTarget != null) {
            Position vPos = aPressedTarget;
            // On trouve le héros à cette position
            Heros[] vArmee = aCarte.getArmeeHeros();

            int vision = 0;
            for (int i = 0; i < NB_HEROS; i++) {
                if(vArmee[i] != null){
                    if (vArmee[i].getPosition().getX() == vPos.getX() && vArmee[i].getPosition().getY() == vPos.getY()){
                        vision = vArmee[i].getPortee();
                        vArmee[i].setSelected(pSet);
                    }
                }   
            }
            targetOne(vPos, pSet, vision);
        }
    }

    /**
     * Fonction subsidiaire récursive de herosTarget
     * @param vPos Case sélectionné
     * @param pSet Si on sélectionne ou non
     * @param pVision Vision du héros
     */
    public void targetOne(Position vPos, boolean pSet, int pVision) {
        if (pVision == 0) // Fin de la récursivité
            return;

        if (vPos.getX() - 1 >= 0) {
            aCarte.getCarte()[vPos.getX() - 1][vPos.getY()].setTarget(pSet);
            targetOne(new Position(vPos.getX() - 1, vPos.getY()), pSet, pVision - 1);
        }
        if (vPos.getX() + 1 < LARGEUR_CARTE) {
            aCarte.getCarte()[vPos.getX() + 1][vPos.getY()].setTarget(pSet);
            targetOne(new Position(vPos.getX() + 1, vPos.getY()), pSet, pVision - 1);

        }
        if (vPos.getY() - 1 >= 0) {
            aCarte.getCarte()[vPos.getX()][vPos.getY() - 1].setTarget(pSet);
            targetOne(new Position(vPos.getX(), vPos.getY() - 1), pSet, pVision - 1);

        }
        if (vPos.getY() + 1 < HAUTEUR_CARTE) {
            aCarte.getCarte()[vPos.getX()][vPos.getY() + 1].setTarget(pSet);
            targetOne(new Position(vPos.getX(), vPos.getY() + 1), pSet, pVision - 1);

        }

        if (vPos.getY() % 2 != 0) { // Ligne impaire

            if (vPos.getX() + 1 < LARGEUR_CARTE && vPos.getY() + 1 < HAUTEUR_CARTE) {
                aCarte.getCarte()[vPos.getX() + 1][vPos.getY() + 1].setTarget(pSet);
                targetOne(new Position(vPos.getX() + 1, vPos.getY() + 1), pSet, pVision - 1);

            }
            if (vPos.getX() + 1 < LARGEUR_CARTE && vPos.getY() - 1 >= 0) {
                aCarte.getCarte()[vPos.getX() + 1][vPos.getY() - 1].setTarget(pSet);
                targetOne(new Position(vPos.getX() + 1, vPos.getY() - 1), pSet, pVision - 1);

            }
        } else { // Ligne paire
            if (vPos.getX() - 1 >= 0 && vPos.getY() + 1 < HAUTEUR_CARTE) {
                aCarte.getCarte()[vPos.getX() - 1][vPos.getY() + 1].setTarget(pSet);
                targetOne(new Position(vPos.getX() - 1, vPos.getY() + 1), pSet, pVision - 1);

            }
            if (vPos.getX() - 1 >= 0 && vPos.getY() - 1 >= 0) {
                aCarte.getCarte()[vPos.getX() - 1][vPos.getY() - 1].setTarget(pSet);
                targetOne(new Position(vPos.getX() - 1, vPos.getY() - 1), pSet, pVision - 1);

            }
        }
    }

    /**
     * Repose le héros s'il est sélectionné
     */
    public void herosRepos() {
        Position vPos = aPressedTarget;
        if (vPos != null) {
            Heros[] vArmee = aCarte.getArmeeHeros();
            // Récupération du soldat souhaité
            for (int i = 0; i < NB_HEROS; i++) {
                if (vArmee[i] != null){
                    if (vArmee[i].getPosition().getX() == vPos.getX() && vArmee[i].getPosition().getY() == vPos.getY()
                        && vArmee[i] != null && !(vArmee[i].getJoue())) {
                        vArmee[i].seRepose();
                        vArmee[i].setJoue(true);
                    }
                }
            }
        }
    }

    /**
     * Joue l'IA
     */
    public void joueIA() {
        Heros[] listeHeros = aCarte.getArmeeHeros();
        Monstre[] listeMonstre = aCarte.getArmeeMonstre();
        Heros vHerosTarget;
       
        // Les héros qui n'ont pas joué ce repose
        for (int i = 0; i < NB_HEROS; i++) {
            if (listeHeros[i] != null){
                if (!(listeHeros[i].getJoue())) {
                    listeHeros[i].seRepose();
                }
            }
        }

        // Au tour des monstres de jouer
        for (int j = 0; j < NB_MONSTRES; j++) {
            if (listeMonstre[j] != null) {
                if (listeMonstre[j].getTir() <= MINIMAL_TIR_REQUIS){ // Si les dégâts de tirs sont jugés trop minime on préférera attaquer de près
                    vHerosTarget = aCarte.trouveHeros(listeMonstre[j].getPosition());
                }
                else{
                    vHerosTarget = aCarte.trouveHerosTir(listeMonstre[j]);
                }
                if (vHerosTarget == null) { // Si on ne trouve pas de héros dans les parages on se déplace ou on se repose
                    if (listeMonstre[j].getPoints() < listeMonstre[j].getTypeSoldat().getPoints() / 5){ // Si il a 1/5 de ses points de vies de base il se repose
                        listeMonstre[j].seRepose();
                    }
                    else{ // Sinon il se déplace
                        Position vNextPos = aCarte.trouvePositionVide(listeMonstre[j].getPosition());
                        aCarte.deplaceSoldat(vNextPos, listeMonstre[j]);
                    }
                } 
                else { // Si on a trouvé un héros non loin on l'affronte
                    listeMonstre[j].combat(vHerosTarget);
                    if (vHerosTarget.getPoints() <= 0){
                        aCarte.mort(vHerosTarget);
                        aCarte.setHerosRestant(aCarte.getHerosRestant() - 1);
                    }
                }
            }
        }

        // On réautorise le joueur à jouer
        for (int i = 0; i < NB_HEROS; i++) {
            if (listeHeros[i] != null){
                listeHeros[i].setJoue(false);
            }
        }
    }

    /**
     * Thread pour repaint plus vite
     */
    public void repaintThread() {
        SwingWorker<Integer, Void> worker = new SwingWorker<Integer, Void>() {
            @Override
            public Integer doInBackground() {
                repaint();
                return 0;
            }
        };
        worker.execute();
    }
    
    public void focusPanel(){
        this.setFocusable(true);
        this.requestFocusInWindow();
    }
}
