package m2info.ter.decofo.dao;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Option;
import m2info.ter.decofo.classes.UE;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class DAOOption extends DAO<Option> {
    @Override
    public Option find(int id) {
        Option o = this.em.find(Option.class, id);

        if(o == null) return null;

        o.getBlocs().size();
        o.getUes().size();

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
            e.printStackTrace();
            return null;
        }
    }

    public void linkUE(Option o, UE ue) {
        o.addUE(ue);
        this.update(o);
    }

    public void unlinkUE(Option o, UE ue) {
        Option obj = this.find(o.getId());
        obj.removeUE(ue);
        this.update(obj);
    }

    public void unlinkAll(Option o) {
        Option obj = this.find(o.getId());

        // enlever liaisons
        for(UE ue: obj.getUes()) {
            ue.getOptions().size();
            ue.removeOption(obj);
        }

        // enlever liaisons
        for(Bloc bloc: obj.getBlocs()) {
            bloc.getOptions().size();
            bloc.removeOption(obj);
        }

        obj.setUes(new ArrayList<>());
        obj.setBlocs(new ArrayList<>());
    }

}
