package m2info.ter.decofo.controllers;

import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.classes.UE;
import m2info.ter.decofo.managers.UEManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ues")
public class UEController {

    @Autowired
    UEManager ueManager;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createUE(@RequestBody UE ue) {
        System.err.println("UE : " + ue.toString());
        this.ueManager.insert(ue);

        return new ResponseEntity(null, HttpStatus.OK);
    }

    @GetMapping("/read-one/{UEId}")
    public ResponseEntity<Formation> getOneUE(@PathVariable("UEId") int UEId) {
        UE ue = ueManager.findOne(UEId);

        if (ue != null) {
            return new ResponseEntity(ue, HttpStatus.OK);
        } else {
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/read-all")
    public ResponseEntity<List<UE>> getAllUEs() {
        return new ResponseEntity<>(this.ueManager.findAll(), HttpStatus.OK);
    }

    @PostMapping("/update/{UEId}")
    public ResponseEntity<Object> updateUE(@RequestBody UE ue, @PathVariable("UEId") int UEId) throws Exception {
        System.err.println("Updatue UE " + UEId);
        ue.setId(UEId);
        this.ueManager.update(ue);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/delete/{UEId}")
    public ResponseEntity<Object> deleteUE(@PathVariable("UEId") int UEId) {
        this.ueManager.delete(UEId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
