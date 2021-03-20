package m2info.ter.decofo.controllers.auth;

import m2info.ter.decofo.classes.User;
import m2info.ter.decofo.exceptions.DecofoException;
import m2info.ter.decofo.manager.auth.AuthManager;
import m2info.ter.decofo.manager.auth.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class AuthController {

	@Autowired
    UserManager userManager;

	@Autowired
    AuthManager authManager;

	// Expiration fixée à 10 mins
	private static long TOKEN_EXPIRATION_TIME_IN_MILLIS = 10*60*1000;
	
	@GetMapping("/auth/{data}")
	public ResponseEntity<Map<String, Object>> getUserData(HttpRequest request, @PathVariable("data") String data) throws Exception {

        // déclare header et réponses
        Map<String, Object> body = new HashMap<String, Object>();
        HttpHeaders headers = new HttpHeaders();

        try {
            // décode données reçu de la requête
            User userEssai = authManager.decoder(data);

            // vérifie que l"utilisateur existe
            User userVerifie = userManager.findAndCheck(userEssai);

            // si user n'existe pas on, renvoie erreur
            if(userVerifie == null) {
                body.put("type","error");
                body.put("msg", "Vos identifiants sont incorrects");
            } else {
                // récupère le token
                String accessToken = Objects.requireNonNull(request.getHeaders().get("authorization")).get(1);

                // sinon on authentifie et on renvoie le token
                authManager.authentifier(userVerifie, "token");
                headers.set("Authorization", "Basic " + accessToken);
            }
        } catch (DecofoException e) {
            body.put("error", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(body, headers, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<Map<String, Object>>(null, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	

}
