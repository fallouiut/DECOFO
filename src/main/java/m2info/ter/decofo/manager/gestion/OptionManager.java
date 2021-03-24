package m2info.ter.decofo.manager.gestion;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.classes.Option;
import m2info.ter.decofo.classes.UE;
import m2info.ter.decofo.dao.DAOFormation;
import m2info.ter.decofo.dao.DAOOption;
import m2info.ter.decofo.dao.DAOUe;
import m2info.ter.decofo.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionManager implements Manager<Option> {

    @Autowired
    DAOFormation daoFormation;

    @Autowired
    DAOOption daoOption;

    @Autowired
    DAOUe daoUe;

    @Override
    public void insert(Option object) {
        daoOption.insert(object);
    }

    @Override
    public void update(Option object) throws Exception {
        Option option = this.daoOption.find(object.getId());
        if(option != null) {

            option.setCode(object.getCode());
            option.setIntitule(object.getIntitule());
            option.setCredits(object.getCredits());

            daoOption.update(option);
        } else {
            throw new NotFoundObjectException("Option doesn't exist");
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

    /**
     * vérif que bloc et ue existent
     * @param optionId
     * @param ueId
     */
    public void linkUE(int optionId, int ueId) throws DecofoException {
        // s'assurer que bloc existe
        Option option = daoOption.find(optionId);
        if(option == null) throw new NotFoundObjectException("option " + optionId + " n'existe pas");

        // s'assurer que ue existe
        UE ue = daoUe.find(ueId);
        if(ue == null) throw new NotFoundObjectException("ue " + ueId + " n'existe pas");

        // s'assurer ue.credits == option.credits
        if (option.getCredits() != ue.getCredits()) throw new DecofoException("Nombre ue option != nombre crédit ue");

        // s'assurer que ue existe dans le formation parent de bloc
        Formation ownerFormation = option.getFormationOwner();
        if(ownerFormation == null) throw new FormationParentNotFoundException("Cet option n'a pas de formaiton parent");
        boolean containedInformation = daoFormation.find(option.getFormationOwner().getId()).getUEs().contains(ue);
        if(!containedInformation) throw new NotFoundObjectException("ue " + ueId + " n'existe pas dans la formation parent");

        // vérifier que bloc ne contient pas deja UE
        if(!option.getUes().contains(ue)){
            daoOption.linkUE(option, ue);
        } else {
            throw new ItemExistInListException("L'ue " + ue.getCode() + " existe déjà dans l'option " + option.getCode());
        }
    }

    /**
     * vérif que bloc et ue existent
     * @param optionId
     * @param ueId
     */
    public void unlinkUE(int optionId, int ueId) throws NotFoundObjectException {
        Option option = daoOption.find(optionId);
        if(option == null) throw new NotFoundObjectException("option " + optionId + " n'existe pas");

        UE ue = daoUe.find(ueId);
        if(ue == null) throw new NotFoundObjectException("ue, "+ ueId + " pn'exite pas ");
        if(!option.getUes().contains(ue)) throw new NotFoundObjectException("ue, "+ ueId + " pn'exite pas dans le bloc " + optionId);

        daoOption.unlinkUE(option, ue);

        System.err.println("----------------------------------");
        System.err.println("Action: Option '" + option.getCode() + "' unlink avec ue '" + ue.getCode());
        System.err.println("Bloc contient à nouveau l'UE' ? " + daoOption.find(option.getId()).getUes().contains(ue));
        System.err.println("UE contient à nouveau l'option ? " + daoUe.find(ue.getId()).getOptions().contains(option));
    }


}
