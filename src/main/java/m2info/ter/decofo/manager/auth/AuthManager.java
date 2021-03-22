package m2info.ter.decofo.manager.auth;

import m2info.ter.decofo.classes.User;
import m2info.ter.decofo.dao.DAOUser;
import m2info.ter.decofo.exceptions.AuthException;
import m2info.ter.decofo.exceptions.DecofoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

@Service
public class AuthManager {

    @Autowired
    DAOUser daoUser;

    /**
     * User authentifiés (Token, UserId)
     */
    public HashMap<String, Integer> authentificatedUsers = new HashMap<String, Integer>();

    // Expiration fixée à 10 mins
    private static long TOKEN_EXPIRATION_TIME_IN_MILLIS = 10*60*1000;

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

    public User decoder(String dataBase64) throws Exception {
        // Données non-vide attendues
        if(dataBase64.isEmpty()) {
            throw new DecofoException("Aucune donnée trouvée");
        }

        // Données d'authentification brutes après décryption
        String RawData = new String(Base64.getDecoder().decode(dataBase64));

        // format de données attendu email:password
        if(!RawData.matches("^(.+):(.+)$")) {
            throw new DecofoException("Données ne correspondent pas email:password");
        }

        // découpage des données d'authentification
        String email = RawData.split(":")[0];
        String password = RawData.split(":")[1];

        return new User(email, password);
    }

    /**
     *
     * @return
     */
    public String authentifier(User userVerifie, String accessToken){

        boolean tokenFound = authentificatedUsers.containsKey(accessToken);

        // Si le token existe
        // soit expiré, soit déjà connecté
        if(tokenFound) {
            // S'il a expiré, on rafraichit le token sinon on renvoie le token courant
            return refreshToken(userVerifie, accessToken);
            
            // si token n'existe pas
            // on authentifie
        } else {
            // génération d'un token
            accessToken = generateToken();

            // On stocke le token pour plus tard
            authentificatedUsers.put(accessToken, userVerifie.getId());
            
            // On renvoie le nouveau token
            return accessToken;
        }
    }
    
    public boolean logout(String accessToken) throws Exception{
    	boolean isTokenExist = authentificatedUsers.containsKey(accessToken);
    	
    	// Si le token existe on peut le supprimer et renvoyer true pour indiquer la destruction de la session
    	if(isTokenExist) {
    		authentificatedUsers.remove(accessToken);
    		return true;
    	}
    	throw new DecofoException("Vous n'êtes pas authentifié");
    }
    
    /**
     * Rafraichit le token s'il a expiré sinon renvoie le token courant
     * @return
     */
    public String refreshToken(User user, String accessToken) {
        if(isTokenExpired(accessToken)) {
        	// On supprime le token
        	authentificatedUsers.remove(accessToken);
        	
            // génération d'un token
            accessToken = generateToken();

            // On stocke le token pour plus tard
            authentificatedUsers.put(accessToken, user.getId());
            
            // On renvoie le nouveau token
            return accessToken;
        }
        
        return accessToken;
    }

    public User getAuthentifiedUserId(String token) throws DecofoException {
        if(token == null) throw  new DecofoException("Non authentifié");
        int userId = this.authentificatedUsers.get(token);
        User user = daoUser.find(userId);
        if(user == null) throw new DecofoException("User non identifié");
        return user;
    }


}
