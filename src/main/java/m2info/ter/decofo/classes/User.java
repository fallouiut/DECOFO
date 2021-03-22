package m2info.ter.decofo.classes;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "User")
@Table(name = "TUser")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "u_id")
    int id;

    @Basic()
    @Column(name = "u_email", length = 200, nullable = false)
    String email;

    @Basic()
    @Column(name = "u_mot_de_passe", length = 200, nullable = false)
    String motDePasse;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Formation> ownedFormations = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Formation> visitedFormations = new ArrayList<>();


    public User() {

    }

    public User(String email, String motDePasse) {
        this.email = email;
        this.motDePasse = motDePasse;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public List<Formation> getOwnedFormations() {
        return ownedFormations;
    }

    public void addOwnedFormation(Formation formation) {
        this.ownedFormations.add(formation);
        formation.setOwner(this);
    }

    public void addVisitedFormation(Formation formation) {
        this.visitedFormations.add(formation);
        formation.addVisitor(this);
    }

    public void removeVisitedFormation(Formation formation) {
        System.err.println("contient ?: " + this.visitedFormations.contains(formation));
        this.visitedFormations.remove(formation);
        System.err.println("size of this: " + this.visitedFormations.size());
    }

    public List<Formation> getVisitedFormations() {
        return visitedFormations;
    }
}
