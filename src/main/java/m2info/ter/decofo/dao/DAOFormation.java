package m2info.ter.decofo.dao;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.classes.Option;
import m2info.ter.decofo.classes.UE;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class DAOFormation extends DAO<Formation> {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    EntityManager em;

    @Override
    public Formation find(int id) {

        Formation f = this.em.find(Formation.class, id);

        if(f == null) return null;

        f.getBlocs().size();
        f.getUEs().size();
        f.getOptions().size();

        return f;
    }

    @Override
    public List<Formation> findAll() {
        try {
            String query = "SELECT f FROM Formation f";
            TypedQuery<Formation> q = this.em.createQuery(query, Formation.class);

            List<Formation> formations = q.setMaxResults(50).getResultList();

            return formations;
        } catch (Exception e) {
            System.err.println("FormationManager.findAll()");
            return null;
        }
    }

    public void addBloc(Formation f, Bloc b) {
        f.addBloc(b);
        this.update(f);
        System.err.println("blocId: " + b);
    }

    public void removeBloc(Formation f, Bloc b) {
        f.removeBloc(b);
        this.update(f);
    }


    public void addOption(Formation f, Option o) {
        f.addOption(o);
        this.update(f);
    }

    public void removeOption(Formation f, Option o) {
        f.removeOption(o);
        this.update(f);
    }

    public void addUE(Formation f, UE ue) {
        f.addUE(ue);
        this.update(f);
    }

    public void removeUE(Formation f, UE ue) {
        f.removeUE(ue);
        this.update(f);
    }

}
