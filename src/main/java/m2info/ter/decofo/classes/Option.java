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
    @Column(name = "o_cout", length = 200)
    private double cout = 0;

    @Basic()
    @Column(name = "o_credits", length = 200)
    private int credits = 0;

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

    // ----------------------- //
    // récupération des effectifs par site //
    @Transient
    private int effectifOptionSite1;
    @Transient
    private int effectifOptionSite2;
    @Transient
    private int effectifOptionSite3;
    @Transient
    private int effectifOptionSite4;

    // effectifs par UE
    @Transient
    private int effectifParUESite1;
    @Transient
    private int effectifParUESite2;
    @Transient
    private int effectifParUESite3;
    @Transient
    private int effectifParUESite4;
    // ----------------------- //

    public Option() {

    }

    public Option(String code, String intitule, int credits) {
        this.code = code;
        this.intitule = intitule;
        this.credits = credits;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        return "Option{" +
                "code='" + code + '\'' +
                ", intitule='" + intitule + '\'' +
                ", cout=" + cout +
                ", credits=" + credits +
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

    public int getEffectifOptionSite1() {
        return effectifOptionSite1;
    }

    public void setEffectifOptionSite1(int effectifOptionSite1) {
        this.effectifOptionSite1 = effectifOptionSite1;
    }

    public int getEffectifOptionSite2() {
        return effectifOptionSite2;
    }

    public void setEffectifOptionSite2(int effectifOptionSite2) {
        this.effectifOptionSite2 = effectifOptionSite2;
    }

    public int getEffectifOptionSite3() {
        return effectifOptionSite3;
    }

    public void setEffectifOptionSite3(int effectifOptionSite3) {
        this.effectifOptionSite3 = effectifOptionSite3;
    }

    public int getEffectifOptionSite4() {
        return effectifOptionSite4;
    }

    public void setEffectifOptionSite4(int effectifOptionSite4) {
        this.effectifOptionSite4 = effectifOptionSite4;
    }

    public int getEffectifParUESite1() {
        return effectifParUESite1;
    }

    public void setEffectifParUESite1(int effectifParUESite1) {
        this.effectifParUESite1 = effectifParUESite1;
    }

    public int getEffectifParUESite2() {
        return effectifParUESite2;
    }

    public void setEffectifParUESite2(int effectifParUESite2) {
        this.effectifParUESite2 = effectifParUESite2;
    }

    public int getEffectifParUESite3() {
        return effectifParUESite3;
    }

    public void setEffectifParUESite3(int effectifParUESite3) {
        this.effectifParUESite3 = effectifParUESite3;
    }

    public int getEffectifParUESite4() {
        return effectifParUESite4;
    }

    public void setEffectifParUESite4(int effectifParUESite4) {
        this.effectifParUESite4 = effectifParUESite4;
    }

    public int getEffectifTotalParUE() {
        return effectifParUESite1 + effectifParUESite2 + effectifParUESite3 + effectifParUESite4;
    }

    public int getEffectifTotal() {
        return effectifOptionSite1 + effectifOptionSite2 + effectifOptionSite3 + effectifOptionSite4;
    }

}