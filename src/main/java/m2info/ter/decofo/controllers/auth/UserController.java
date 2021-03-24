package m2info.ter.decofo.controllers.auth;

import m2info.ter.decofo.classes.PasswordChange;
import m2info.ter.decofo.classes.User;
import m2info.ter.decofo.exceptions.DecofoException;
import m2info.ter.decofo.manager.auth.AuthManager;
import m2info.ter.decofo.manager.auth.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserManager userManager;

    @Autowired
    AuthManager authManager;

    @PostMapping("/update")
    public ResponseEntity<Map<String, Object>> updateMdp(@RequestParam(name = "accessToken") String accessToken, @RequestBody PasswordChange passwordChange) {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            System.err.println("ancien: " + passwordChange.getAncienMdp());
            System.err.println("nv: " + passwordChange.getNvMdp());

            User user = authManager.getAuthentifiedUserId(accessToken);
            userManager.updatePassword(user, passwordChange.getAncienMdp(), passwordChange.getNvMdp());
            return new ResponseEntity(null, HttpStatus.OK);

        } catch (DecofoException decofoException) {
            result.put("error", decofoException.getMessage());
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/read-one/{userEmail}")
    public ResponseEntity<Map<String, Object>> updateMdp(@PathVariable("userEmail") String userEmail) {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            User user = userManager.findByEmail(userEmail);
            result.put("user", user);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (DecofoException decofoException) {
            result.put("error", decofoException.getMessage());
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
