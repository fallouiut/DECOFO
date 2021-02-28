package m2info.ter.decofo.controllers;

import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.managers.FormationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/formations")
public class FormationController {

    // ajout UE
    // suppression UE

    // ajout Bloc
    // suppression Bloc

    // ajout option
    // suppression option

    @Autowired
    FormationManager formationManager;

    /**
     * Créer une formation
     */
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createFormation(@RequestBody Formation formation) {
        try {
            this.formationManager.insert(formation);

            System.err.println("Création : " + formation.toString());

            return new ResponseEntity(null, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Erreur FormationController.createFormation()");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtenir detail d'une formation
     */
    @GetMapping("/read-one/{formationId}")
    public ResponseEntity<Formation> getOneFormation(@PathVariable("formationId") int formationId) {
        try {
            if(formationId < 0) throw new Exception("formationId < 0");
            Formation formation = formationManager.findOne(formationId);

            System.err.println("Read formation: " + formation.toString());

            return new ResponseEntity(formation, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Erreur FormationController.getOneFormation()");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Afficher toutes les formations
     *
     * @return
     */
    @GetMapping("/read-all")
    public ResponseEntity<List<Formation>> getAllFormations() {
        return new ResponseEntity<>(this.formationManager.findAll(), HttpStatus.OK);
    }

    @PostMapping("/update/{formationId}")
    public ResponseEntity<Object> updateFormation(@RequestBody Formation formation, @PathVariable("formationId") int formationId) {
        try {
            if(formationId < 0) throw new Exception("formationId < 0");
            formation.setId(formationId);
            this.formationManager.update(formation);

            System.err.println("Update formation: " + formation.toString());

            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Erreur FormationController.updateFormation()");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/delete/{formationId}")
    public ResponseEntity<Object> deleteFormation(@PathVariable("formationId") int formationId) {
        try {
            if(formationId < 0) throw new Exception("formationId < 0");
            this.formationManager.delete(formationId);

            System.err.println("delete formation: " + formationId);

            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Erreur FormationController.deleteFormation()");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
