package m2info.ter.decofo.classes;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "UE")
@Table(name = "T_UE")
public class UE implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Basic()
    @Column(name = "ue_code", length = 200, nullable = false)
    private String code;

    @Basic()
    @Column(name = "ue_intitule", length = 200, nullable = false)
    private String intitule;

    @Basic()
    @Column(name = "ue_cout", length = 200, nullable = true)
    private double cout;

    @Basic()
    @Column(name = "ue_nombreHeureCM", length = 200, nullable = true)
    private double nombreHeureCM;

    @Basic()
    @Column(name = "ue_nombreHeureTD", length = 200, nullable = true)
    private double nombreHeureTD;

    @Basic()
    @Column(name = "ue_nombreHeureTP", length = 200, nullable = true)
    private double nombreHeureTP;

    // calcul intermediaire, données non obligatoires
    @Basic()
    @Column(name = "ue_nombreGroupesCM", length = 200, nullable = true)
    private int nombreGroupesCM;

    @Basic()
    @Column(name = "ue_nombreGroupesTD", length = 200, nullable = true)
    private int nombreGroupesTD;

    @Basic()
    @Column(name = "ue_nombreGroupesTP", length = 200, nullable = true)
    private int nombreGroupesTP;

    @Basic()
    @Column(name = "ue_effectifTotal", length = 200, nullable = true)
    private int effectifTotal;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "f_id")
    private Formation formationOwner;

    // ue parent
    @JsonIgnore
    @ManyToMany(mappedBy = "ues", fetch = FetchType.LAZY)
    List<Bloc> blocs = new ArrayList<>();

    // option parent
    @JsonIgnore
    @ManyToMany(mappedBy = "ues", fetch = FetchType.LAZY)
    List<Option> options = new ArrayList<>();

    public UE() {

    }

    public UE(String code, String intitule, double cout, double nombreHeureCM, double nombreHeureTD, double nombreHeureTP, int nombreGroupesCM, int nombreGroupesTD, int nombreGroupesTP, int effectifTotal) {
        this.code = code;
        this.intitule = intitule;
        this.cout = cout;
        this.nombreHeureCM = nombreHeureCM;
        this.nombreHeureTD = nombreHeureTD;
        this.nombreHeureTP = nombreHeureTP;
        this.nombreGroupesCM = nombreGroupesCM;
        this.nombreGroupesTD = nombreGroupesTD;
        this.nombreGroupesTP = nombreGroupesTP;
        this.effectifTotal = effectifTotal;
    }

    @Override
    public String toString() {
        return "UE{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", intitule='" + intitule + '\'' +
                ", cout=" + cout +
                ", nombreHeureCM=" + nombreHeureCM +
                ", nombreHeureTD=" + nombreHeureTD +
                ", nombreHeureTP=" + nombreHeureTP +
                ", nombreGroupesCM=" + nombreGroupesCM +
                ", nombreGroupesTD=" + nombreGroupesTD +
                ", nombreGroupesTP=" + nombreGroupesTP +
                ", effectifTotal=" + effectifTotal +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UE ue = (UE) o;
        return id == ue.id;
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

    // --------- OPTIONS -------------
    public void addOption(Option option) {
        this.options.add(option);
    }

    public void removeOption(Option option) {
        List<Option> newOption = new ArrayList<>();

        for(Option myOption: options)
            if(option.getId() != myOption.getId())
                newOption.add(option);

        this.options = newOption;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
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