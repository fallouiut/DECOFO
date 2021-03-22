package m2info.ter.decofo.manager.auth;

import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.classes.User;
import m2info.ter.decofo.dao.DAOFormation;
import m2info.ter.decofo.dao.DAOUser;
import m2info.ter.decofo.exceptions.DecofoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class RolesManager {

    @Autowired
    DAOUser daoUser;

    @Autowired
    DAOFormation daoFormation;

    public void addVisitor(int userId, int formationId) throws DecofoException {

        User user = daoUser.find(userId);
        if(user == null) throw new DecofoException("User '" + user.getEmail() + "' n'existe pas");

        Formation formation = daoFormation.find(formationId);
        if(formation == null) throw new DecofoException("Formation " + formationId + " n'existe pas");

        if(!user.getVisitedFormations().contains(formation)) {
            user.addVisitedFormation(formation);
            daoUser.update(user);
        } else {
            throw new DecofoException("User '" + user.getEmail() + "' est déjà visiteur de la formation " + formationId);
        }
        
    }

    public void removeVisitor(int userId, int formationId) throws DecofoException {

        User user = daoUser.find(userId);
        if(user == null) throw new DecofoException("User '" + user.getEmail() + "' n'existe pas");

        Formation formation = daoFormation.find(formationId);
        if(formation == null) throw new DecofoException("Formation " + formationId + " n'existe pas");

        if(user.getVisitedFormations().contains(formation)) {
            daoUser.unlinkVisitedFormation(user, formation);
        } else {
            throw new DecofoException("User " + user.getEmail() + "' n'est pas visiteur de la formation " + formationId);
        }

    }

}
