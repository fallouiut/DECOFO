package m2info.ter.decofo.manager.hetd;

import m2info.ter.decofo.classes.*;
import org.springframework.stereotype.Service;

@Service
public class CalculEffectifUE {

    private Formation formation;

    public Formation getFormation () {
        return this.formation;
    }

    public void calculerEffectifUEs(Formation formation) {
        this.formation = formation;

        System.err.println("----------------------------------------------");
        System.err.println("2 / Calcul Effectif UEs");

        formation.getUEs().forEach(this::calculerEffectifParSite);
    }

    private void calculerEffectifParSite(UE ue) {
        // pour somme effectif pa site pour chaque bloc

        ue.getBlocs().forEach((bloc -> {
            ue.getEffectifTotalParSite().additionParSite(bloc.getEffectif());
        }));

        ue.getOptions().forEach((option -> {
            ue.getEffectifTotalParSite().additionParSite(option.getEffectifParUE());
        }));

        System.err.println("UE : (" + ue.getCode() + "): " + ue.getEffectifTotalParSite());
    }

}
