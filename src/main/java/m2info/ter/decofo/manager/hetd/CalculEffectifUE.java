package m2info.ter.decofo.manager.hetd;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.classes.Option;
import m2info.ter.decofo.classes.UE;
import org.springframework.stereotype.Service;

@Service

public class CalculEffectifUE {

    private Formation formation;

    public Formation getFormation () {
        return this.formation;
    }

    public void calculerEffectifUEs(Formation formation) {
        this.formation = formation;

        for(UE ue : formation.getUEs())  {
            System.err.println("UE" + ue);
            int effectifTotalUE = 0;

            // pour chaque bloc contenant l'UE
            for(Bloc blocs: ue.getBlocs()) {
                effectifTotalUE += blocs.getEffectif().getSite1();
                effectifTotalUE += blocs.getEffectif().getSite2();
                effectifTotalUE += blocs.getEffectif().getSite3();
                effectifTotalUE += blocs.getEffectif().getSite4();
            }

            // et pour chaque option contenant UE
            for (Option option: ue.getOptions()) {
                effectifTotalUE += option.getEffectifParUe();
            }
            ue.setEffectifTotal(effectifTotalUE);
        }
    }
}
