package m2info.ter.decofo.controllers;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.exceptions.DecofoException;
import m2info.ter.decofo.managers.BlocManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/blocs")
public class BlocController {

    @Autowired
    BlocManager blocManager;

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

    // ajouter une ue
    @GetMapping("/link-ue/")
    public ResponseEntity<Map<String, Object>> linkUE(@RequestParam("blocId") int blocId, @RequestParam("ueId") int ueId) {
        Map <String, Object> result = new HashMap<>();
        try{
            if(blocId < 0 ) throw new Exception("blocId < 0");
            if(ueId < 0 ) throw new Exception("ueId < 0");

            this.blocManager.linkUE(blocId,ueId);
            result.put("success", true);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (DecofoException e) {
            result.put("success", false);
            result.put("error", e.getMessage());

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Erreur FormationController.createBloc()");
            result.put("error", "Server error");
            System.err.println(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // enlever une ue
    @GetMapping("/unlink-ue/")
    public ResponseEntity<Map<String, Object>> unlinkUE(@RequestParam("blocId") int blocId, @RequestParam("ueId") int ueId){
        Map <String, Object> result = new HashMap<>();

        try{
            if(blocId < 0 ) throw new Exception("blocId < 0");
            if(ueId < 0 ) throw new Exception("ueId < 0");

            this.blocManager.unlinkUE(blocId,ueId);

            result.put("success", true);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (DecofoException e) {
            result.put("success", false);
            result.put("error", e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Erreur FormationController.createBloc()");
            result.put("error", "Server error");
            System.err.println(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // ajouter une option
    @GetMapping("/link-option/")
    public ResponseEntity<Map<String, Object>> linkOption(@RequestParam("blocId") int blocId, @RequestParam("optionId") int optionId) {
        Map <String, Object> result = new HashMap<>();
        try{
            if(blocId < 0 ) throw new Exception("blocId < 0");
            if(optionId < 0 ) throw new Exception("ueId < 0");

            this.blocManager.linkOption(blocId,optionId);
            result.put("success", true);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (DecofoException e) {
            result.put("success", false);
            result.put("error", e.getMessage());

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Erreur FormationController.createBloc()");
            result.put("error", "Server error");
            System.err.println(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // enlever une option
    @GetMapping("/unlink-option/")
    public ResponseEntity<Map<String, Object>> unlinkOption(@RequestParam("blocId") int blocId, @RequestParam("optionId") int optionId){
        Map <String, Object> result = new HashMap<>();

        try{
            if(blocId < 0 ) throw new Exception("blocId < 0");
            if(optionId < 0 ) throw new Exception("ueId < 0");

            this.blocManager.unlinkOption(blocId,optionId);

            result.put("success", true);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (DecofoException e) {
            result.put("success", false);
            result.put("error", e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println("Erreur FormationController.createBloc()");
            result.put("error", "Server error");
            System.err.println(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
