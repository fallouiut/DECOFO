package m2info.ter.decofo.controllers;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.exceptions.DecofoException;
import m2info.ter.decofo.exceptions.NotFoundObjectException;
import m2info.ter.decofo.manager.gestion.FormationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/formations")
public class FormationController {

    @Autowired
    FormationManager formationManager;

    /**
     * Créer une formation
     */
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createFormation(@RequestBody Formation formation) {
        Map <String, Object> result = new HashMap<>();

        try {
            this.formationManager.insert(formation);

            result.put("formationId", formation.getId());
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtenir detail d'une formation
     */
    @GetMapping("/read-one/{formationId}")
    public ResponseEntity<Formation> getOneFormation(@PathVariable("formationId") int formationId) {
        Map <String, Object> result = new HashMap<>();

        try {
            if (formationId < 0) throw new NotFoundObjectException("Mauvais ID saisit");
            Formation formation = formationManager.findOne(formationId);
            if(formation == null) throw new NotFoundObjectException("Formation n'existe pas");

            result.put("formation", formation);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (DecofoException e) {
            result.put("error", e.getMessage());
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            System.out.println(e.getMessage());
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
        Map <String, Object> result = new HashMap<>();

        try {
            List<Formation> formations = this.formationManager.findAll();

            result.put("formations", formations);
            return new ResponseEntity<>(formations, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update/{formationId}")
    public ResponseEntity<Object> updateFormation(@RequestBody Formation formation, @PathVariable("formationId") int formationId) {
        Map <String, Object> result = new HashMap<>();
        try {
            if (formationId < 0) throw new NotFoundObjectException("Mauvais ID saisit");
            formation.setId(formationId);
            this.formationManager.update(formation);

            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (DecofoException e) {
            result.put("error", e.getMessage());
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/delete/{formationId}")
    public ResponseEntity<Object> deleteFormation(@PathVariable("formationId") int formationId) {
        try {
            if (formationId < 0) throw new NotFoundObjectException("Mauvais ID saisit");
            this.formationManager.delete(formationId);

            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // création bloc
    @PostMapping("/create-bloc/{formationId}")
    public ResponseEntity<Map<String, Object>> createBlocOnFormation(@PathVariable("formationId") int formationId, @RequestBody Bloc bloc) {
        Map <String, Object> result = new HashMap<>();
        try {
            if (formationId < 0) throw new NotFoundObjectException("Mauvais ID saisit");
            this.formationManager.addBloc(formationId, bloc);

            return new ResponseEntity(null, HttpStatus.OK);
        } catch (DecofoException e) {
            result.put("error", e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // suppression bloc
    @GetMapping("/delete-bloc/")
    public ResponseEntity<Map<String, Object>> deleteBlocOnFormation(@RequestParam("formationId") int formationId, @RequestParam("blocId") int blocId) {
        Map <String, Object> result = new HashMap<>();
        try {
            if (formationId < 0) throw new NotFoundObjectException("Mauvais ID Formation saisit");
            if (blocId < 0) throw new NotFoundObjectException("Mauvais ID Bloc saisit");
            this.formationManager.removeBloc(formationId, blocId);

            return new ResponseEntity(null, HttpStatus.OK);
        } catch (DecofoException e) {
            result.put("error", e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
