package m2info.ter.decofo.classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @Column(name = "b_cout", length = 200)
    private double cout = 0;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "f_id")
    private Formation formationOwner;

    // ues
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<UE> ues = new ArrayList<>();

    // options
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private List<Option> options = new ArrayList<>();

    @Embedded
    private Effectif effectif;

    public Bloc() {

    }

    public Bloc(String code, String intitule) {
        this.code = code;
        this.intitule = intitule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bloc bloc = (Bloc) o;
        return id == bloc.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

    // --------- UE -------------
    public void addUE(UE ue) {
        this.ues.add(ue);
        ue.addBloc(this);
    }

    public void removeUE(UE ue) {
        this.ues.remove(ue);
        //ue.getBlocs().remove(this);
    }

    public List<UE> getUes() {
        return ues;
    }

    public void setUes(List<UE> ues) {
        this.ues = ues;
    }

    // --------- OPTIONS -------------
    public void addOption(Option option) {
        this.options.add(option);
        option.addBloc(this);
    }

    public void removeOption(Option option) {
        this.options.remove(option);
        //option.getBlocs().remove(this);
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
    // ------------------------------------

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

    public Effectif getEffectif() {
        return effectif;
    }

    public void setEffectif(Effectif effectif) {
        this.effectif = effectif;
    }
}
