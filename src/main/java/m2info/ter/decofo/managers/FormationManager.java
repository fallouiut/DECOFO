package m2info.ter.decofo.managers;

import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.exceptions.ServerErrorResponse;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class FormationManager implements Manager<Formation> {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    EntityManager em;

    public void insert(Formation formation) {
        this.em.persist(formation);
        this.em.flush();
        System.err.println("Formation ajouté avec ID: " + formation.getId());
    }

    public void update(Formation object) {
        em.merge(object);
    }

    public void delete(int id) throws Exception {
        Formation formation =  this.findOne(id);
        if(formation != null) {
            formation = em.merge(formation);
            em.remove(formation);
        } else {
            System.err.println("<NEXISTE PAS DE FORMATION");
            throw new ServerErrorResponse("Formation didn't exist");
        }
    }

    public Formation findOne(int id) throws Exception {
        Formation formation = this.em.find(Formation.class, id);
        if(formation == null) {
            throw new Exception("formation not found");
        }
        return formation;
    }

    public List<Formation> findAll() {
        try {
            String query = "SELECT f FROM Formation f";
            TypedQuery<Formation> q = em.createQuery(query, Formation.class);

            List<Formation> formations = q.setMaxResults(200).getResultList();

            for(Formation f: formations)
                System.err.println(f.toString());

            return formations;
        } catch (Exception e) {
            System.err.println("FormationManager.findAll()");
            return null;
        }
    }
}
