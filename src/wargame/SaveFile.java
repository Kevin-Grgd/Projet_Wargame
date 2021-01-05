package wargame;

import java.io.Serializable;
public class SaveFile implements Serializable{

    private static final long serialVersionUID = 3517160801267666132L;
    
    private Hexagone[][] aMap;
    private Heros[] armeeHeros;
    private Monstre[] armeeMonstre;
    private int heros_restant;
    private int monstre_restant;
    private String fileName;

    public SaveFile(int i) {
        this.setFileName(i);
        this.setaMap(null);
        this.setArmeeHeros(null);
        this.setArmeeMonstre(null);
        this.setHeros_restant(0);
        this.setMonstre_restant(0);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(int numero) {
        this.fileName = "carte" + numero + ".warsave";
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

    public Hexagone[][] getaMap() {
        return aMap;
    }

    public void setaMap(Hexagone[][] aMap) {
        this.aMap = aMap;
    }

    

}
