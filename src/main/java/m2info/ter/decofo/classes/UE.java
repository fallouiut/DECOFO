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
    @Column(name = "ue_cout", length = 200)
    private double cout = 0;

    @Basic()
    @Column(name = "ue_nombreHeureCM", length = 200)
    private double nombreHeureCM = 0;

    @Basic()
    @Column(name = "ue_nombreHeureTD", length = 200)
    private double nombreHeureTD = 0;

    @Basic()
    @Column(name = "ue_nombreHeureTP", length = 200)
    private double nombreHeureTP = 0;

    @Basic()
    @Column(name = "ue_credits", length = 200)
    private int credits = 0;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "f_id")
    private Formation formationOwner;

    // ue parent
    @JsonIgnore
    @ManyToMany(mappedBy = "ues", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    List<Bloc> blocs = new ArrayList<>();

    // option parent
    @JsonIgnore
    @ManyToMany(mappedBy = "ues", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    List<Option> options = new ArrayList<>();



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

    // ----------------------- //
    // Valeurs intermédiaires UE //
    @Transient
    @JsonIgnore
    @Embedded
    private Effectif effectifTotalParSite = new Effectif();


    // ----------------------- //
    @Transient
    int nombreGroupeCMSite1;
    @Transient
    int nombreGroupeCMSite2;
    @Transient
    int nombreGroupeCMSite3;
    @Transient
    int nombreGroupeCMSite4;

    @Transient
    int nombreGroupeTDSite1;
    @Transient
    int nombreGroupeTDSite2;
    @Transient
    int nombreGroupeTDSite3;
    @Transient
    int nombreGroupeTDSite4;

    @Transient
    int nombreGroupeTPSite1;
    @Transient
    int nombreGroupeTPSite2;
    @Transient
    int nombreGroupeTPSite3;
    @Transient
    int nombreGroupeTPSite4;

    public UE() {

    }

    public UE(String code, String intitule, double nombreHeureCM, double nombreHeureTD, double nombreHeureTP, int credits) {
        this.code = code;
        this.intitule = intitule;
        this.nombreHeureCM = nombreHeureCM;
        this.nombreHeureTD = nombreHeureTD;
        this.nombreHeureTP = nombreHeureTP;
        this.credits = credits;
    }

    public UE(String code, String intitule, double nombreHeureCM, double nombreHeureTD, double nombreHeureTP, int nombreGroupesCM, int nombreGroupesTD, int nombreGroupesTP, int credits) {
        this.code = code;
        this.intitule = intitule;
        this.nombreHeureCM = nombreHeureCM;
        this.nombreHeureTD = nombreHeureTD;
        this.nombreHeureTP = nombreHeureTP;
        this.nombreGroupesCM = nombreGroupesCM;
        this.nombreGroupesTD = nombreGroupesTD;
        this.nombreGroupesTP = nombreGroupesTP;
        this.credits = credits;
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
                ", credits=" + credits +
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
        blocs.remove(bloc);
    }

    public List<Bloc> getBlocs() {
        return blocs;
    }

    // --------- OPTIONS -------------
    public void addOption(Option option) {
        this.options.add(option);
    }

    public void removeOption(Option option) {
        options.remove(option);
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

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getNombreGroupeCMSite1() {
        return nombreGroupeCMSite1;
    }

    public void setNombreGroupeCMSite1(int nombreGroupeCMSite1) {
        this.nombreGroupeCMSite1 = nombreGroupeCMSite1;
    }

    public int getNombreGroupeCMSite2() {
        return nombreGroupeCMSite2;
    }

    public void setNombreGroupeCMSite2(int nombreGroupeCMSite2) {
        this.nombreGroupeCMSite2 = nombreGroupeCMSite2;
    }

    public int getNombreGroupeCMSite3() {
        return nombreGroupeCMSite3;
    }

    public void setNombreGroupeCMSite3(int nombreGroupeCMSite3) {
        this.nombreGroupeCMSite3 = nombreGroupeCMSite3;
    }

    public int getNombreGroupeCMSite4() {
        return nombreGroupeCMSite4;
    }

    public void setNombreGroupeCMSite4(int nombreGroupeCMSite4) {
        this.nombreGroupeCMSite4 = nombreGroupeCMSite4;
    }

    public int getNombreGroupeTDSite1() {
        return nombreGroupeTDSite1;
    }

    public void setNombreGroupeTDSite1(int nombreGroupeTDSite1) {
        this.nombreGroupeTDSite1 = nombreGroupeTDSite1;
    }

    public int getNombreGroupeTDSite2() {
        return nombreGroupeTDSite2;
    }

    public void setNombreGroupeTDSite2(int nombreGroupeTDSite2) {
        this.nombreGroupeTDSite2 = nombreGroupeTDSite2;
    }

    public int getNombreGroupeTDSite3() {
        return nombreGroupeTDSite3;
    }

    public void setNombreGroupeTDSite3(int nombreGroupeTDSite3) {
        this.nombreGroupeTDSite3 = nombreGroupeTDSite3;
    }

    public int getNombreGroupeTDSite4() {
        return nombreGroupeTDSite4;
    }

    public void setNombreGroupeTDSite4(int nombreGroupeTDSite4) {
        this.nombreGroupeTDSite4 = nombreGroupeTDSite4;
    }

    public int getNombreGroupeTPSite1() {
        return nombreGroupeTPSite1;
    }

    public void setNombreGroupeTPSite1(int nombreGroupeTPSite1) {
        this.nombreGroupeTPSite1 = nombreGroupeTPSite1;
    }

    public int getNombreGroupeTPSite2() {
        return nombreGroupeTPSite2;
    }

    public void setNombreGroupeTPSite2(int nombreGroupeTPSite2) {
        this.nombreGroupeTPSite2 = nombreGroupeTPSite2;
    }

    public int getNombreGroupeTPSite3() {
        return nombreGroupeTPSite3;
    }

    public void setNombreGroupeTPSite3(int nombreGroupeTPSite3) {
        this.nombreGroupeTPSite3 = nombreGroupeTPSite3;
    }

    public int getNombreGroupeTPSite4() {
        return nombreGroupeTPSite4;
    }

    public void setNombreGroupeTPSite4(int nombreGroupeTPSite4) {
        this.nombreGroupeTPSite4 = nombreGroupeTPSite4;
    }

    public Effectif getEffectifTotalParSite() {
        return effectifTotalParSite;
    }

    public void setEffectifTotalParSite(Effectif effectifTotalParSite) {
        this.effectifTotalParSite = effectifTotalParSite;
    }
}
