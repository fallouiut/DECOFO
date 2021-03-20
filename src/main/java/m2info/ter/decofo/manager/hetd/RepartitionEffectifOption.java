package m2info.ter.decofo.manager.hetd;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Effectif;
import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.classes.Option;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepartitionEffectifOption {

    private Formation formation;

    public Formation getFormation () {
        return this.formation;
    }

    List<String> alertMessages = new ArrayList<>();

    public void calculerEffectifOption(Formation formation) {
        this.formation = formation;

        System.err.println("----------------------------------------------");
        System.err.println("1 / Calcul Effectif Option + répartition sur UEs");

        formation.getOptions().forEach((option) -> {
            Effectif effectifTotalParSite = option.getEffectifTotalParSite();
            Effectif effectifParUE = option.getEffectifParUE();

            /**
             * fais la somme des effectifs par site
             * de tout les options appartenant au bloc
             */
            option.getBlocs().forEach((bloc -> {
                effectifTotalParSite.setSite1(effectifTotalParSite.getSite1() + bloc.getEffectif().getSite1());
                effectifTotalParSite.setSite2(effectifTotalParSite.getSite2() + bloc.getEffectif().getSite2());
                effectifTotalParSite.setSite3(effectifTotalParSite.getSite3() + bloc.getEffectif().getSite3());
                effectifTotalParSite.setSite4(effectifTotalParSite.getSite4() + bloc.getEffectif().getSite4());

                int nombreUEs = option.getUes().size();

                if(nombreUEs == 0) {
                    alertMessages.add("Option (" + option.getCode() + ") n'a pas d'UEs");
                }

                /**
                 * calcule effectif repartition par UE
                 *  effectif / nombre UE pour chaque site
                 */
                effectifParUE.setSite1(this.calculerEffectifParSiteUE(effectifTotalParSite.getSite1(), nombreUEs));
                effectifParUE.setSite2(this.calculerEffectifParSiteUE(effectifTotalParSite.getSite2(), nombreUEs));
                effectifParUE.setSite3(this.calculerEffectifParSiteUE(effectifTotalParSite.getSite3(), nombreUEs));
                effectifParUE.setSite4(this.calculerEffectifParSiteUE(effectifTotalParSite.getSite4(), nombreUEs));

                System.err.println("Effectif Option (: " + option.getCode() + "): " + option.getEffectifTotalParSite());
                System.err.println("Effectif par UE (: " + option.getCode() + "): " + option.getEffectifParUE());

            }));

        });
    }

    private int calculerEffectifParSiteUE(int effectif, int nombreUE) {

        if(effectif == 0) return 0;

        double effectifParSiteUE = (double) effectif / nombreUE;

        if((effectif % nombreUE) == 0) {
            return (int) effectifParSiteUE;
        } else { // arrondi supérieur, s
            int arrondiSuperieur = (int) Math.ceil(effectifParSiteUE);
            return arrondiSuperieur;
        }
    }
}
