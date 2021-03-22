package m2info.ter.decofo.manager.auth;

import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.classes.User;
import m2info.ter.decofo.dao.DAOFormation;
import m2info.ter.decofo.dao.DAOUser;
import m2info.ter.decofo.exceptions.DecofoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesFilter {

    @Autowired
    DAOUser daoUser;

    @Autowired
    DAOFormation daoFormation;

    public void assertOwner(int formationId, int userId) throws DecofoException {
        User user = daoUser.find(userId);
        if(user == null) throw new DecofoException("Utilisateur " + userId + " introuvable");

        Formation formation = daoFormation.find(formationId);
        if(formation == null) throw new DecofoException("Formation " + formationId + " introuvable");

        boolean isOwner = formation.getOwner().equals(user);
        if(!isOwner) throw new DecofoException("Vous n'avez pas le droit de modifier la formation " + formationId);

    }

    public void assertVisitor(int formationId, int userId) throws DecofoException {
        User user = daoUser.find(userId);
        if(user == null) throw new DecofoException("Utilisateur " + userId + " introuvable");

        Formation formation = daoFormation.find(formationId);
        if(formation == null) throw new DecofoException("Formation " + formationId + " introuvable");

        boolean isVisitor = formation.getOwner().equals(user);
        if(!isVisitor) throw new DecofoException("Vous n'avez pas le droit de visiter la formation " + formationId);

    }

}
