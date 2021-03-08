package m2info.ter.decofo.managers;

import m2info.ter.decofo.classes.UE;
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
public class UEManager implements Manager<UE> {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    EntityManager em;

    @Override
    public void insert(UE object) {
        this.em.persist(object);
        this.em.flush();
        System.err.println("UE ajouté avec ID: " + object.toString());
    }

    @Override
    public void update(UE object) {
        em.merge(object);

    }

    @Override
    public void delete(int id) {
        UE ue =  this.findOne(id);
        if(ue != null) {
            ue = em.merge(ue);
            em.remove(ue);
        } else {
            System.err.println("<NEXISTE PAS DE UE");
            throw new ServerErrorResponse("UE didn't exist");
        }
    }

    @Override
    public UE findOne(int id) {

        return this.em.find(UE.class, id);
    }

    @Override
    public List<UE> findAll() {
        try {
            String query = "SELECT u FROM UE u";
            TypedQuery<UE> q = em.createQuery(query, UE.class);

            List<UE> ues = q.setMaxResults(200).getResultList();

            return ues;
        } catch (Exception e) {
            System.err.println("UEManager.findAll()");
            return null;
        }
    }
}
