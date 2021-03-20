package m2info.ter.decofo.manager.auth;

import m2info.ter.decofo.classes.User;
import m2info.ter.decofo.exceptions.AuthException;
import m2info.ter.decofo.exceptions.DecofoException;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

@Service
public class AuthManager {

    /**
     * User authentifiés (Token, UserId)
     */
    public HashMap<String, Integer> authentificatedUsers;

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
    public void authentifier(User userVerifie, String accessToken) throws Exception {

        boolean tokenFound = authentificatedUsers.containsKey(accessToken);

        // Si le token existe
        // soit expiré, soit déjà connecté
        if(!tokenFound) {
            // S'il a expiré, on rafraichit le token
            if(isTokenExpired(accessToken)) {
                // génération d'un token
                accessToken = generateToken();

                // On stocke le token pour plus tard
                authentificatedUsers.put(accessToken, userVerifie.getId());
            }
            // si token n'existe pas
            // on authentifie
        } else {
            // génération d'un token
            accessToken = generateToken();

            // On stocke le token pour plus tard
            authentificatedUsers.put(accessToken, userVerifie.getId());
        }
    }


}
