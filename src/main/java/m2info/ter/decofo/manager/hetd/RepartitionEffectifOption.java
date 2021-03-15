package m2info.ter.decofo.manager.hetd;

import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.classes.Option;
import org.springframework.stereotype.Service;

@Service
public class RepartitionEffectifOption {

    private Formation formation;

    public Formation getFormation () {
        return this.formation;
    }

    public void calculerEffectifOption(Formation formation) {
        this.formation = formation;

        for(Option option: formation.getOptions()) {

            // Effectif pour chaque UE = effectif option / nombre d'UE
            int nombreUEs = option.getUes().size();
            if (nombreUEs == 0) {
                option.setEffectifParUe(0);
            } else {
                int effectifParUe = option.getEffectifTotal() / nombreUEs;
                option.setEffectifParUe(effectifParUe);
            }
        }


    }
}
