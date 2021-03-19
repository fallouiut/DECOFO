package m2info.ter.decofo.classes;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Effectif implements Serializable {

    private static final long serialVersionUID = 1L;

    // methode addition d'effectifs (Effectif eff)
    // methode total (tout site confondus)

    @Basic()
    @Column(name = "eff_site1")
    private int site1 = 0;

    @Basic()
    @Column(name = "eff_site2")
    private int site2 = 0;

    @Basic()
    @Column(name = "eff_site3")
    private int site3 = 0;

    @Basic()
    @Column(name = "eff_site4")
    private int site4 = 0;

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


    public int getEffectifTotal() {
        return site1 + site2 + site3 + site4;
    }

    public void additionParSite(Effectif effectif) {
        site1 += effectif.getSite1();
        site2 += effectif.getSite2();
        site3 += effectif.getSite3();
        site4 += effectif.getSite4();
    }

    @Override
    public String toString() {
        return "Effectif{" +
                "site1=" + site1 +
                ", site2=" + site2 +
                ", site3=" + site3 +
                ", site4=" + site4 +
                '}';
    }
}
