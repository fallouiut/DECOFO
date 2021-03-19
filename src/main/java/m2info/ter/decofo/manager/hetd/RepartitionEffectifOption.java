package m2info.ter.decofo.manager.hetd;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Effectif;
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

        System.err.println("----------------------------------------------");
        System.err.println("1 / Calcul Effectif Option + répartition sur UEs");

        for(Option option: formation.getOptions()) {

            Effectif effectifTotalParSite = option.getEffectifTotalParSite();
            Effectif effectifParUE = option.getEffectifParUE();

            /**
             * fais la somme des effectifs par site
             * de tout les options appartenant au bloc
             */
            for(Bloc blocs: option.getBlocs()) {
                effectifTotalParSite.setSite1(effectifTotalParSite.getSite1() + blocs.getEffectif().getSite1());
                effectifTotalParSite.setSite2(effectifTotalParSite.getSite2() + blocs.getEffectif().getSite2());
                effectifTotalParSite.setSite3(effectifTotalParSite.getSite3() + blocs.getEffectif().getSite3());
                effectifTotalParSite.setSite4(effectifTotalParSite.getSite4() + blocs.getEffectif().getSite4());
            }

            int nombreUEs = option.getUes().size();

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
        }

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
