package m2info.ter.decofo.classes;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
public class Effectif implements Serializable {

    private static final long serialVersionUID = 1L;


    @Basic()
    @Column(name = "eff_site1")
    private int site1 = 0;
    @Basic()
    @Column(name = "ng_gs_cm_site1")
    private int nbGroupesCMSite1 = 0;
    @Basic()
    @Column(name = "ng_gs_td_site1")
    private int nbGroupesTDSite1 = 0;
    @Basic()
    @Column(name = "ng_gs_tp_site1")
    private int nbGroupesTPSite1 = 0;

    @Basic()
    @Column(name = "eff_site2")
    private int site2 = 0;
    @Basic()
    @Column(name = "ng_gs_cm_site2")
    private int nbGroupesCMSite2 = 0;
    @Basic()
    @Column(name = "ng_gs_td_site2")
    private int nbGroupesTDSite2 = 0;
    @Basic()
    @Column(name = "ng_gs_tp_site2")
    private int nbGroupesTPSite2 = 0;

    @Basic()
    @Column(name = "eff_site3")
    private int site3 = 0;
    @Basic()
    @Column(name = "ng_gs_cm_site3")
    private int nbGroupesCMSite3 = 0;
    @Basic()
    @Column(name = "ng_gs_td_site3")
    private int nbGroupesTDSite3 = 0;
    @Basic()
    @Column(name = "ng_gs_tp_site3")
    private int nbGroupesTPSite3 = 0;

    @Basic()
    @Column(name = "eff_site4")
    private int site4 = 0;
    @Basic()
    @Column(name = "ng_gs_cm_site4")
    private int nbGroupesCMSite4 = 0;
    @Basic()
    @Column(name = "ng_gs_td_site4")
    private int nbGroupesTDSite4 = 0;
    @Basic()
    @Column(name = "ng_gs_tp_site4")
    private int nbGroupesTPSite4 = 0;

    public Effectif(){

    }

    public Effectif(int site1, int site2, int site3, int site4) {
        this.site1 = site1;
        this.site2 = site2;
        this.site3 = site3;
        this.site4 = site4;
    }

    public int getSite1() {
        return site1;
    }

    public void setSite1(int site1) {
        this.site1 = site1;
    }

    public int getSite2() {
        return site2;
    }

    public void setSite2(int site2) {
        this.site2 = site2;
    }

    public int getSite3() {
        return site3;
    }

    public void setSite3(int site3) {
        this.site3 = site3;
    }

    public int getSite4() {
        return site4;
    }

    public void setSite4(int site4) {
        this.site4 = site4;
    }

    // ne pas mettre dans constructeur


    public int getNbGroupesCMSite1() {
        return nbGroupesCMSite1;
    }

    public void setNbGroupesCMSite1(int nbGroupesCMSite1) {
        this.nbGroupesCMSite1 = nbGroupesCMSite1;
    }

    public int getNbGroupesTDSite1() {
        return nbGroupesTDSite1;
    }

    public void setNbGroupesTDSite1(int nbGroupesTDSite1) {
        this.nbGroupesTDSite1 = nbGroupesTDSite1;
    }

    public int getNbGroupesTPSite1() {
        return nbGroupesTPSite1;
    }

    public void setNbGroupesTPSite1(int nbGroupesTPSite1) {
        this.nbGroupesTPSite1 = nbGroupesTPSite1;
    }

    public int getNbGroupesCMSite2() {
        return nbGroupesCMSite2;
    }

    public void setNbGroupesCMSite2(int nbGroupesCMSite2) {
        this.nbGroupesCMSite2 = nbGroupesCMSite2;
    }

    public int getNbGroupesTDSite2() {
        return nbGroupesTDSite2;
    }

    public void setNbGroupesTDSite2(int nbGroupesTDSite2) {
        this.nbGroupesTDSite2 = nbGroupesTDSite2;
    }

    public int getNbGroupesTPSite2() {
        return nbGroupesTPSite2;
    }

    public void setNbGroupesTPSite2(int nbGroupesTPSite2) {
        this.nbGroupesTPSite2 = nbGroupesTPSite2;
    }

    public int getNbGroupesCMSite3() {
        return nbGroupesCMSite3;
    }

    public void setNbGroupesCMSite3(int nbGroupesCMSite3) {
        this.nbGroupesCMSite3 = nbGroupesCMSite3;
    }

    public int getNbGroupesTDSite3() {
        return nbGroupesTDSite3;
    }

    public void setNbGroupesTDSite3(int nbGroupesTDSite3) {
        this.nbGroupesTDSite3 = nbGroupesTDSite3;
    }

    public int getNbGroupesTPSite3() {
        return nbGroupesTPSite3;
    }

    public void setNbGroupesTPSite3(int nbGroupesTPSite3) {
        this.nbGroupesTPSite3 = nbGroupesTPSite3;
    }

    public int getNbGroupesCMSite4() {
        return nbGroupesCMSite4;
    }

    public void setNbGroupesCMSite4(int nbGroupesCMSite4) {
        this.nbGroupesCMSite4 = nbGroupesCMSite4;
    }

    public int getNbGroupesTDSite4() {
        return nbGroupesTDSite4;
    }

    public void setNbGroupesTDSite4(int nbGroupesTDSite4) {
        this.nbGroupesTDSite4 = nbGroupesTDSite4;
    }

    public int getNbGroupesTPSite4() {
        return nbGroupesTPSite4;
    }

    public void setNbGroupesTPSite4(int nbGroupesTPSite4) {
        this.nbGroupesTPSite4 = nbGroupesTPSite4;
    }
}
