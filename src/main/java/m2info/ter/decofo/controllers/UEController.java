package m2info.ter.decofo.controllers;

import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.classes.Option;
import m2info.ter.decofo.classes.UE;
import m2info.ter.decofo.exceptions.DecofoException;
import m2info.ter.decofo.exceptions.NotFoundObjectException;
import m2info.ter.decofo.manager.gestion.UEManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ues")
public class UEController {

    @Autowired
    UEManager ueManager;

    @GetMapping("/read-one/{UEId}")
    public ResponseEntity<Formation> getOneUE(@PathVariable("UEId") int UEId) {
        Map <String, Object> result = new HashMap<>();
        try {
            if (UEId < 0) throw new NotFoundObjectException("Mauvais ID saisit");
            UE ue = ueManager.findOne(UEId);
            if(ue == null) throw new NotFoundObjectException("ue n'existe pas");
            result.put("ue", ue);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (DecofoException e) {
            result.put("error", e.getMessage());
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update/{UEId}")
    public ResponseEntity<Object> updateUE(@RequestBody UE ue, @PathVariable("UEId") int UEId) throws Exception {
        Map <String, Object> result = new HashMap<>();
        try {
            if (UEId < 0) throw new NotFoundObjectException("Mauvais ID saisit");
            ue.setId(UEId);
            this.ueManager.update(ue);

            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (DecofoException e) {
            result.put("error", e.getMessage());
            return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/delete/{UEId}")
    public ResponseEntity<Object> deleteUE(@PathVariable("UEId") int UEId) {
        Map <String, Object> result = new HashMap<>();
        try {
            if (UEId < 0) throw new NotFoundObjectException("Mauvais ID saisit");
            this.ueManager.delete(UEId);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
