package m2info.ter.decofo.dao;

import m2info.ter.decofo.classes.UE;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class DAOUe extends DAO<UE> {

    @Override
    public UE find(int id) {
        UE ue = this.em.find(UE.class, id);

        if (ue == null) return null;

        ue.getBlocs().size();
        ue.getOptions().size();

        return ue;
    }

    @Override
    public List<UE> findAll() {
        try {
            String query = "SELECT u FROM UE u";
            TypedQuery<UE> q = this.em.createQuery(query, UE.class);

            List<UE> ues = q.setMaxResults(200).getResultList();

            return ues;
        } catch (Exception e) {
            System.err.println("UEManager.findAll()");
            return null;
        }
    }

    public List<UE> findAllByFormation(int formationId) {
        return null;
    }


    public List<UE> findAllByBloc(int blocId) {
        return null;
    }

    public List<UE> findAllByOption(int optionId) {
        return null;
    }

}
