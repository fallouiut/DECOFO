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
		// à compléter
		//return new User("decofo@gmail.com", "motDePasse");
	}

	public void updatePassword(String userTrial, String newPassword) {
		// vérifier que user existe bien avec ce mdp puis changer
	}
}
