package m2info.ter.decofo.classes;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "f_id")
    private Formation formationOwner;

    @JsonIgnore
    @ManyToMany(mappedBy = "options", fetch = FetchType.LAZY)
    List<Bloc> blocs = new ArrayList<>();

    // ues
    @ManyToMany(fetch = FetchType.LAZY)
    private List<UE> ues = new ArrayList<>();

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;
        return id == option.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // ------------------------- blocs
    
    public void addBloc(Bloc bloc) {
        this.blocs.add(bloc);
    }

    public void removeBloc(Bloc bloc) {
        List<Bloc> newBlocs = new ArrayList<>();

        for(Bloc myBloc: blocs)
            if(bloc.getId() != myBloc.getId())
                newBlocs.add(myBloc);

        this.blocs = newBlocs;
    }

    public List<Bloc> getBlocs() {
        return blocs;
    }

    // --------- UE -------------

    public void addUE(UE ue) {
        this.ues.add(ue);
        ue.addOption(this);
    }

    public void removeUE(UE ue) {
        List<UE> newUes = new ArrayList<>();

        for(UE myUe: ues)
            if(ue.getId() != myUe.getId())
                newUes.add(myUe);

        this.ues = newUes;
        ue.removeOption(this);
    }

    public List<UE> getUes() {
        return ues;
    }

    public void setUes(List<UE> ues) {
        this.ues = ues;
    }

    // ------------------------------------

    public void setBlocs(List<Bloc> blocs) {
        this.blocs = blocs;
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

    public int getEffectifTotal() {
        return effectifTotal;
    }

    public void setEffectifTotal(int effectifTotal) {
        this.effectifTotal = effectifTotal;
    }

}