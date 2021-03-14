package m2info.ter.decofo.manager.gestion;

import m2info.ter.decofo.classes.UE;
import m2info.ter.decofo.dao.DAOUe;
import m2info.ter.decofo.exceptions.ServerErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UEManager implements Manager<UE> {

    @Autowired
    DAOUe daoUe;

    @Override
    public void insert(UE object) {
        daoUe.insert(object);
    }

    @Override
    public void update(UE object) throws Exception {
        boolean exist = this.findOne(object.getId()) != null;

        if(exist) {
            daoUe.update(object);
        } else {
            throw new Exception("UE doesn't exist");
        }

    }

    @Override
    public void delete(int id) {
        UE ue =  this.findOne(id);
        if(ue != null) {
            daoUe.delete(ue);
        } else {
            throw new ServerErrorResponse("UE didn't exist");
        }
    }

    @Override
    public UE findOne(int id) {
        return daoUe.find(id);
    }

    @Override
    public List<UE> findAll() {
        return daoUe.findAll();
    }
}
