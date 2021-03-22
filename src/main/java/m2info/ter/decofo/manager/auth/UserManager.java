package m2info.ter.decofo.manager.auth;

import java.util.HashMap;

import m2info.ter.decofo.dao.DAOUser;
import m2info.ter.decofo.exceptions.DecofoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import m2info.ter.decofo.classes.User;

@Service
public class UserManager {

	@Autowired
	DAOUser daoUser;

	public User findByEmailAndPassword(User user) throws Exception {
		// String password = service.crypt(password);
		User userFound = daoUser.findByEmailAndPassword(user);
		if(user == null) throw new DecofoException("Vos Identifiants sont incorrects");

		return userFound;
	}

	public void updatePassword(User userTrial, String ancientMdp, String newPassword) throws DecofoException {
		User userFound = daoUser.find(userTrial.getId());
		if (userFound == null) throw new DecofoException("User pas trouvé ");
		if(userFound.getMotDePasse().equals(ancientMdp)){
			userFound.setMotDePasse(newPassword);
			daoUser.update(userFound);
		} else {
			throw new DecofoException("Les deux mots de passes ne correspondent pas");
		}
	}

	public User findByEmail(String email) throws DecofoException {
		User found = daoUser.findByEmail(email);
		if(found == null) throw new DecofoException("Utilisateur '" + email + "'" + " pas trouvé");
		return found;
	}

}
