package m2info.ter.decofo.dao;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Option;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class DAOOption extends DAO<Option> {
    @Override
    public Option find(int id) {
        Option o = this.em.find(Option.class, id);

        if(o == null) return null;

        o.getBlocs().size();

        return o;
    }

    @Override
    public List<Option> findAll() {
        try {
            String query = "SELECT o FROM Option o";
            TypedQuery<Option> o = this.em.createQuery(query, Option.class);

            List<Option> options = o.setMaxResults(50).getResultList();

            return options;
        } catch (Exception e) {
            System.err.println("DAOOption.findAll()");
            return null;
        }
    }

    public List<Option> findAllByFormation(int formationId) {
        return null;
    }


    public List<Option> findAllByBloc(int blocId) {

        return null;
    }
}
