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

    @Autowired
    FormationManager formationManager;

    /**
     * Obtenir detail d'une formation
     */
    @GetMapping("/details/{formationId}")
    public ResponseEntity<Formation> getOneFormation(@PathVariable("formationId") int formationId) {
        System.err.println("-- details --");

        System.err.println("details ok");
        Formation formation = formationManager.findOne(formationId);

        if (formation != null) {
            System.err.println("Formation demandée trouvée");
            return new ResponseEntity(formation, HttpStatus.OK);
        } else {
            System.err.println("Formation demandée non trouvée");
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Afficher toutes les formations
     *
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<List<Formation>> getAllFormations() {
        System.err.println("-- all --");
        System.err.println("toutes formations demandées");
        return new ResponseEntity<>(this.formationManager.findAll(), HttpStatus.OK);
    }

    /**
     * Créer une formation
     */
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createFormation(@RequestBody Formation formation) {
        System.err.println("-- add --");
        System.err.println("Formation : " + formation.toString());
        this.formationManager.insert(formation);
        this.formationManager.findAll();

        return new ResponseEntity(null, HttpStatus.OK);
    }

    @PostMapping("/update/{formationId}")
    public ResponseEntity<Object> updateFormation(@RequestBody Formation formation, @PathVariable("formationId") int formationId) {
        System.err.println("-- update --");
        System.err.println("Updatinf formation " + formationId);
        formation.setId(formationId);
        this.formationManager.update(formation);
        this.formationManager.findAll();

        //this.formationManager.update(formation);
        return null;
    }

    @GetMapping("/delete/{formationId}")
    public ResponseEntity<Object> deleteFormation(@PathVariable("formationId") int formationId) {
        this.formationManager.delete(formationId);
        System.err.println("-- delete --");
        this.formationManager.findAll();

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
