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
     * Calcule nombre groupes CM par site
     * puis fais la somme et l'enregistre
     */
    private void calculerNombreGroupesCMTotal(UE ue) {
        int nombreGroupeCMTotal;

        ue.setNombreGroupeCMSite1(this.calculerNombreGroupesCMSite(ue, ue.getEffectifTotalSite1()));
        ue.setNombreGroupeCMSite2(this.calculerNombreGroupesCMSite(ue, ue.getEffectifTotalSite2()));
        ue.setNombreGroupeCMSite3(this.calculerNombreGroupesCMSite(ue, ue.getEffectifTotalSite3()));
        ue.setNombreGroupeCMSite4(this.calculerNombreGroupesCMSite(ue, ue.getEffectifTotalSite4()));

        nombreGroupeCMTotal = ue.getNombreGroupeCMSite1() + ue.getNombreGroupeCMSite2() + ue.getNombreGroupeCMSite3() + ue.getNombreGroupeCMSite4() ;

        ue.setNombreGroupesCM(nombreGroupeCMTotal);
    }

    /**
     * Calcule nombre groupes TD par site
     * puis fais la somme et l'enregistre
     */
    private void calculerNombreGroupesTDTotal(UE ue) {
        int nombreGroupeTDTotal;

        ue.setNombreGroupeTDSite1(this.calculerNombreGroupesTDSite(ue, ue.getEffectifTotalSite1()));
        ue.setNombreGroupeTDSite2(this.calculerNombreGroupesTDSite(ue, ue.getEffectifTotalSite2()));
        ue.setNombreGroupeTDSite3(this.calculerNombreGroupesTDSite(ue, ue.getEffectifTotalSite3()));
        ue.setNombreGroupeTDSite4(this.calculerNombreGroupesTDSite(ue, ue.getEffectifTotalSite4()));
        nombreGroupeTDTotal = ue.getNombreGroupeTDSite1() + ue.getNombreGroupeTDSite2() + ue.getNombreGroupeTDSite3() + ue.getNombreGroupeTDSite4() ;

        ue.setNombreGroupesTD(nombreGroupeTDTotal);
    }

    /**
     * Calcule nombre groupes TP par site
     * puis fais la somme et l'enregistre
     */
    private void calculerNombreGroupesTPTotal(UE ue) {
        int nombreGroupeTPTotal = 0;

        ue.setNombreGroupeTPSite1(this.calculerNombreGroupesTPSite(ue, ue.getEffectifTotalSite1()));
        ue.setNombreGroupeTPSite2(this.calculerNombreGroupesTPSite(ue, ue.getEffectifTotalSite2()));
        ue.setNombreGroupeTPSite3(this.calculerNombreGroupesTPSite(ue, ue.getEffectifTotalSite3()));
        ue.setNombreGroupeTPSite4(this.calculerNombreGroupesTPSite(ue, ue.getEffectifTotalSite4()));
        nombreGroupeTPTotal = ue.getNombreGroupeTPSite1() + ue.getNombreGroupeTPSite2() + ue.getNombreGroupeTPSite3() + ue.getNombreGroupeTPSite4() ;

        ue.setNombreGroupesTP(nombreGroupeTPTotal);
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
