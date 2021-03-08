package m2info.ter.decofo.classes;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "Formation")
@Table(name = "TFormation")
public class Formation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "f_id")
    int id;

    @Basic()
    @Column(name = "f_code", length = 200, nullable = false)
    String code;

    @Basic()
    @Column(name = "f_cout")
    double cout;

    @Basic()
    @Column(name = "f_intitulé", length = 200, nullable = false)
    String intitule;

    @Basic()
    @Column(name = "f_cm")
    int tailleGroupeCM;

    @Basic()
    @Column(name = "f_Td")
    int tailleGroupeTD;

    @Basic()
    @Column(name = "f_Tp")
    int tailleGroupeTP;

    @JsonIgnore
    @OneToMany(mappedBy = "formationOwner", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<Bloc> blocs;

    public void addBloc(Bloc b) {
        this.blocs.add(b);
        b.setFormationOwner(this);
    }

    public void removeBloc(Bloc b) {
        this.blocs.remove(b);
    }

    public List<Bloc> getBlocs() {
        return blocs;
    }

    public void setBlocs(List<Bloc> blocs) {
        this.blocs = blocs;
    }

    @Override
    public String toString() {
        return "Formation{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", cout=" + cout +
                ", intitule='" + intitule + '\'' +
                ", tailleGroupeCM=" + tailleGroupeCM +
                ", tailleGroupeTD=" + tailleGroupeTD +
                ", tailleGroupeTP=" + tailleGroupeTP +
                '}';
    }

    public Formation() {

    }

    /**
     * attention à ne pas mettre l'ID
     */
    public Formation(String code, double cout, String intitule, int tailleGroupeCM, int tailleGroupeTD, int tailleGroupeTP) {
        this.code = code;
        this.cout = cout;
        this.intitule = intitule;
        this.tailleGroupeCM = tailleGroupeCM;
        this.tailleGroupeTD = tailleGroupeTD;
        this.tailleGroupeTP = tailleGroupeTP;
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

    public double getCout() {
        return cout;
    }

    public void setCout(double cout) {
        this.cout = cout;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public int getTailleGroupeCM() {
        return tailleGroupeCM;
    }

    public void setTailleGroupeCM(int tailleGroupeCM) {
        this.tailleGroupeCM = tailleGroupeCM;
    }

    public int getTailleGroupeTD() {
        return tailleGroupeTD;
    }

    public void setTailleGroupeTD(int tailleGroupeTD) {
        this.tailleGroupeTD = tailleGroupeTD;
    }

    public int getTailleGroupeTP() {
        return tailleGroupeTP;
    }

    public void setTailleGroupeTP(int tailleGroupeTP) {
        this.tailleGroupeTP = tailleGroupeTP;
    }
}
