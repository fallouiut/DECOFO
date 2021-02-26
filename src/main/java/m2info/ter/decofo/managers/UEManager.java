package m2info.ter.decofo.managers;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.UE;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

public class UEManager implements Manager<UE> {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    EntityManager em;

    @Override
    public void insert(UE object) {

    }

    @Override
    public void update(UE object) {

    }

    @Override
    public void delete(int id) throws Exception {

    }

    @Override
    public UE findOne(int id) {
        return null;
    }

    @Override
    public List<UE> findAll() {
        return null;
    }
}
