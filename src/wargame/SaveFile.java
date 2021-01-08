package wargame;

import java.io.Serializable;
public class SaveFile implements Serializable{

    private static final long serialVersionUID = 3517160801267666132L;
    private Position[][] map;
    private Heros[] armeeHeros;
    private Monstre[] armeeMonstre;
    private int heros_restant;
    private int monstre_restant;
    private String fileName;
    private int nbTours;

    public SaveFile(int i, Carte carte) {
        setFileName(i);
        setCarte(carte.getCarte());
        setArmeeHeros(carte.getArmeeHeros());
        setArmeeMonstre(carte.getArmeeMonstre());
        setHeros_restant(carte.getHerosRestant());
        setMonstre_restant(carte.getMonstreRestant());
        setTours(carte.getTours());
    }
    
    public int getTours() {
    	return nbTours;
    }
    
    public void setTours(int nbTours) {
    	this.nbTours = nbTours;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(int numero) {
        this.fileName = "carte" + numero + ".warsave";
    }
    
    public Position[][] getCarte(){
    	return map;
    }
    
    public void setCarte(Position[][] map) {
    	this.map = map;
    }
    
    public int getMonstre_restant() {
        return monstre_restant;
    }

    public void setMonstre_restant(int monstre_restant) {
        this.monstre_restant = monstre_restant;
    }

    public int getHeros_restant() {
        return heros_restant;
    }

    public void setHeros_restant(int heros_restant) {
        this.heros_restant = heros_restant;
    }

    public Monstre[] getArmeeMonstre() {
        return armeeMonstre;
    }

    public void setArmeeMonstre(Monstre[] armeeMonstre) {
        this.armeeMonstre = armeeMonstre;
    }

    public Heros[] getArmeeHeros() {
        return armeeHeros;
    }

    public void setArmeeHeros(Heros[] armeeHeros) {
        this.armeeHeros = armeeHeros;
    }

}