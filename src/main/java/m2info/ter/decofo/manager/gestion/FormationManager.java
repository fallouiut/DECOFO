package m2info.ter.decofo.manager.gestion;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.classes.Option;
import m2info.ter.decofo.classes.UE;
import m2info.ter.decofo.dao.DAOBloc;
import m2info.ter.decofo.dao.DAOFormation;
import m2info.ter.decofo.dao.DAOOption;
import m2info.ter.decofo.dao.DAOUe;
import m2info.ter.decofo.exceptions.ItemExistInListException;
import m2info.ter.decofo.exceptions.NotFoundObjectException;
import m2info.ter.decofo.exceptions.ServerErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormationManager implements Manager<Formation> {

    @Autowired
    DAOFormation daoFormation;

    @Autowired
    DAOBloc daoBloc;

    @Autowired
    DAOOption daoOption;

    @Autowired
    DAOUe daoUe;

    public void insert(Formation formation) {
        daoFormation.insert(formation);
    }

    public void update(Formation object) throws Exception {

        Formation formation = daoFormation.find(object.getId());

        if(formation != null) {

            formation.setTailleGroupeCM(object.getTailleGroupeCM());
            formation.setTailleGroupeTD(object.getTailleGroupeTD());
            formation.setTailleGroupeTP(object.getTailleGroupeTP());
            formation.setCode(object.getCode());
            formation.setIntitule(object.getIntitule());

            daoFormation.update(formation);
        } else {
            throw new NotFoundObjectException("Formation "+object.getId()+" n'existe pas");
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

    public Formation findOne(int id) {
        Formation f = this.daoFormation.find(id);
        return f;
    }

    public List<Formation> findAll() {
        return daoFormation.findAll();
    }

    /**
     * vérif que formation existe
     * que le noeud n'existe pas dnas la formation
     */
    public void addBloc(int formationId, Bloc bloc) throws NotFoundObjectException, ItemExistInListException {
        Formation formation = daoFormation.find(formationId);
        if(formation == null) throw new NotFoundObjectException("formation " + formationId + " n'existe pas");

        if(!formation.getBlocs().contains(bloc)) {
            daoFormation.addBloc(formation, bloc);
        } else {
            throw new ItemExistInListException("Le bloc " + bloc.getCode() + " existe déjà dans la formation " + formation.getCode());
        }
    }

    /**
     * vérifier que la formation existe
     * que le noeud n'existe pas dnas la formation
     */
    public void removeBloc(int formationId, int blocId) throws NotFoundObjectException, ItemExistInListException {
        Formation formation = daoFormation.find(formationId);
        if(formation == null) throw new NotFoundObjectException("formation " + formationId+ " n'existe pas");

        Bloc bloc = daoBloc.find(blocId);
        if(bloc == null) throw new NotFoundObjectException("bloc "+ blocId + " pas trouvé");
        if(!formation.getBlocs().contains(bloc)) throw new NotFoundObjectException("bloc "+ blocId + " pas trouvé dans la formation");

        daoFormation.removeBloc(formation, bloc);

    }

    /**
     * vérif que formation existe
     * que le noeud n'existe pas dnas la formation
     */
    public void addOption(int formationId, Option option) throws NotFoundObjectException, ItemExistInListException {
        Formation formation = daoFormation.find(formationId);
        if(formation == null) throw new NotFoundObjectException("formation " + formationId + " n'existe pas");

        if(!formation.getOptions().contains(option)) {
            daoFormation.addOption(formation, option);
        } else {
            throw new ItemExistInListException("Le bloc " + option.getCode() + " existe déjà dans la formation " + formation.getCode());
        }
    }

    /**
     * vérifier que la formation existe
     * que le noeud n'existe pas dnas la formation
     */
    public void removeOption(int formationId, int optionId) throws NotFoundObjectException {
        Formation formation = daoFormation.find(formationId);
        if(formation == null) throw new NotFoundObjectException("formation " + formationId+ " n'existe pas");

        Option option = daoOption.find(optionId);
        if(option == null) throw new NotFoundObjectException("option, "+ optionId + " pas trouvé");
        if(!formation.getOptions().contains(option)) throw new NotFoundObjectException("option, "+ optionId + " pas trouvé dans la formation");

        daoFormation.removeOption(formation, option);
    }

    /**
     * vérif que formation existe
     * que le noeud n'existe pas dnas la formation
     */
    public void addUE(int formationId, UE ue) throws NotFoundObjectException, ItemExistInListException {
        Formation formation = daoFormation.find(formationId);
        if(formation == null) throw new NotFoundObjectException("formation " + formationId + " n'existe pas");

        if(!formation.getUEs().contains(ue)) {
            daoFormation.addUE(formation, ue);
        } else {
            throw new ItemExistInListException("L'ue " + ue.getCode() + " existe déjà dans la formation " + formation.getCode());
        }
    }

    /**
     * vérifier que la formation existe
     * que le noeud n'existe pas dnas la formation
     */
    public void removeUE(int formationId, int ueId) throws NotFoundObjectException {
        Formation formation = daoFormation.find(formationId);
        if(formation == null) throw new NotFoundObjectException("formation " + formationId+ " n'existe pas");

        UE ue = daoUe.find(ueId);
        if(ue == null) throw new NotFoundObjectException("ue, "+ ueId + " pas trouvé");

        System.err.println("Taille formation ! " + formation.getUEs().size());
        if(!formation.getUEs().contains(ue)) throw new NotFoundObjectException("ue, "+ ueId + " pas trouvé dans la formation");

        daoFormation.removeUE(formation, ue);
    }

}
