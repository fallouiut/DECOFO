package m2info.ter.decofo.managers;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Option;
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
public class OptionManager implements Manager<Option> {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    EntityManager em;

    @Override
    public void insert(Option object) {
        this.em.persist(object);
        this.em.flush();
        System.err.println("Option ajouté avec ID: " + object.getId());

    }

    @Override
    public void update(Option object) {
        em.merge(object);
    }

    @Override
    public void delete(int id) {
        Option option =  this.findOne(id);
        if(option != null) {
            option = em.merge(option);
            em.remove(option);
        } else {
            System.err.println("<NEXISTE PAS D'OPTION");
            throw new ServerErrorResponse("Option didn't exist");
        }
    }

    @Override
    public Option findOne(int id) {
        return this.em.find(Option.class, id);
    }

    @Override
    public List<Option> findAll() {
        try {
            String query = "SELECT o FROM Option o";
            TypedQuery<Option> q = em.createQuery(query, Option.class);

            List<Option> options = q.setMaxResults(200).getResultList();

            for(Option o: options)
                System.err.println(o.toString());

            return options;
        } catch (Exception e) {
            System.err.println("OptionManager.findAll()");
            return null;
        }
    }

}
