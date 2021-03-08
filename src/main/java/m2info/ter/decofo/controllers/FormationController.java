package m2info.ter.decofo.controllers;

import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.exceptions.NotFoundObjectException;
import m2info.ter.decofo.managers.FormationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/formations")
public class FormationController {

    @Autowired
    FormationManager formationManager;

    // ajout Bloc
    @GetMapping("/add-bloc")
    public ResponseEntity<Map<String, Object>> addBloc(@RequestParam("formationId") int formationId, @RequestParam("blocId") int blocId) {

        Map<String, Object> map = new HashMap<>();

        try {
            this.formationManager.linkBloc(formationId, blocId);
            map.put("success", true);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (NotFoundObjectException e) {
            map.put("success", false);
            map.put("error", e.getMessage());
            System.err.println(e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // suppression Bloc
    @GetMapping("/remove-bloc")
    public ResponseEntity<Map<String, Object>> removeBloc(@RequestParam("formationId") int formationId, @RequestParam("blocId") int blocId) {

        Map<String, Object> map = new HashMap<>();

        try {
            this.formationManager.unlinkBloc(formationId, blocId);
            map.put("success", true);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (NotFoundObjectException e) {
            System.err.println(e.getMessage());
            map.put("success", false);
            map.put("error", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ajout UE
    // suppression UE

    // ajout option
    // suppression option

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
            if (formationId < 0) throw new Exception("formationId < 0");
            Formation formation = formationManager.findOne(formationId);
            if(formation == null) throw new Exception("Formation n'existe pas");

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
        List<Formation> formations =this.formationManager.findAll();
        System.err.println("Size: " + formations.size());
        return new ResponseEntity<>(formations, HttpStatus.OK);
    }

    @PostMapping("/update/{formationId}")
    public ResponseEntity<Object> updateFormation(@RequestBody Formation formation, @PathVariable("formationId") int formationId) {
        try {
            if (formationId < 0) throw new Exception("formationId < 0");
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
            if (formationId < 0) throw new Exception("formationId < 0");
            this.formationManager.delete(formationId);

            System.err.println("delete formation: " + formationId);

            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Erreur FormationController.deleteFormation()");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
