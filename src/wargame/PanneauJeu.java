package wargame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

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
    public transient int nb_tour = 1;
   

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
        // Ajout des boutons
        JButton button_fin_tour = Bouton("Fin du Tour");
        JButton repos = Bouton("Se reposer");
        
        JLabel label = new JLabel();
        label.setText("Tour "+nb_tour);
        
        // Action des boutons
        button_fin_tour.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                nb_tour++;
                label.setText("Tour "+nb_tour); // Fin du tour, on incrémente de 1
                joueIA(); // A l'IA de jouer
                repaintThread();
            }

        });

        repos.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                herosRepos();
            }

        });
        barreoutil.addSeparator();
        barreoutil.add(button_fin_tour);
        barreoutil.addSeparator(); // Sépare les deux boutons
        barreoutil.add(repos);
        barreoutil.addSeparator(new Dimension(400, 0));
        barreoutil.add(label);
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
                for (int x = 0; x < aCarte.aMap.length; x++) {
                    for (int y = 0; y < aCarte.aMap[0].length; y++) {
                        if (aCarte.aMap[x][y].isContain(e.getX(), e.getY())
                                && aCarte.aMap[x][y].getElement() instanceof Heros) {
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
                for (int x = 0; x < aCarte.aMap.length; x++) {
                    for (int y = 0; y < aCarte.aMap[0].length; y++) {

                        if (aCarte.aMap[x][y].isContain(e.getX(), e.getY()) && aPressedPosition != null
                                && (new Position(x, y).estVoisine(aPressedPosition)
                                        || aCarte.aMap[x][y].getElement() instanceof Monstre)) {
                            // aCarte.deplaceSoldat(new Position(x, y),(Soldat)
                            // aCarte.aMap[aPressedPosition.getX()][aPressedPosition.getY()].getElement());

                            aCarte.actionHeros(
                                    (Heros) aCarte.aMap[aPressedPosition.getX()][aPressedPosition.getY()].getElement(),
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
                for (int x = 0; x < aCarte.aMap.length; x++) {
                    for (int y = 0; y < aCarte.aMap[0].length; y++) {
                        aCarte.aMap[x][y].setTarget(false); // On clear la portée d'attaque
                        if (aCarte.aMap[x][y].isContain(e.getX(), e.getY())
                                && aCarte.aMap[x][y].getElement() instanceof Heros) {
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
                    int[] xPoly = { xCenter, xCenter, (int) (NB_PIX_CASE * 0.5) + xCenter, NB_PIX_CASE + xCenter,
                            NB_PIX_CASE + xCenter, (int) (NB_PIX_CASE * 0.5) + xCenter };
                    int[] yPoly = { (int) (NB_PIX_CASE * 0.75) + yCenter, (int) (NB_PIX_CASE * 0.25) + yCenter, yCenter,
                            (int) (NB_PIX_CASE * 0.25) + yCenter, (int) (NB_PIX_CASE * 0.75) + yCenter,
                            yCenter + NB_PIX_CASE };
                    aDraggedUnit = new Polygon(xPoly, yPoly, xPoly.length);
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

        this.aCarte = new Carte(LARGEUR_CARTE, HAUTEUR_CARTE, NB_PIX_CASE, NB_PIX_CASE);
        ajout_obstacle(this.aCarte);
        ajout_soldat(this.aCarte);

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
        return this.aCarte;
    }

    /**
     * Défini la carte du jeu
     * @param pCarte Carte à définir
     */
    public void setCarte(Carte pCarte){
        this.aCarte = pCarte;
    }

    /**
     * Ajout des obstacles sur la carte
     * @param pCarte Carte où ajouter les obstacles
     */
    public void ajout_obstacle(Carte pCarte) {
        int x;
        int y;
        Obstacle vObstacle;
        Position vPos;
        for (int i = 0; i < NB_OBSTACLES; i++) {
            do {
                x = new Random().nextInt(pCarte.aMap.length);
                y = new Random().nextInt(pCarte.aMap[0].length);
                vPos = new Position(x, y);
            } while (pCarte.aMap[x][y].getElement() != null);
            vObstacle = new Obstacle(vPos, pCarte.aMap[x][y].getHexagone());
            pCarte.aMap[x][y].setElement(vObstacle);
        }
    }

    /**
     * Ajout des soldats sur la carte
     * @param pCarte Carte où ajouter les soldats
     */
    public void ajout_soldat(Carte pCarte) {
        int x;
        int y;
        Position vPos;
        Heros vHeroes;
        Monstre vMonstre;
        // Ajout des héros sur le côté gauche de la map
        for (int i = 0; i < NB_HEROS; i++) {
            do {
                x = new Random().nextInt(LARGEUR_CARTE / 2); // Côté gauche de la carte
                y = new Random().nextInt(HAUTEUR_CARTE); // De bas en haut
                vPos = new Position(x, y);
            } while (pCarte.aMap[x][y].getElement() != null); // Tant que il y a un obstacle on prend une autre position

            vHeroes = new Heros(vPos); // Création du héros
            vHeroes.setPoints(new Random().nextInt(vHeroes.type.getPoints()));
            pCarte.aMap[x][y].setElement(vHeroes); // Ajout du héros dans la map
            pCarte.appendHeros(vHeroes);
            pCarte.herosVision(true);
        }

        // Ajout des monstres sur le côté droit de la map
        for (int j = 0; j < NB_MONSTRES; j++) {
            do {
                x = new Random().nextInt(LARGEUR_CARTE); // Côté droit de la carte
                y = new Random().nextInt(HAUTEUR_CARTE); // De bas en haut
                vPos = new Position(x, y);
            } while (pCarte.aMap[x][y].getElement() != null || x < LARGEUR_CARTE / 2); // Tant que il y a un obstacle on
                                                                                       // prend une autre position

            vMonstre = new Monstre(vPos); // Création du monstre
            vMonstre.setPoints(new Random().nextInt(vMonstre.type.getPoints()));
            pCarte.aMap[x][y].setElement(vMonstre); // Ajout du monstre dans la map
            pCarte.appendMonstre(vMonstre);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);

        for (int x = 0; x < this.aCarte.aMap.length; x++) {
            for (int y = 0; y < this.aCarte.aMap[0].length; y++) {
                this.aCarte.aMap[x][y].seDessiner(g);
            }
        }

        if (this.aDraggedUnit != null) {
            new Hexagone(this.aCarte.aMap[this.aPressedPosition.getX()][this.aPressedPosition.getY()].getElement(),true, this.aDraggedUnit, null).seDessiner(g);
        }

        if (this.aInfo != null) {
            this.aInfo.paintComponent(g);
        }

        aCarte.seDessine(g);

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
        for (int x = 0; x < this.aCarte.aMap.length; x++) {
            for (int y = 0; y < this.aCarte.aMap[0].length; y++) {
                if (this.aCarte.aMap[x][y].isContain(e.getX(), e.getY())) {
                    this.aCarte.aMap[x][y].setFocus(true);
                    if (this.aCarte.aMap[x][y].getElement() instanceof Soldat && this.aCarte.aMap[x][y].getVisible()) {
                        this.aInfo = new InfoBulle((Soldat) this.aCarte.aMap[x][y].getElement(), e.getX(), e.getY());
                    } else {
                        this.aInfo = null;
                    }
                } else {
                    this.aCarte.aMap[x][y].setFocus(false);
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
            Position vPos = this.aPressedTarget;
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
            aCarte.aMap[vPos.getX() - 1][vPos.getY()].setTarget(pSet);
            targetOne(new Position(vPos.getX() - 1, vPos.getY()), pSet, pVision - 1);
        }
        if (vPos.getX() + 1 < LARGEUR_CARTE) {
            aCarte.aMap[vPos.getX() + 1][vPos.getY()].setTarget(pSet);
            targetOne(new Position(vPos.getX() + 1, vPos.getY()), pSet, pVision - 1);

        }
        if (vPos.getY() - 1 >= 0) {
            aCarte.aMap[vPos.getX()][vPos.getY() - 1].setTarget(pSet);
            targetOne(new Position(vPos.getX(), vPos.getY() - 1), pSet, pVision - 1);

        }
        if (vPos.getY() + 1 < HAUTEUR_CARTE) {
            aCarte.aMap[vPos.getX()][vPos.getY() + 1].setTarget(pSet);
            targetOne(new Position(vPos.getX(), vPos.getY() + 1), pSet, pVision - 1);

        }

        if (vPos.getY() % 2 != 0) { // Ligne impaire

            if (vPos.getX() + 1 < LARGEUR_CARTE && vPos.getY() + 1 < HAUTEUR_CARTE) {
                aCarte.aMap[vPos.getX() + 1][vPos.getY() + 1].setTarget(pSet);
                targetOne(new Position(vPos.getX() + 1, vPos.getY() + 1), pSet, pVision - 1);

            }
            if (vPos.getX() + 1 < LARGEUR_CARTE && vPos.getY() - 1 >= 0) {
                aCarte.aMap[vPos.getX() + 1][vPos.getY() - 1].setTarget(pSet);
                targetOne(new Position(vPos.getX() + 1, vPos.getY() - 1), pSet, pVision - 1);

            }
        } else { // Ligne paire
            if (vPos.getX() - 1 >= 0 && vPos.getY() + 1 < HAUTEUR_CARTE) {
                aCarte.aMap[vPos.getX() - 1][vPos.getY() + 1].setTarget(pSet);
                targetOne(new Position(vPos.getX() - 1, vPos.getY() + 1), pSet, pVision - 1);

            }
            if (vPos.getX() - 1 >= 0 && vPos.getY() - 1 >= 0) {
                aCarte.aMap[vPos.getX() - 1][vPos.getY() - 1].setTarget(pSet);
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
                if (vArmee[i].getPosition().getX() == vPos.getX() && vArmee[i].getPosition().getY() == vPos.getY()
                        && vArmee[i] != null && !(vArmee[i].getJoue())) {
                    vArmee[i].seRepose();
                    vArmee[i].setJoue(true);
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
                } else { // Si on a trouvé un héros non loin on l'affronte
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
}
