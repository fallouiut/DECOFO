package m2info.ter.decofo.controllers.gestion;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.exceptions.DecofoException;
import m2info.ter.decofo.exceptions.NotFoundObjectException;
import m2info.ter.decofo.manager.gestion.BlocManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/blocs")
public class BlocController {

    @Autowired
    BlocManager blocManager;

    @GetMapping("/read-one/{blocId}")
    public ResponseEntity<Bloc> getBlocDetails(@PathVariable("blocId") int blocId) {
        Map <String, Object> result = new HashMap<>();

        try {
            if (blocId < 0) throw new NotFoundObjectException("Mauvais ID saisit");
            Bloc bloc = blocManager.findOne(blocId);
            if(bloc == null) throw new NotFoundObjectException("Bloc n'existe pas");

            result.put("bloc", bloc);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (DecofoException e) {
            result.put("error", e.getMessage());
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update/{blocId}")
    public ResponseEntity<Map<String, Object>> updateBloc(@RequestBody Bloc bloc, @PathVariable("blocId") int blocId) {
        Map <String, Object> result = new HashMap<>();
        try {
            if (blocId < 0) throw new NotFoundObjectException("Mauvais ID saisit");
            bloc.setId(blocId);
            this.blocManager.update(bloc);

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
    public ResponseEntity<Map<String, Object>> linkUE(@RequestParam("blocId") int blocId, @RequestParam("ueId") int ueId) {
        Map <String, Object> result = new HashMap<>();
        try{
            if(blocId < 0) throw new NotFoundObjectException("Mauvais ID Bloc");
            if(ueId < 0)   throw new NotFoundObjectException("Mauvais ID UE");

            this.blocManager.linkUE(blocId,ueId);
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
    public ResponseEntity<Map<String, Object>> unlinkUE(@RequestParam("blocId") int blocId, @RequestParam("ueId") int ueId){
        Map <String, Object> result = new HashMap<>();

        try{
            if(blocId < 0) throw new NotFoundObjectException("Mauvais ID Bloc");
            if(ueId < 0)   throw new NotFoundObjectException("Mauvais ID UE");

            this.blocManager.unlinkUE(blocId,ueId);

            result.put("success", true);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (DecofoException e) {
            result.put("error", e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // ajouter une option
    @GetMapping("/link-option/")
    public ResponseEntity<Map<String, Object>> linkOption(@RequestParam("blocId") int blocId, @RequestParam("optionId") int optionId) {
        Map <String, Object> result = new HashMap<>();
        try{
            if(blocId < 0)   throw new NotFoundObjectException("Mauvais ID Bloc");
            if(optionId < 0) throw new NotFoundObjectException("Mauvais ID Option");

            this.blocManager.linkOption(blocId,optionId);
            result.put("success", true);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (DecofoException e) {
            result.put("success", false);
            result.put("error", e.getMessage());

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // enlever une option
    @GetMapping("/unlink-option/")
    public ResponseEntity<Map<String, Object>> unlinkOption(@RequestParam("blocId") int blocId, @RequestParam("optionId") int optionId){
        Map <String, Object> result = new HashMap<>();

        try{
            if(blocId < 0)   throw new NotFoundObjectException("Mauvais ID Bloc");
            if(optionId < 0) throw new NotFoundObjectException("Mauvais ID Option");

            this.blocManager.unlinkOption(blocId,optionId);

            result.put("success", true);
            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (DecofoException e) {
            result.put("error", e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
