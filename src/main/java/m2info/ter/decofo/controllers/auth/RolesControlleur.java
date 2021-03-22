package m2info.ter.decofo.controllers.auth;

import m2info.ter.decofo.exceptions.DecofoException;
import m2info.ter.decofo.manager.auth.AuthManager;
import m2info.ter.decofo.manager.auth.RolesFilter;
import m2info.ter.decofo.manager.auth.RolesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/roles")
public class RolesControlleur {

    @Autowired
    RolesManager rolesManager;

    @Autowired
    RolesFilter rolesFilter;

    @Autowired
    AuthManager authManager;

    @GetMapping("/add-visitor")
    public ResponseEntity<Map<String, Object>> addVisitor(@RequestParam("newVisitorId") int visitorId, @RequestParam("formationId") int formationId, @RequestParam(name = "accessToken", required = false) String token) {
        Map <String, Object> result = new HashMap<>();

        try {
            rolesFilter.assertOwner(formationId, authManager.getAuthentifiedUserId(token).getId());
            this.rolesManager.addVisitor(visitorId, formationId);

            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (DecofoException e) {
            result.put("error", e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/remove-visitor")
    public ResponseEntity<Map<String, Object>> removeVisitor(@RequestParam("newVisitorId") int visitorId, @RequestParam("formationId") int formationId, @RequestParam(name = "accessToken", required = false) String token) {
        Map <String, Object> result = new HashMap<>();

        try {
            rolesFilter.assertOwner(formationId, authManager.getAuthentifiedUserId(token).getId());
            this.rolesManager.removeVisitor(visitorId, formationId);

            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (DecofoException e) {
            result.put("error", e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
