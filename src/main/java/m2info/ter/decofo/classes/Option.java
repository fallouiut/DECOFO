package m2info.ter.decofo.classes;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="Option")
@Table(name = "TOption")
public class Option implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "o_id")
    private int id;

    @Basic()
    @Column(name = "o_code", length = 200, nullable = false)
    private String code;

    @Basic()
    @Column(name = "o_intitule", length = 200, nullable = false)
    private String intitule;

    @Basic()
    @Column(name = "o_cout", length = 200, nullable = true)
    private double cout;

    @Basic()
    @Column(name = "o_effectifTotal", length = 200, nullable = true)
    private int effectifTotal;

    public Option() {

    }

    public Option(String code, String intitule, double cout, int effectifTotal) {
        this.code = code;
        this.intitule = intitule;
        this.cout = cout;
        this.effectifTotal = effectifTotal;
    }

    @Override
    public String toString() {
        return "Option{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", intitule='" + intitule + '\'' +
                ", cout=" + cout +
                ", effectifTotal=" + effectifTotal +
                '}';
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

    public int getEffectifTotal() {
        return effectifTotal;
    }

    public void setEffectifTotal(int effectifTotal) {
        this.effectifTotal = effectifTotal;
    }

}