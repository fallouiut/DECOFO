package m2info.ter.decofo.classes;

import java.io.Serializable;

public class Bloc implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;

    private String code;

    private String intitule;

    private double cout;

    public Bloc() {

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
