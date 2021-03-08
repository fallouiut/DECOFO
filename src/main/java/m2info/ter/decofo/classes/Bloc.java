package m2info.ter.decofo.classes;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Bloc")
@Table(name = "TBloc")
public class Bloc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "b_id")
    private int id;

    @Basic()
    @Column(name = "b_code", length = 200, nullable = false)
    private String code;

    @Basic()
    @Column(name = "b_intitule", length = 200, nullable = false)
    private String intitule;

    @Basic()
    @Column(name = "b_cout", length = 200, nullable = true)
    private double cout;

    @ManyToOne
    @JoinColumn(name = "f_id")
    private Formation formationOwner;

    public Bloc() {

    }

    public Bloc(String code, String intitule, double cout) {
        this.code = code;
        this.intitule = intitule;
        this.cout = cout;
    }

    @Override
    public String toString() {
        return "Bloc{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", intitule='" + intitule + '\'' +
                ", cout=" + cout +
                '}';
    }

    public Formation getFormationOwner() {
        return formationOwner;
    }

    public void setFormationOwner(Formation formationOwner) {
        this.formationOwner = formationOwner;
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

}
