package m2info.ter.decofo.controllers;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.managers.BlocManager;
import m2info.ter.decofo.managers.FormationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blocs")
public class BlocController {

    @Autowired
    BlocManager blocManager;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createBloc(@RequestBody Bloc bloc) {

        System.err.println("Adding bloc + " + bloc.toString());
        this.blocManager.insert(bloc);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/read-one/{blocId}")
    public ResponseEntity<Bloc> getBlocDetails(@PathVariable("blocId") int blocId) {
        try {
            Bloc b = this.blocManager.findOne(blocId);
            return new ResponseEntity<>(b, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Erreur BlocController.getBlocDetails()");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/read-all")
    public ResponseEntity<List<Bloc>> getAllFormations() {
        return new ResponseEntity<List<Bloc>>(this.blocManager.findAll(), HttpStatus.OK);
    }

    @PostMapping("/update/{blocId}")
    public ResponseEntity<Map<String, Object>> updateBloc(@RequestBody Bloc bloc, @PathVariable("blocId") int blocId) {
        System.err.println("--- Update ---");
        try {
            if(blocId > 0) {
                bloc.setId(blocId);
                this.blocManager.update(bloc);
                return new ResponseEntity<>(null, HttpStatus.OK);
            } else {
                throw new Exception("bloc id < 0");
            }
        } catch (Exception e) {
            System.err.println("Erreur");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/delete/{blocId}")
    public ResponseEntity<Map<String, Object>> deleteBloc(@PathVariable("blocId") int blocId) {
        try {
            if(blocId > 0) {
                this.blocManager.delete(blocId);
                return new ResponseEntity<>(null, HttpStatus.OK);
            } else {
                throw new Exception("bloc id < 0");
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // ajouter ue (qui exite dans la formation=)
    // enlever UE

    // ajouter option (qui exite dans la formation=)
    // enlever option
}
