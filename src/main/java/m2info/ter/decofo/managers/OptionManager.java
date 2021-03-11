package m2info.ter.decofo.managers;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Option;
import m2info.ter.decofo.dao.DAOOption;
import m2info.ter.decofo.exceptions.ServerErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class OptionManager implements Manager<Option> {

    @Autowired
    DAOOption daoOption;

    @Override
    public void insert(Option object) {
        daoOption.insert(object);

    }

    @Override
    public void update(Option object) throws Exception {
        boolean exist = this.findOne(object.getId()) != null;

        if(exist) {
            daoOption.update(object);
        } else {
            throw new Exception("Bloc doesn't exist");
        }
    }

    @Override
    public void delete(int id) {
        Option option =  this.findOne(id);
        if(option != null) {
            daoOption.delete(option);
        } else {
            System.err.println("<NEXISTE PAS D'OPTION");
            throw new ServerErrorResponse("Option didn't exist");
        }
    }

    @Override
    public Option findOne(int id) {
        return this.daoOption.find(id);
    }

    @Override
    public List<Option> findAll() {
        return this.daoOption.findAll();
    }

}
