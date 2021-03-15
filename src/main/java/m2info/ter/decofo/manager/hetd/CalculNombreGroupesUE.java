package m2info.ter.decofo.manager.hetd;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.classes.UE;
import org.springframework.stereotype.Service;

@Service
public class CalculNombreGroupesUE {

    private Formation formation;

    public Formation getFormation () {
        return this.formation;
    }

    public void calculerNombreGroupesCMTDTP(Formation formation) {
        this.formation = formation;

        for(UE ue: this.formation.getUEs()) {

            int nombreGroupeCM = 0;
            int nombreGroupeTD = 0;
            int nombreGroupeTP = 0;

            for(Bloc bloc : ue.getBlocs()) {

                // pour chaque site, calculer le npmbre de groupe CM, TD , TP
                // incrémenter cette valeur à l'efectif total (CM, TD, TP) pour chaque UE

                bloc.getEffectif().setNbGroupesCMSite1(this.calculerNombreGroupesCM(ue, bloc.getEffectif().getSite1()));
                bloc.getEffectif().setNbGroupesCMSite2(this.calculerNombreGroupesCM(ue, bloc.getEffectif().getSite2()));
                bloc.getEffectif().setNbGroupesCMSite3(this.calculerNombreGroupesCM(ue, bloc.getEffectif().getSite3()));
                bloc.getEffectif().setNbGroupesCMSite4(this.calculerNombreGroupesCM(ue, bloc.getEffectif().getSite4()));

                bloc.getEffectif().setNbGroupesTDSite1(this.calculerNombreGroupesTD(ue, bloc.getEffectif().getSite1()));
                bloc.getEffectif().setNbGroupesTDSite2(this.calculerNombreGroupesTD(ue, bloc.getEffectif().getSite2()));
                bloc.getEffectif().setNbGroupesTDSite3(this.calculerNombreGroupesTD(ue, bloc.getEffectif().getSite3()));
                bloc.getEffectif().setNbGroupesTDSite4(this.calculerNombreGroupesTD(ue, bloc.getEffectif().getSite4()));

                bloc.getEffectif().setNbGroupesTPSite1(this.calculerNombreGroupesTP(ue, bloc.getEffectif().getSite1()));
                bloc.getEffectif().setNbGroupesTPSite2(this.calculerNombreGroupesTP(ue, bloc.getEffectif().getSite2()));
                bloc.getEffectif().setNbGroupesTPSite3(this.calculerNombreGroupesTP(ue, bloc.getEffectif().getSite3()));
                bloc.getEffectif().setNbGroupesTPSite4(this.calculerNombreGroupesTP(ue, bloc.getEffectif().getSite4()));

                nombreGroupeCM = bloc.getEffectif().getNbGroupesCMSite1() +  bloc.getEffectif().getNbGroupesCMSite2() +  bloc.getEffectif().getNbGroupesCMSite3() +  bloc.getEffectif().getNbGroupesCMSite4();
                nombreGroupeTD = bloc.getEffectif().getNbGroupesTDSite1() +  bloc.getEffectif().getNbGroupesTDSite2() +  bloc.getEffectif().getNbGroupesTDSite3() +  bloc.getEffectif().getNbGroupesTDSite4();
                nombreGroupeTP = bloc.getEffectif().getNbGroupesTPSite1() +  bloc.getEffectif().getNbGroupesTPSite2() +  bloc.getEffectif().getNbGroupesTPSite3() +  bloc.getEffectif().getNbGroupesTPSite4();

            }

            // garder la valeur
            ue.setNombreGroupesCM(nombreGroupeCM);
            ue.setNombreGroupesTD(nombreGroupeTD);
            ue.setNombreGroupesTP(nombreGroupeTP);
        }

    }

    private int calculerNombreGroupesCM(UE ue, int effectifSite) {
        if(effectifSite == 0) {
            return 0;
        } else {
            return 1;
            //int nbGroupe = effectifSite + ue.getFormationOwner().getTailleGroupeCM() - 1;
            //nbGroupe  = nbGroupe / ue.getFormationOwner().getTailleGroupeCM();

            //return nbGroupe;
        }
    }

    /**
     * si effectif == 0,
     *      on renvoie 0 car 0 groupe
     * si effectif < taille_imposé
     *      on renvoie 1 car ça tient sur un groupe
     * sinon
     *      si reste_division(effecif, taille) = 0
     *          renvoie la valeur car pas de valeur décimal
     *      sinon
     *          renvoie division exces arrondi supérieur
     */
    private int calculerNombreGroupesTD(UE ue, int effectifSite) {
        System.out.println("Effectif : "+ effectifSite + ", taille: " + ue.getFormationOwner().getTailleGroupeTD());
        
        if(effectifSite == 0) {
            return 0;
        } else if (effectifSite < ue.getFormationOwner().getTailleGroupeTD()) {
            return 1;
        } else {
            boolean modulo = (effectifSite % ue.getFormationOwner().getTailleGroupeTD()) == 0;

            if(modulo) {
                int rest = effectifSite / ue.getFormationOwner().getTailleGroupeTD();
                return rest;
            } else {
                double nbGroupe = (effectifSite  - 1) / (double)(ue.getFormationOwner().getTailleGroupeTD());
                int nbGroupeInt = (int) nbGroupe;
                int arrondiSuperieur = (int) Math.ceil(nbGroupe);
                return arrondiSuperieur;
            }
        }
    }

    private int calculerNombreGroupesTP(UE ue, int effectifSite) {
        if(effectifSite == 0) {
            return 0;
        } else if (effectifSite < ue.getFormationOwner().getTailleGroupeTP()) {
            return 1;
        } else {

            boolean modulo = (effectifSite % ue.getFormationOwner().getTailleGroupeTP()) == 0;
            if(modulo) {
                int rest = effectifSite / ue.getFormationOwner().getTailleGroupeTP();
                return rest;
            } else {
                double nbGroupe = (effectifSite  - 1) / (double)(ue.getFormationOwner().getTailleGroupeTP());
                int nbGroupeInt = (int) nbGroupe;
                int arrondiSuperieur = (int) Math.ceil(nbGroupe);
                return arrondiSuperieur;
            }
        }
    }


}
