package m2info.ter.decofo.controllers;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import m2info.ter.decofo.classes.User;
import m2info.ter.decofo.managers.UserManager;

@RestController
public class AuthController {
	
	@Autowired
	UserManager manager;
	@Autowired
	public HashMap<String,String> authentificatedUsers; 
	
	// Expiration fixée à 10 mins
	private static long TOKEN_EXPIRATION_TIME_IN_MILLIS = 10*60*1000;
	
	@GetMapping("/auth/{data}")
	public ResponseEntity<Map<String, Object>> getUserData(@PathVariable("data") String data) {
		Map<String, Object> body = new HashMap<String, Object>();
		HttpHeaders headers = new HttpHeaders();
		
		// Données non-vide attendues
		if(data.isEmpty()) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// Données d'authentification brutes après décryption
		String RawData = new String(Base64.getDecoder().decode(data));
		
		// format de données attendu email:password
		if(!RawData.matches("^(.+):(.+)$")) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// découpage des données d'authentification
		String email = RawData.split(":")[0];
		String password = RawData.split(":")[1];
		
		boolean isValid = manager.validateUserAccount(email,password);
		
		if(!isValid) {
			body.put("type","error");
			body.put("msg", "Vos identifiants sont incorrects");
		}else {
			String accessToken = authentificatedUsers.get(email);
			
			// Si le token existe
			if(accessToken != null) {				
				// S'il a expiré, on rafraichit le token
				if(isTokenExpired(accessToken)) {
					// génération d'un token
					accessToken = generateToken();
					
					// On stocke le token pour plus tard
					authentificatedUsers.put(email,accessToken);
					
					body.put("type","error");
					body.put("msg", "Authentification réussie");
					
				}else {
					body.put("type","error");
					body.put("msg", "Vous êtes déjà authentifié");
				}
				headers.set("Authorization", "Basic "+accessToken);
			}else {
				// génération d'un token
				accessToken = generateToken();
				
				// On stocke le token pour plus tard
				authentificatedUsers.put(email,accessToken);
				
				body.put("type","error");
				body.put("msg", "Authentification réussie");
				
				headers.set("Authorization", "Basic "+accessToken);
			}
		}
		
		
		return new ResponseEntity<Map<String, Object>>(body,headers,HttpStatus.OK);
	}
	
	private boolean isTokenExpired(String accessToken) {
		long now = System.currentTimeMillis();
		long timestamp = Long.parseLong(accessToken.split(":")[1]);
		
		if(now-timestamp >= TOKEN_EXPIRATION_TIME_IN_MILLIS) {
			return true;
		}
		
		return false;
	}
	
	private String generateToken() {
		return UUID.randomUUID().toString()+":"+System.currentTimeMillis();
	}
	
	
	
}
