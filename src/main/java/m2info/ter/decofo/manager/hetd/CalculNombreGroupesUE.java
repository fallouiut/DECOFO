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
            this.calculerNombreGroupesCMTotal(ue);
            this.calculerNombreGroupesTDTotal(ue);
            this.calculerNombreGroupesTPTotal(ue);
        }

    }

    /**
     * Somme des nombres de groupe de CM de chaque site
     */
    private void calculerNombreGroupesCMTotal(UE ue) {
        int nombreGroupeCM = 0;
        nombreGroupeCM += this.calculerNombreGroupesCMSite(ue, ue.getEffectifTotalSite1());
        nombreGroupeCM += this.calculerNombreGroupesCMSite(ue, ue.getEffectifTotalSite2());
        nombreGroupeCM += this.calculerNombreGroupesCMSite(ue, ue.getEffectifTotalSite3());
        nombreGroupeCM += this.calculerNombreGroupesCMSite(ue, ue.getEffectifTotalSite4());
        ue.setNombreGroupesCM(nombreGroupeCM);
    }

    /**
     * Somme des nombres de groupe de TD de chaque site
     */
    private void calculerNombreGroupesTDTotal(UE ue) {
        int nombreGroupeTD = 0;
        nombreGroupeTD += this.calculerNombreGroupesTDSite(ue, ue.getEffectifTotalSite1());
        nombreGroupeTD += this.calculerNombreGroupesTDSite(ue, ue.getEffectifTotalSite2());
        nombreGroupeTD += this.calculerNombreGroupesTDSite(ue, ue.getEffectifTotalSite3());
        nombreGroupeTD += this.calculerNombreGroupesTDSite(ue, ue.getEffectifTotalSite4());
        ue.setNombreGroupesTD(nombreGroupeTD);
    }

    /**
     * Somme des nombres de groupe de TP de chaque site
     */
    private void calculerNombreGroupesTPTotal(UE ue) {
        int nombreGroupeTP = 0;
        nombreGroupeTP += this.calculerNombreGroupesTPSite(ue, ue.getEffectifTotalSite1());
        nombreGroupeTP += this.calculerNombreGroupesTPSite(ue, ue.getEffectifTotalSite2());
        nombreGroupeTP += this.calculerNombreGroupesTPSite(ue, ue.getEffectifTotalSite3());
        nombreGroupeTP += this.calculerNombreGroupesTPSite(ue, ue.getEffectifTotalSite4());
        ue.setNombreGroupesTP(nombreGroupeTP);
    }

    /**
     * Calcul Nombre groupe TD pour un site
     * un site = un groupe de CM
     */
    private int calculerNombreGroupesCMSite(UE ue, int effectifSite) {
        if(effectifSite == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * Calcul Nombre groupe TD pour un site
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
    private int calculerNombreGroupesTDSite(UE ue, int effectifSite) {
        
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

    /**
     * Calcul Nombre groupe TP pour un site
     */
    private int calculerNombreGroupesTPSite(UE ue, int effectifSite) {
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
