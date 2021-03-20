package m2info.ter.decofo.controllers;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.classes.Option;
import m2info.ter.decofo.classes.UE;
import m2info.ter.decofo.exceptions.DecofoException;
import m2info.ter.decofo.manager.gestion.FormationManager;
import m2info.ter.decofo.manager.hetd.EstimationHETD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/estimation")
public class EstimationController {

    @Autowired
    EstimationHETD estimationHETD;

    @Autowired
    FormationManager formationManager;

    @GetMapping("/hetd/{formationId}")
    public ResponseEntity<Formation> deleteFormation(@PathVariable("formationId") int formationId) {
        Map<String, Object> result = new HashMap<>();

        try {
            if(formationId < 0) throw new DecofoException("Numéro de formation n'existe pas");
            Formation formation = formationManager.findOne(formationId);
            if(formation == null) throw new DecofoException("Formation n'existe pas");

            estimationHETD.calculerHETD(formation);
            formationManager.update(estimationHETD.getFormation());

            result.put(String.valueOf(formation.getId()), formation.getCout());
            for(Bloc b: formation.getBlocs())
                result.put(String.valueOf(b.getId()), b.getCout());
            for(Option o: formation.getOptions())
                result.put(String.valueOf(o.getId()), o.getCout());
            for(UE ue: formation.getUEs())
                result.put(String.valueOf(ue.getId()), ue.getCout());

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
