package m2info.ter.decofo.managers;

import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.dao.DAOFormation;
import m2info.ter.decofo.exceptions.ServerErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormationManager implements Manager<Formation> {

    @Autowired
    DAOFormation daoFormation;

    public void insert(Formation formation) {
        daoFormation.insert(formation);
    }

    public void update(Formation object) throws Exception {
        boolean exist = this.findOne(object.getId()) != null;

        if(exist) {
            daoFormation.update(object);
        } else {
            throw new Exception("Bloc doesn't exist");
        }
    }

    public void delete(int id) throws Exception {
        Formation formation =  this.findOne(id);
        if(formation != null) {
            daoFormation.delete(formation);
        } else {
            throw new ServerErrorResponse("Formation didn't exist");
        }
    }

    public Formation findOne(int id) throws Exception {
        Formation f = this.daoFormation.find(id);
        return f;
    }

    public List<Formation> findAll() {
        return daoFormation.findAll();
    }
}
