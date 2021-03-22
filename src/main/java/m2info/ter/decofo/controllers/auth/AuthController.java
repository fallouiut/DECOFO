package m2info.ter.decofo.controllers.auth;

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

import m2info.ter.decofo.classes.User;
import m2info.ter.decofo.exceptions.DecofoException;
import m2info.ter.decofo.manager.auth.AuthManager;
import m2info.ter.decofo.manager.auth.UserManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin()
@RequestMapping
public class AuthController {

	@Autowired
    UserManager userManager;

	@Autowired
    AuthManager authManager;
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestHeader(name = "Authorization") String authorization, @RequestParam(name = "accessToken", required = false) String token) throws Exception {
        // déclare header et réponses
        Map<String, Object> body = new HashMap<String, Object>();
        HttpHeaders headers = new HttpHeaders();
        try {
        	String encryptedData = authorization.split("Basic ")[1];
        	
            // décode données reçu de la requête
            User userEssai = authManager.decoder(encryptedData);

            // vérifie que l"utilisateur existe (exception lancée dans le manager si erreur
            User userVerifie = userManager.findByEmailAndPassword(userEssai);

            if(userVerifie == null) throw new DecofoException("Vos identifiants sont incorrects");

            // recup token
            String accessToken = token == null ? "" : token;

            // sinon on authentifie et on renvoie le token
            accessToken = authManager.authentifier(userVerifie, accessToken);

            // renvoie reponse
            body.put("accessToken", accessToken);
            body.put("user", userVerifie);
            return ResponseEntity.ok().body(body);

        } catch (DecofoException e) {
            body.put("error", e.getMessage());
            return new ResponseEntity(body, headers, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
        	e.printStackTrace();
            return new ResponseEntity(body, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }

	}
	
	@GetMapping("/logout")
	public ResponseEntity<Map<String, Object>> logout(@RequestParam(name = "accessToken") String accessToken){
        // déclare header et réponses
        Map<String, Object> body = new HashMap<String, Object>();
        HttpHeaders headers = new HttpHeaders();
        
        try {
			if(authManager.logout(accessToken)) {
				 return ResponseEntity.ok().body(body);
			}
		} catch (DecofoException e) {
			return new ResponseEntity<Map<String,Object>>(body,headers, HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new ResponseEntity(body, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        
	}

}
