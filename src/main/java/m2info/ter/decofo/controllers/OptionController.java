package m2info.ter.decofo.controllers;

import m2info.ter.decofo.classes.Option;
import m2info.ter.decofo.exceptions.DecofoException;
import m2info.ter.decofo.managers.OptionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/options")
public class OptionController {

    @Autowired
    OptionManager optionManager;

    @GetMapping("/read-one/{optionId}")
    public ResponseEntity<Option> getOneOption(@PathVariable("optionId") int optionId) {

        Option option = optionManager.findOne(optionId);

        if (option != null) {
            return new ResponseEntity(option, HttpStatus.OK);
        } else {
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update/{optionId}")
    public ResponseEntity<Object> updateOption(@RequestBody Option option, @PathVariable("optionId") int optionId) throws Exception {
        option.setId(optionId);
        this.optionManager.update(option);
        return null;
    }

    @GetMapping("/delete/{optionId}")
    public ResponseEntity<Object> deleteOption(@PathVariable("optionId") int optionId) {
        this.optionManager.delete(optionId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


    // ajouter une ue
    @GetMapping("/link-ue/")
    public ResponseEntity<Map<String, Object>> linkUE(@RequestParam("optionId") int optionId, @RequestParam("ueId") int ueId) {
        Map <String, Object> result = new HashMap<>();
        try{
            if(optionId < 0 ) throw new Exception("blocId < 0");
            if(ueId < 0 ) throw new Exception("ueId < 0");

            this.optionManager.linkUE(optionId,ueId);
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
    public ResponseEntity<Map<String, Object>> unlinkUE(@RequestParam("optionId") int optionId, @RequestParam("ueId") int ueId){
        Map <String, Object> result = new HashMap<>();

        try{
            if(optionId < 0 ) throw new Exception("blocId < 0");
            if(ueId < 0 ) throw new Exception("ueId < 0");

            this.optionManager.unlinkUE(optionId,ueId);

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
