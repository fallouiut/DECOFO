package m2info.ter.decofo.classes;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
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
    @Column(name = "f_cout", length = 200)
    double cout = 0;

    @Basic()
    @Column(name = "f_intitulé", length = 200, nullable = false)
    String intitule;

    @Basic()
    @Column(name = "f_cm")
    int tailleGroupeCM = 0;

    @Basic()
    @Column(name = "f_Td")
    int tailleGroupeTD = 0;

    @Basic()
    @Column(name = "f_Tp")
    int tailleGroupeTP = 0;

    @OneToMany(mappedBy = "formationOwner", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bloc> blocs = new ArrayList<>();

    @OneToMany(mappedBy = "formationOwner", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options = new ArrayList<>();

    @OneToMany(mappedBy = "formationOwner", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UE> ues = new ArrayList<>();

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

    public void addOption(Option o) {
        this.options.add(o);
        o.setFormationOwner(this);
    }

    public List<Option> getOptions() {
        return options;
    }

    public void removeOption(Option o) {
        this.options.remove(o);
    }

    public void addUE(UE ue) {
        this.ues.add(ue);
        ue.setFormationOwner(this);
    }

    public List<UE> getUEs() {
        return ues;
    }

    public void removeUE(UE ue) {
        this.ues.remove(ue);
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
    public Formation(String code, String intitule, int tailleGroupeCM, int tailleGroupeTD, int tailleGroupeTP) {
        this.code = code;
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
