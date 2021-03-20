package m2info.ter.decofo.manager.hetd;

import m2info.ter.decofo.classes.Formation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstimationHETD {

    @Autowired
    RepartitionEffectifOption repartitionEffectifOption;

    @Autowired
    CalculEffectifUE calculEffectifUE;

    @Autowired
    CalculNombreGroupesUE calculNombreGroupesUE;

    private Formation formation;

    List<String> alertMessages = new ArrayList<>();

    public List<String> getAlertMessages() {
        alertMessages.addAll(repartitionEffectifOption.alertMessages);
        alertMessages.addAll(calculEffectifUE.alertMessages);
        alertMessages.addAll(calculNombreGroupesUE.alertMessages);
        return alertMessages;
    }

    public Formation getFormation () {
        return this.formation;
    }


    public void calculerHETD(Formation formation) {
        this.formation = formation;

        repartitionEffectifOption.calculerEffectifOption(formation);
        calculEffectifUE         .calculerEffectifUEs(repartitionEffectifOption.getFormation());
        calculNombreGroupesUE    .calculerNombreGroupesCMTDTP(calculEffectifUE.getFormation());

        this.formation = calculNombreGroupesUE.getFormation();


        System.err.println("----------------------------------------------");
        System.err.println("4 / HETD");

        calculerHETDUEs();
        calculerHETDUOption();
        calculerHETDUBlocs();
        calculerHETDUFormation();

    }

    /**
     * HETD UE
     * parametrer 1.5 (sur fichier ou BD)
     */
    private void calculerHETDUEs() {
        formation.getUEs().forEach((ue) -> {
            double coutCM = 1.5 * ue.getNombreGroupesCM() * ue.getNombreHeureCM();
            double coutTD = ue.getNombreGroupesTD() * ue.getNombreHeureTD();
            double coutTP = ue.getNombreGroupesTP() * ue.getNombreHeureTP();
            ue.setCout(coutCM + coutTD + coutTP);
            System.err.println("HETD ("+ ue.getCode() + "): " + ue.getCout());

            if(ue.getCout() == 0) {
                alertMessages.add("HETD de l'UE (" + ue.getCode() + ") est à zéro !");
            }

        });
    }

    /**
     * HETD Option
     */
    private void calculerHETDUOption() {
        formation.getOptions().forEach((option)->{
            option.setCout(0);

            option.getUes().forEach((ue)-> {
                double coutUe = ue.getCout() * ((double)option.getEffectifParUE().getEffectifTotal() / ue.getEffectifTotalParSite().getEffectifTotal());
                option.setCout(option.getCout() + coutUe);
            });
            System.err.println("HETD ("+ option.getCode() + "): " + option.getCout());

            if(option.getCout() == 0) {
                alertMessages.add("HETD de l'Option (" + option.getCode() + ") est à zéro !");
            }
        });
    }

    /**
     * HETD Bloc
     */
    private void calculerHETDUBlocs() {
        formation.getBlocs().forEach((bloc -> {
            bloc.setCout(0);
            int effectifTotalBloc = bloc.getEffectif().getEffectifTotal();

            bloc.getUes().forEach((ue -> {
                double coutUe = ue.getCout() * ((double)effectifTotalBloc / ue.getEffectifTotalParSite().getEffectifTotal());
                bloc.setCout(bloc.getCout() + coutUe);
            }));

            bloc.getOptions().forEach((option -> {
                double coutOption = option.getCout() * ((double)effectifTotalBloc / option.getEffectifTotalParSite().getEffectifTotal());
                bloc.setCout(bloc.getCout() + coutOption);
            }));

            if(bloc.getEffectif().getEffectifTotal() == 0) {
                alertMessages.add("Bloc (" + bloc.getCode() + ") n'a pas d'effectif !");
            }

            if(bloc.getCout() == 0) {
                alertMessages.add("HETD du bloc (" + bloc.getCode() + ") est à zéro !");
            }

        }));
    }

    /**
     * HETD Formation
     * // foreach
     */
    private void calculerHETDUFormation() {
        formation.setCout(0);
        formation.getBlocs().forEach((bloc) -> formation.setCout(formation.getCout() + bloc.getCout()));

        //this.formation.setCout(coutFormation);
        System.err.println("HETD ("+ formation.getCode() + "): " + formation.getCout());

        if(formation.getCout() == 0) {
            alertMessages.add("HETD de la formation (" + formation.getCode() + ") est à zéro !");
        }
    }

}
