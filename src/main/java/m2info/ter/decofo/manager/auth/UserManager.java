package m2info.ter.decofo.manager.auth;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import m2info.ter.decofo.classes.User;

@Service
public class UserManager {

	public User findAndCheck(User user) {
		// à compléter
		return new User("decofo@gmail.com", "motDePasse");
	}
}
