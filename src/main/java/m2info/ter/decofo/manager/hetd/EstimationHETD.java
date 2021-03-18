package m2info.ter.decofo.manager.hetd;

import m2info.ter.decofo.classes.Bloc;
import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.classes.Option;
import m2info.ter.decofo.classes.UE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EstimationHETD {

    Map<String, String> alert;

    @Autowired
    RepartitionEffectifOption repartitionEffectifOption;

    @Autowired
    CalculEffectifUE calculEffectifUE;

    @Autowired
    CalculNombreGroupesUE calculNombreGroupesUE;

    private Formation formation;

    public Formation getFormation () {
        return this.formation;
    }

    public void calculerHETD(Formation formation) {
        this.formation = formation;

        repartitionEffectifOption.calculerEffectifOption(formation);
        calculEffectifUE         .calculerEffectifUEs(repartitionEffectifOption.getFormation());
        calculNombreGroupesUE    .calculerNombreGroupesCMTDTP(calculEffectifUE.getFormation());

        this.formation = calculNombreGroupesUE.getFormation();

        calculerHETDUEs();
        calculerHETDUOption();
        calculerHETDUBlocs();
        calculerHETDUFormation();

    }

    private void calculerHETDUEs() {
        for(UE ue: this.formation.getUEs()) {
            double coutCM = 1.5 * ue.getNombreGroupesCM() * ue.getNombreHeureCM();
            double coutTD = ue.getNombreGroupesTD() * ue.getNombreHeureTD();
            double coutTP = ue.getNombreGroupesTP() * ue.getNombreHeureTP();
            ue.setCout(coutCM + coutTD + coutTP);
            System.err.println("HETD ("+ ue.getIntitule() + "): " + ue.getCout());
        }
    }

    private void calculerHETDUOption() {
        for(Option option: this.formation.getOptions()) {
            double coutOption = 0;
            for(UE ue: option.getUes()) {
                double coutUe = ue.getCout() * ((double)option.getEffectifTotalParUE() / ue.getEffectifTotal());
                coutOption += coutUe;
            }
            option.setCout(coutOption);
            System.err.println("HETD ("+ option.getIntitule() + "): " + option.getCout());
        }
    }
    private void calculerHETDUBlocs() {
        for(Bloc b: this.formation.getBlocs()) {
            double coutBloc = 0;
            int effectifTotalBloc = b.getEffectifTotal();

            for(UE ue: b.getUes()) {
                double coutUe = ue.getCout() * ((double)effectifTotalBloc / ue.getEffectifTotal());
                coutBloc += coutUe;
            }

            for (Option option: b.getOptions()) {
                double coutOption = option.getCout() * ((double)effectifTotalBloc / option.getEffectifTotal());
                coutBloc += coutOption;
            }
            b.setCout(coutBloc);
            System.err.println("HETD ("+ b.getIntitule() + "): " + b.getCout());
        }
    }

    private void calculerHETDUFormation() {
        double coutFormation = 0;
        for(Bloc bloc: this.formation.getBlocs()) {
            coutFormation += bloc.getCout();
        }
        this.formation.setCout(coutFormation);
    }

}
