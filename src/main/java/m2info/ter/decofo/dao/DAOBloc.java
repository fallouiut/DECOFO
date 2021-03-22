package m2info.ter.decofo.dao;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Option;
import m2info.ter.decofo.classes.UE;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
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

    public void linkUE(Bloc b, UE ue) {
        b.addUE(ue);
        this.update(b);
    }

    public void unlinkUE(Bloc b, UE ue) {
        Bloc obj = this.find(b.getId()); // a faire sinon erreur lazy

        obj.removeUE(ue);
        this.update(obj);
    }

    public void unlinkAll(Bloc b) {
        Bloc obj = this.find(b.getId());

        // enlever liaisons
        for(UE ue: obj.getUes()) {
            ue.getBlocs().size();
            ue.removeBloc(obj);
        }

        // enlever liaisons
        for(Option option: obj.getOptions()) {
            option.getBlocs().size();
            option.removeBloc(obj);
        }

        obj.setUes(new ArrayList<>());
        obj.setOptions(new ArrayList<>());
    }

    public void linkOption(Bloc b, Option o) {
        b.addOption(o);
        this.update(b);
    }

    public void unlinkOption(Bloc b, Option o) {
        Bloc obj = this.find(b.getId()); // a faire sinon erreur lazy

        obj.removeOption(o);
        this.update(obj);
    }
}
