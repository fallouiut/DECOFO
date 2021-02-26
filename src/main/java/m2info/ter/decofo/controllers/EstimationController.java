package m2info.ter.decofo.controllers;

import m2info.ter.decofo.classes.Formation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estimation")
public class EstimationController {

    @GetMapping("/hetd/{formationId}")
    public ResponseEntity<Formation> deleteFormation(@PathVariable("formationId") int formationId) {

        return null;
    }
}
