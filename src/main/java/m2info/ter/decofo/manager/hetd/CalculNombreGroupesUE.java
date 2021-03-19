package m2info.ter.decofo.manager.hetd;

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

        System.err.println("----------------------------------------------");
        System.err.println("3 / Calcul Nombre groupes UEs");

        for(UE ue: this.formation.getUEs()) {
            this.calculerNombreGroupesCMTotal(ue);
            this.calculerNombreGroupesTDTotal(ue);
            this.calculerNombreGroupesTPTotal(ue);

            System.err.println("Nombre Groupes CM/TD/TP UE (" + ue.getCode() + "): (" + ue.getNombreGroupesCM() + ", " + ue.getNombreGroupesTD() + ", " + ue.getNombreGroupesTP() + ")");

        }

    }

    /**
     * Calcule nombre groupes CM par site
     * puis fais la somme et l'enregistre
     */
    private void calculerNombreGroupesCMTotal(UE ue) {
        int nombreGroupeCMTotal;
        ue.setNombreGroupeCMSite1(this.calculerEffectif(ue.getEffectifTotalParSite().getSite1(), ue.getFormationOwner().getTailleGroupeCM()));
        ue.setNombreGroupeCMSite2(this.calculerEffectif(ue.getEffectifTotalParSite().getSite2(), ue.getFormationOwner().getTailleGroupeCM()));
        ue.setNombreGroupeCMSite3(this.calculerEffectif(ue.getEffectifTotalParSite().getSite3(), ue.getFormationOwner().getTailleGroupeCM()));
        ue.setNombreGroupeCMSite4(this.calculerEffectif(ue.getEffectifTotalParSite().getSite4(), ue.getFormationOwner().getTailleGroupeCM()));

        nombreGroupeCMTotal = ue.getNombreGroupeCMSite1() + ue.getNombreGroupeCMSite2() + ue.getNombreGroupeCMSite3() + ue.getNombreGroupeCMSite4() ;
        ue.setNombreGroupesCM(nombreGroupeCMTotal);
    }

    /**
     * Calcule nombre groupes TD par site
     * puis fais la somme et l'enregistre
     */
    private void calculerNombreGroupesTDTotal(UE ue) {
        int nombreGroupeTDTotal;

        ue.setNombreGroupeTDSite1(this.calculerEffectif(ue.getEffectifTotalParSite().getSite1(), ue.getFormationOwner().getTailleGroupeTD()));
        ue.setNombreGroupeTDSite2(this.calculerEffectif(ue.getEffectifTotalParSite().getSite2(), ue.getFormationOwner().getTailleGroupeTD()));
        ue.setNombreGroupeTDSite3(this.calculerEffectif(ue.getEffectifTotalParSite().getSite3(), ue.getFormationOwner().getTailleGroupeTD()));
        ue.setNombreGroupeTDSite4(this.calculerEffectif(ue.getEffectifTotalParSite().getSite4(), ue.getFormationOwner().getTailleGroupeTD()));

        nombreGroupeTDTotal = ue.getNombreGroupeTDSite1() + ue.getNombreGroupeTDSite2() + ue.getNombreGroupeTDSite3() + ue.getNombreGroupeTDSite4() ;
        ue.setNombreGroupesTD(nombreGroupeTDTotal);
    }

    /**
     * Calcule nombre groupes TP par site
     * puis fais la somme et l'enregistre
     */
    private void calculerNombreGroupesTPTotal(UE ue) {
        int nombreGroupeTPTotal = 0;

        ue.setNombreGroupeTPSite1(this.calculerEffectif(ue.getEffectifTotalParSite().getSite1(), ue.getFormationOwner().getTailleGroupeTP()));
        ue.setNombreGroupeTPSite1(this.calculerEffectif(ue.getEffectifTotalParSite().getSite1(), ue.getFormationOwner().getTailleGroupeTP()));
        ue.setNombreGroupeTPSite2(this.calculerEffectif(ue.getEffectifTotalParSite().getSite2(), ue.getFormationOwner().getTailleGroupeTP()));
        ue.setNombreGroupeTPSite3(this.calculerEffectif(ue.getEffectifTotalParSite().getSite3(), ue.getFormationOwner().getTailleGroupeTP()));
        ue.setNombreGroupeTPSite4(this.calculerEffectif(ue.getEffectifTotalParSite().getSite4(), ue.getFormationOwner().getTailleGroupeTP()));

        nombreGroupeTPTotal = ue.getNombreGroupeTPSite1() + ue.getNombreGroupeTPSite2() + ue.getNombreGroupeTPSite3() + ue.getNombreGroupeTPSite4() ;
        ue.setNombreGroupesTP(nombreGroupeTPTotal);
    }


    /**
     * Calcul Nombre groupe TD pour un site
     * si effectif == 0,
     *      on renvoie 0 car 0 groupe
     * si effectif < taille_imposé
     *      on renvoie 1 car ça tient sur un groupe
     * sinon
     *      renvoyer division exces (taille, effectif)
     * (eff + taille) - 1
     * ------------------
     *      taille
     *
     * une seule formule
     */
    private int calculerEffectif(int effectif, int taille) {
        if(effectif == 0 || taille == 0) {
            return 0;
        } else if (effectif < taille) {
            return 1;
        } else {
            // division par exces
            int nbGroupe = ( (effectif + taille  - 1) / taille );
            return nbGroupe;
        }
    }

}
