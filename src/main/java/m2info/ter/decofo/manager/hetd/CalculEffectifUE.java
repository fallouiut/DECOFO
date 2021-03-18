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

        int i = 0;
        for(UE ue : formation.getUEs())  {
            this.calculerEffectifParSite(ue, i);
            i+=1;
        }
    }

    private void calculerEffectifParSite(UE ue, int i) {
        int effectifTotalUESite1 = 0;    // somme des effectifs des blocs (pour le site 1)
        int effectifTotalUESite2 = 0;    // somme des effectifs des blocs (pour le site 2)
        int effectifTotalUESite3 = 0;    // somme des effectifs des blocs (pour le site 3)
        int effectifTotalUESite4 = 0;    // somme des effectifs des blocs (pour le site 4)


        System.err.println("--------------------------------");
        System.err.println("UE " + i);
        // pour somme effectif pa site pour chaque bloc
        for(Bloc bloc: ue.getBlocs()) {
            effectifTotalUESite1 += bloc.getEffectif().getSite1();
            effectifTotalUESite2 += bloc.getEffectif().getSite2();
            effectifTotalUESite3 += bloc.getEffectif().getSite3();
            effectifTotalUESite4 += bloc.getEffectif().getSite4();
        }

        // pour somme effectif pa site pour chaque option
        for (Option option: ue.getOptions()) {
            effectifTotalUESite1 += option.getEffectifParUESite1();
            effectifTotalUESite2 += option.getEffectifParUESite2();
            effectifTotalUESite3 += option.getEffectifParUESite3();
            effectifTotalUESite4 += option.getEffectifParUESite4();
        }

        // enregistre les valeurs pour la suite du calcul
        ue.setEffectifTotalSite1(effectifTotalUESite1);
        ue.setEffectifTotalSite2(effectifTotalUESite2);
        ue.setEffectifTotalSite3(effectifTotalUESite3);
        ue.setEffectifTotalSite4(effectifTotalUESite4);
    }

}
