package m2info.ter.decofo.classes;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Bloc")
@Table(name = "TBloc")
public class Bloc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "b_id")
    private int id;

    @Basic()
    @Column(name = "b_code", length = 200)
    private String code;

    @Basic()
    @Column(name = "b_intitule", length = 200)
    private String intitule;

    @Basic()
    @Column(name = "b_cout", length = 200, nullable = true)
    private double cout;

    @ManyToOne
    @JoinColumn(name = "f_id")
    private Formation formationOwner;

    // ues
    @ManyToMany(fetch = FetchType.LAZY)
    private List<UE> ues = new ArrayList<>();

    // options
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Option> options = new ArrayList<>();

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

    // --------- UE -------------
    public void addUE(UE ue) {
        this.ues.add(ue);
        ue.addBloc(this);
    }

    public void removeUE(UE ue) {
        List<UE> newUes = new ArrayList<>();

        for(UE myUe: ues)
            if(ue.getId() != myUe.getId())
                newUes.add(myUe);

        this.ues = newUes;
        ue.removeBloc(this);
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
        List<Option> newOption = new ArrayList<>();

        for(Option myOption: options)
            if(option.getId() != myOption.getId())
                newOption.add(option);

        this.options = newOption;
        option.removeBloc(this);
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

}
