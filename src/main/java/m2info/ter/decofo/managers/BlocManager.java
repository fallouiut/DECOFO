package m2info.ter.decofo.managers;

import m2info.ter.decofo.classes.Bloc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

public class BlocManager implements Manager<Bloc> {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    EntityManager em;

    @Override
    public void insert(Bloc object) {

    }

    @Override
    public void update(Bloc object) {

    }

    @Override
    public void delete(int id) throws Exception {

    }

    @Override
    public Bloc findOne(int id) {
        return null;
    }

    @Override
    public List<Bloc> findAll() {
        return null;
    }
}
