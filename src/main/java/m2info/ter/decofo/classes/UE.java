package m2info.ter.decofo.classes;

import java.io.Serializable;

public class UE implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private String code;

    private String intitule;

    private double cout;

    private double nombreHeureCM;

    private double nombreHeureTD;

    private double nombreHeureTP;

    // calcul intermediaire, données non obligatoires
    private int nombreGroupesCM;

    private int nombreGroupesTD;

    private int nombreGroupesTP;

    int effectifTotal;

    public UE() {

    }

    public void incrementerEffectif(int effectif) {
        this.effectifTotal += 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public double getCout() {
        return cout;
    }

    public void setCout(double cout) {
        this.cout = cout;
    }

    public double getNombreHeureCM() {
        return nombreHeureCM;
    }

    public void setNombreHeureCM(double nombreHeureCM) {
        this.nombreHeureCM = nombreHeureCM;
    }

    public double getNombreHeureTD() {
        return nombreHeureTD;
    }

    public void setNombreHeureTD(double nombreHeureTD) {
        this.nombreHeureTD = nombreHeureTD;
    }

    public double getNombreHeureTP() {
        return nombreHeureTP;
    }

    public void setNombreHeureTP(double nombreHeureTP) {
        this.nombreHeureTP = nombreHeureTP;
    }

    public int getNombreGroupesCM() {
        return nombreGroupesCM;
    }

    public void setNombreGroupesCM(int nombreGroupesCM) {
        this.nombreGroupesCM = nombreGroupesCM;
    }

    public int getNombreGroupesTD() {
        return nombreGroupesTD;
    }

    public void setNombreGroupesTD(int nombreGroupesTD) {
        this.nombreGroupesTD = nombreGroupesTD;
    }

    public int getNombreGroupesTP() {
        return nombreGroupesTP;
    }

    public void setNombreGroupesTP(int nombreGroupesTP) {
        this.nombreGroupesTP = nombreGroupesTP;
    }

    public int getEffectifTotal() {
        return effectifTotal;
    }

    public void setEffectifTotal(int effectifTotal) {
        this.effectifTotal = effectifTotal;
    }
}
