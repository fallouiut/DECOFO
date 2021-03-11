package m2info.ter.decofo.dao;

import m2info.ter.decofo.classes.Bloc;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class DAOBloc extends DAO<Bloc> {

    @Override
    public Bloc find(int id) {
        Bloc b = this.em.find(Bloc.class, id);

        if(b == null) return null;

        b.getOptions().size();
        b.getUes().size();

        return b;
    }

    @Override
    public List<Bloc> findAll() {
        try {
            String query = "SELECT b FROM Bloc b";
            TypedQuery<Bloc> q = this.em.createQuery(query, Bloc.class);

            List<Bloc> blocs = q.setMaxResults(50).getResultList();

            return blocs;
        } catch (Exception e) {
            System.err.println("BlocManager.findAll()");
            return null;
        }
    }

    public List<Bloc> findAllByFormation(int formationId) {
        try {
            String query = "SELECT b FROM Bloc b JOIN b.formationOwner owner WHERE owner.id = :id";
            TypedQuery<Bloc> q = this.em.createQuery(query, Bloc.class);

            List<Bloc> blocs = q.setParameter("id", formationId).setMaxResults(50).getResultList();

            return blocs;
        } catch (Exception e) {
            System.err.println("BlocManager.findAll()");
            return null;
        }
    }

}
