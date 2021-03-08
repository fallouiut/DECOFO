package m2info.ter.decofo.managers;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.exceptions.NotFoundObjectException;
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
        return this.em.find(Formation.class, id);
    }

    public List<Formation> findAll() {
        try {
            String query = "SELECT f FROM Formation f";
            TypedQuery<Formation> q = em.createQuery(query, Formation.class);

            List<Formation> formations = q.setMaxResults(200).getResultList();

            return formations;
        } catch (Exception e) {
            System.err.println("FormationManager.findAll()");
            return null;
        }
    }

    /**
     * vérifier que la formation existe
     * vérifier que le bloc existe
     * vérifier que le bloc n'appartient deja pas à une formations
     * @param formationId
     * @param blocId
     * @throws Exception
     */
    public void linkBloc(int formationId, int blocId) throws Exception {

        // vérifie que la formation existe
        Formation formation = this.findOne(formationId);
        if (formation == null) throw new NotFoundObjectException("Formation "+ formationId+ " n'existe pas");

        // vérifie que le bloc existe
        Bloc bloc = this.em.find(Bloc.class, blocId);
        if(bloc == null) throw new NotFoundObjectException("Bloc "+ blocId +" n'existe pas");

        // vérifie que le bloc n'a pas de formation parent
        if(bloc.getFormationOwner() != null) throw new NotFoundObjectException("Bloc "+ blocId + " a deja une formation parent");

        // crée le lien
        formation.addBloc(bloc);
        this.update(formation);
    }

    public void unlinkBloc(int formationId, int blocId) throws Exception {
        // vérifie que la formation existe
        Formation formation = this.findOne(formationId);
        if (formation == null) throw new NotFoundObjectException("Formation "+ formationId+ " n'existe pas");

        // vérifie que le bloc existe
        Bloc bloc = this.em.find(Bloc.class, blocId);
        if(bloc == null) throw new NotFoundObjectException("Bloc "+ blocId +" n'existe pas");

        // crée le lien
        formation.removeBloc(bloc);
        this.update(formation);
    }
}
