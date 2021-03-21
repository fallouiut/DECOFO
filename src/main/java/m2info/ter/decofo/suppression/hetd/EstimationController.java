package m2info.ter.decofo.suppression.hetd;

import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.exceptions.DecofoException;
import m2info.ter.decofo.manager.gestion.FormationManager;
import m2info.ter.decofo.manager.hetd.EstimationHETD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hetd")
public class EstimationController {

    @Autowired
    EstimationHETD estimationHETD;

    @Autowired
    FormationManager formationManager;

    @GetMapping("/{formationId}")
    public ResponseEntity<Map<String, Formation>> deleteFormation(@PathVariable("formationId") int formationId) {
        Map<String, Object> result = new HashMap<>();

        try {
            if(formationId < 0) throw new DecofoException("Numéro de formation n'existe pas");
            Formation formation = formationManager.findOne(formationId);
            if(formation == null) throw new DecofoException("Formation n'existe pas");

            estimationHETD.calculerHETD(formation);
            formationManager.update(estimationHETD.getFormation());

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
}
