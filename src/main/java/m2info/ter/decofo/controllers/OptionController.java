package m2info.ter.decofo.controllers;

import m2info.ter.decofo.classes.Option;
import m2info.ter.decofo.managers.OptionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/options")
public class OptionController {

    @Autowired
    OptionManager optionManager;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createOption(@RequestBody Option option) {
        System.err.println("Adding option + " + option.toString());
        this.optionManager.insert(option);
        return new ResponseEntity(null, HttpStatus.OK);
    }
    
    @GetMapping("/read-one/{optionId}")
    public ResponseEntity<Option> getOneOption(@PathVariable("optionId") int optionId) {

        Option option = optionManager.findOne(optionId);

        if (option != null) {
            return new ResponseEntity(option, HttpStatus.OK);
        } else {
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/read-all")
    public ResponseEntity<List<Option>> getAllOptions() {
        return new ResponseEntity<>(this.optionManager.findAll(), HttpStatus.OK);
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

    // ajouter UE
    // enlever UE

}
