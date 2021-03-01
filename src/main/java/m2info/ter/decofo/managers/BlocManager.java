package m2info.ter.decofo.managers;

import m2info.ter.decofo.classes.Bloc;
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
public class BlocManager {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    EntityManager em;

    public EntityManager entityManager() {
        return this.em;
    }

    public void insert(Bloc object) {
        this.em.persist(object);
        this.em.flush();
        System.err.println("Bloc ajouté avec ID: " + object.getId());
    }

    public void update(Bloc object) throws Exception  {
        boolean exist = this.findOne(object.getId()) != null;

        if(exist) {
            em.merge(object);
        } else {
            System.err.println("N'existe pas dans manager");
            throw new Exception("Bloc doesn't exist");
        }
    }

    public void delete(int id) throws Exception {
        Bloc bloc =  this.findOne(id);
        if(bloc != null) {
            bloc = em.merge(bloc);
            em.remove(bloc);
        } else {
            System.err.println("<NEXISTE PAS DE Bloc");
            throw new ServerErrorResponse("Bloc didn't exist");
        }
    }

    public Bloc findOne(int id) throws Exception  {
        Bloc b = this.em.find(Bloc.class, id);
        return b;
    }

    public List<Bloc> findAll() {
        try {
            String query = "SELECT b FROM Bloc b";
            TypedQuery<Bloc> q = em.createQuery(query, Bloc.class);

            List<Bloc> blocs = q.setMaxResults(200).getResultList();

            for(Bloc f: blocs)
                System.err.println(f.toString());

            return blocs;
        } catch (Exception e) {
            System.err.println("BlocManager.findAll()");
            return null;
        }
    }
}
