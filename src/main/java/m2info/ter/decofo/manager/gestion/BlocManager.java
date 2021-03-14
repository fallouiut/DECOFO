package m2info.ter.decofo.manager.gestion;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.classes.Option;
import m2info.ter.decofo.classes.UE;
import m2info.ter.decofo.dao.DAOBloc;
import m2info.ter.decofo.dao.DAOFormation;
import m2info.ter.decofo.dao.DAOOption;
import m2info.ter.decofo.dao.DAOUe;
import m2info.ter.decofo.exceptions.FormationParentNotFoundException;
import m2info.ter.decofo.exceptions.ItemExistInListException;
import m2info.ter.decofo.exceptions.NotFoundObjectException;
import m2info.ter.decofo.exceptions.ServerErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlocManager {

    @Autowired
    DAOFormation daoFormation;

    @Autowired
    DAOBloc daoBloc;

    @Autowired
    DAOUe daoUe;

    @Autowired
    DAOOption daoOption;

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

    public Bloc findOne(int id)  {
        Bloc b = daoBloc.find(id);
        return b;
    }

    public List<Bloc> findAll() {
        return daoBloc.findAll();
    }

    /**
     * vérif que bloc et ue existent
     * @param blocId
     * @param ueId
     */
    public void linkUE(int blocId, int ueId) throws NotFoundObjectException, ItemExistInListException, FormationParentNotFoundException {
        // s'assurer que bloc existe
        Bloc bloc = daoBloc.find(blocId);
        if(bloc == null) throw new NotFoundObjectException("bloc " + blocId + " n'existe pas");

        // s'assurer que ue existe
        UE ue = daoUe.find(ueId);
        if(ue == null) throw new NotFoundObjectException("ue " + ueId + " n'existe pas");

        // s'assurer que ue existe dans le formation parent de bloc
        Formation ownerFormation = bloc.getFormationOwner();
        if(ownerFormation == null) throw new FormationParentNotFoundException("Ce bloc n'a pas de formaiton parent");
        boolean containedInformation = daoFormation.find(bloc.getFormationOwner().getId()).getUEs().contains(ue);
        if(!containedInformation) throw new NotFoundObjectException("ue " + ueId + " n'existe pas dans la formation parent");

        // vérifier que bloc ne contient pas deja UE
        if(!bloc.getUes().contains(ue)){
            daoBloc.linkUE(bloc, ue);
        } else {
            throw new ItemExistInListException("L'ue " + ue.getCode() + " existe déjà dans le bloc " + bloc.getCode());
        }
    }

    /**
     * vérif que bloc et ue existent
     * @param blocId
     * @param ueId
     */
    public void unlinkUE(int blocId, int ueId) throws NotFoundObjectException {
        Bloc bloc = daoBloc.find(blocId);
        if(bloc == null) throw new NotFoundObjectException("bloc " + blocId + " n'existe pas");

        UE ue = daoUe.find(ueId);
        if(ue == null) throw new NotFoundObjectException("ue, "+ ueId + " pn'exite pas ");
        if(!bloc.getUes().contains(ue)) throw new NotFoundObjectException("ue, "+ ueId + " pn'exite pas dans le bloc " + blocId);

        daoBloc.unlinkUE(bloc, ue);
    }

    /**
     * vérif que bloc et  option existent
     * @param blocId
     * @param  optionId
     */
    public void linkOption(int blocId, int optionId) throws NotFoundObjectException, FormationParentNotFoundException, ItemExistInListException {
        // s'assurer que bloc existe
        Bloc bloc = daoBloc.find(blocId);
        if(bloc == null) throw new NotFoundObjectException("bloc " + blocId + " n'existe pas");

        // s'assurer que option existe
        Option option = daoOption.find(optionId);
        if(option == null) throw new NotFoundObjectException("option " + optionId + " n'existe pas");

        // s'assurer que optioon existe dans le formation parent de bloc
        Formation ownerFormation = bloc.getFormationOwner();
        if(ownerFormation == null) throw new FormationParentNotFoundException("Ce bloc n'a pas de formaiton parent");
        boolean containedInformation = daoFormation.find(bloc.getFormationOwner().getId()).getOptions().contains(option);
        if(!containedInformation) throw new NotFoundObjectException("option " + optionId + " n'existe pas dans la formation parent");

        // vérifier que bloc ne contient pas deja UE
        if(!bloc.getOptions().contains(option)){
            daoBloc.linkOption(bloc, option);
        } else {
            throw new ItemExistInListException("L'option " + option.getCode() + " existe déjà dans le bloc " + bloc.getCode());
        }
    }

    /**
     * vérif que bloc et  option existent
     * @param blocId
     * @param  optionId
     */
    public void unlinkOption(int blocId, int optionId) throws NotFoundObjectException {
        Bloc bloc = daoBloc.find(blocId);
        if(bloc == null) throw new NotFoundObjectException("bloc " + blocId + " n'existe pas");

        Option option = daoOption.find(optionId);
        if(option == null) throw new NotFoundObjectException("option, "+ optionId + " pn'exite pas ");
        if(!bloc.getOptions().contains(option)) throw new NotFoundObjectException("option, "+ optionId + " pn'exite pas dans le bloc " + blocId);

        daoBloc.unlinkOption(bloc, option);
    }
}
