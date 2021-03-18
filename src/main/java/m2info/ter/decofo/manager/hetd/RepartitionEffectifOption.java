package m2info.ter.decofo.manager.hetd;

import m2info.ter.decofo.classes.Bloc;
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
            int effectifParOptionSite1 = 0;
            int effectifParOptionSite2 = 0;
            int effectifParOptionSite3 = 0;
            int effectifParOptionSite4 = 0;

            for(Bloc blocs: option.getBlocs()) {
                effectifParOptionSite1 += blocs.getEffectif().getSite1();
                effectifParOptionSite2 += blocs.getEffectif().getSite2();
                effectifParOptionSite3 += blocs.getEffectif().getSite3();
                effectifParOptionSite4 += blocs.getEffectif().getSite4();
            }

            int nombreUEs = option.getUes().size();

            int effectifParUESitet1 = this.calculerEffectifParSiteUE(effectifParOptionSite1, nombreUEs);
            int effectifParUESitet2 = this.calculerEffectifParSiteUE(effectifParOptionSite2, nombreUEs);
            int effectifParUESitet3 = this.calculerEffectifParSiteUE(effectifParOptionSite3, nombreUEs);
            int effectifParUESitet4 = this.calculerEffectifParSiteUE(effectifParOptionSite4, nombreUEs);

            // enregistre les valeurs pour la suite du calcul
            option.setEffectifOptionSite1(effectifParOptionSite1);
            option.setEffectifOptionSite2(effectifParOptionSite2);
            option.setEffectifOptionSite3(effectifParOptionSite3);
            option.setEffectifOptionSite4(effectifParOptionSite4);

            option.setEffectifParUESite1(effectifParUESitet1);
            option.setEffectifParUESite2(effectifParUESitet2);
            option.setEffectifParUESite3(effectifParUESitet3);
            option.setEffectifParUESite4(effectifParUESitet4);
        }

    }

    private int calculerEffectifParSiteUE(int effectif, int nombreUE) {

        if(effectif == 0) return 0;

        double effectifParSiteUE = (double)effectif / nombreUE;

        if((effectif % nombreUE) == 0) {
            return (int) effectifParSiteUE;
        } else { // arrondi supérieur, s
            int arrondiSuperieur = (int) Math.ceil(effectifParSiteUE);
            return arrondiSuperieur;
        }
    }
}
