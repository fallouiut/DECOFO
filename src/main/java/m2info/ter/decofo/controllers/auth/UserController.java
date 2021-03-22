package m2info.ter.decofo.controllers.auth;

import m2info.ter.decofo.classes.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public class UserController {

    // methode de modif du profil
    // récupérer email, ancienMdp, nouveau mdp, repeat mdp
    public ResponseEntity<Map<String, Object>> updateProfile(@RequestBody User userAModifier) {

        // retrouver user par email
        // changer mdp

        return null;
    }


}
