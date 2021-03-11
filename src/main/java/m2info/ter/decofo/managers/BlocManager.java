package m2info.ter.decofo.managers;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.dao.DAOBloc;
import m2info.ter.decofo.exceptions.ServerErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BlocManager {

    @Autowired
    DAOBloc daoBloc;

    public void insert(Bloc object) {
        daoBloc.insert(object);
    }

    public void update(Bloc object) throws Exception  {
        boolean exist = this.findOne(object.getId()) != null;

        if(exist) {
            daoBloc.update(object);
        } else {
            System.err.println("N'existe pas dans manager");
            throw new Exception("Bloc doesn't exist");
        }
    }

    public void delete(int id) throws Exception {
        Bloc bloc =  this.findOne(id);
        if(bloc != null) {
            daoBloc.delete(bloc);
        } else {
            System.err.println("<NEXISTE PAS DE Bloc");
            throw new ServerErrorResponse("Bloc didn't exist");
        }
    }

    public Bloc findOne(int id) throws Exception  {
        Bloc b = daoBloc.find(id);
        return b;
    }

    public List<Bloc> findAll() {
        return daoBloc.findAll();
    }
}
