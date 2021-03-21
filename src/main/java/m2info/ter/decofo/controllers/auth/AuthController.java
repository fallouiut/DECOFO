package m2info.ter.decofo.controllers.auth;

import m2info.ter.decofo.classes.AuthBean;
import m2info.ter.decofo.classes.User;
import m2info.ter.decofo.exceptions.DecofoException;
import m2info.ter.decofo.manager.auth.AuthManager;
import m2info.ter.decofo.manager.auth.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping
public class AuthController {

	@Autowired
    UserManager userManager;

	@Autowired
    AuthManager authManager;
	
	@PostMapping("/auth")
	public ResponseEntity<Map<String, Object>> getUserData(@RequestHeader(name = "Authorization", required = false) String authorization, @RequestBody AuthBean data) throws Exception {
        // déclare header et réponses
        Map<String, Object> body = new HashMap<String, Object>();
        HttpHeaders headers = new HttpHeaders();
        try {
            // décode données reçu de la requête
            User userEssai = authManager.decoder(data.getData());

            // vérifie que l"utilisateur existe (exception lancée dans le manager si erreur
            User userVerifie = userManager.findByEmailAndPassword(userEssai);

            // recup token
            String accessToken = authorization == null ? "" : authorization.split("Basic ")[0];

            // sinon on authentifie et on renvoie le token
            accessToken = authManager.authentifier(userVerifie, accessToken);

            // renvoie reponse
            headers.set(HttpHeaders.AUTHORIZATION, "Basic " + accessToken);
            return ResponseEntity.ok().headers(headers).build();

        } catch (DecofoException e) {
            body.put("error", e.getMessage());
            return new ResponseEntity(body, headers, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
        	e.printStackTrace();
            return new ResponseEntity(body, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

	}

}
