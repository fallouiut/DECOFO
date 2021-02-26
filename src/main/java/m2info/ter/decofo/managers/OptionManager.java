package m2info.ter.decofo.managers;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Option;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

public class OptionManager  implements Manager<Option> {


    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    EntityManager em;

    @Override
    public void insert(Option object) {

    }

    @Override
    public void update(Option object) {

    }

    @Override
    public void delete(int id) throws Exception {

    }

    @Override
    public Option findOne(int id) {
        return null;
    }

    @Override
    public List<Option> findAll() {
        return null;
    }
}
