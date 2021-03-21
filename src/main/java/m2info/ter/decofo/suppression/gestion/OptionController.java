package m2info.ter.decofo.suppression.gestion;

import m2info.ter.decofo.classes.Option;
import m2info.ter.decofo.exceptions.DecofoException;
import m2info.ter.decofo.exceptions.NotFoundObjectException;
import m2info.ter.decofo.manager.gestion.OptionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/options")
public class OptionController {

    @Autowired
    OptionManager optionManager;

    @GetMapping("/read-one/{optionId}")
    public ResponseEntity<Option> getOneOption(@PathVariable("optionId") int optionId) {
        Map <String, Object> result = new HashMap<>();
        try {
            if (optionId < 0) throw new NotFoundObjectException("Mauvais ID saisit");
            Option option = optionManager.findOne(optionId);
            if(option == null) throw new NotFoundObjectException("Option n'existe pas");

            result.put("option", option);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (DecofoException e) {
            result.put("error", e.getMessage());
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/update/{optionId}")
    public ResponseEntity<Object> updateOption(@RequestBody Option option, @PathVariable("optionId") int optionId) throws Exception {
        Map <String, Object> result = new HashMap<>();
        try {
            if (optionId < 0) throw new NotFoundObjectException("Mauvais ID saisit");
            option.setId(optionId);
            this.optionManager.update(option);

            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (DecofoException e) {
            result.put("error", e.getMessage());
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ajouter une ue
    @GetMapping("/link-ue/")
    public ResponseEntity<Map<String, Object>> linkUE(@RequestParam("optionId") int optionId, @RequestParam("ueId") int ueId) {
        Map <String, Object> result = new HashMap<>();
        try{
            if(optionId < 0) throw new NotFoundObjectException("Mauvais ID Option");
            if(ueId < 0)   throw new NotFoundObjectException("Mauvais ID UE");

            this.optionManager.linkUE(optionId, ueId);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (DecofoException e) {
            result.put("error", e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // enlever une ue
    @GetMapping("/unlink-ue/")
    public ResponseEntity<Map<String, Object>> unlinkUE(@RequestParam("optionId") int optionId, @RequestParam("ueId") int ueId){

        Map <String, Object> result = new HashMap<>();
        try{
            if(optionId < 0) throw new NotFoundObjectException("Mauvais ID Option");
            if(ueId < 0)   throw new NotFoundObjectException("Mauvais ID UE");

            this.optionManager.unlinkUE(optionId, ueId);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (DecofoException e) {
            result.put("error", e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
