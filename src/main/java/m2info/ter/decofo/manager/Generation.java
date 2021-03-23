package m2info.ter.decofo.manager;

import m2info.ter.decofo.classes.*;
import m2info.ter.decofo.exceptions.FormationParentNotFoundException;
import m2info.ter.decofo.exceptions.ItemExistInListException;
import m2info.ter.decofo.exceptions.NotFoundObjectException;
import m2info.ter.decofo.manager.gestion.BlocManager;
import m2info.ter.decofo.manager.gestion.FormationManager;
import m2info.ter.decofo.manager.gestion.OptionManager;
import m2info.ter.decofo.manager.gestion.UEManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;


@Service
public class Generation {

    @Autowired
    FormationManager formationManager;

    @Autowired
    BlocManager blocManager;

    @Autowired
    OptionManager optionManager;

    @Autowired
    UEManager ueManager;


    static String[] intitulé = new String [] {"Diplôme Centrale Digital Lab","Diplôme d'ingénieur de Centrale Marseille","Master Chimie Parcours Analyse chimique et spectroscopie ","Master Chimie Parcours Chimie pour le Vivant ","Master Chimie Parcours Méthodologies innovantes en synthèse organique","Master Génie des procédés et des bio-procédés Parcours Génie des procédés","Master Génie mécanique Parcours Conception de structures composites",
        "Master Génie mécanique Parcours Conception de systèmes mécaniques","Master Informatique Parcours Fiabilité et sécurité informatique","Master Informatique Parcours Ingénierie du logiciel et des données","Master Informatique Parcours Intelligence artificielle et apprentissage automatique","Master Mathématiques appliquées, statistique Parcours Computational and mathematical biology","Master Mathématiques appliquées, statistique Parcours Data science",
        "Master Mathématiques appliquées, statistique Parcours Ingénierie mathématiques et sciences actuarielles ","Master Mathématiques appliquées, statistique Parcours Mathématiques appliquées et sciences sociales - Analyse des populations","Master Mathématiques et Applications Parcours AGREG, Mathématiques pour l'Agrégation","Master Mathématiques et Applications Parcours Mathématiques Fondamentales ","Master Mathématiques et applications Parcours Computational and mathematical biology",
        "Master Mathématiques et applications Parcours Didactique des mathématiques","Master Mathématiques et applications Parcours Informatique et mathématiques discrètes ","Master Mécanique Parcours Aéronautique et transport ","Master Mécanique Parcours Fluids and solids","Master Mécanique Parcours Sciences du feu et ingénierie de la sécurité incendie","Master Mécanique Parcours Waves, Acoustics, Vibrations, Engineering and Sound","Master Physique Parcours Europhotonics",
        "Master Physique Parcours Physique","Master Physique Parcours Sciences de la Fusion et des Plasmas","Master Traitement du signal et des images Parcours Images, modèles et vision","Master Traitement du signal et des images Parcours Interactions physique signaux image","Master Traitement du signal et des images Parcours Signaux et images biomédicaux","Licence Chimie Parcours Chimie","Licence Chimie Parcours Génie des procédés","Licence Chimie Parcours Physique-chimie",
        "Licence Informatique Parcours Informatique","Licence Informatique Parcours Méthodes Informatiques Appliquées à la Gestion des Entreprises","Licence Mécanique","Licence Sciences de la vie Parcours Biochimie","Licence Sciences de la vie Parcours De la molécule à l’organisme","Licence Sciences de la vie Parcours Neurosciences","Licence Sciences de la vie et de la Terre Parcours Mer","Licence Sciences et technologies Parcours Sciences, arts et techniques de l'image et du son",
        "Licence Sciences pour l'ingénieur Parcours Electronique, électrotechnique et automatique","Licence Sciences pour l'ingénieur Parcours Ingénierie mécanique","Licence Sciences pour l'ingénieur Parcours Matériaux pour l’électronique et l’énergie","Licence Sciences pour l'ingénieur Parcours Physique appliquée et instrumentation","Licence Pro. Métiers de l'instrumentation, de la mesure et du contrôle qualité Parcours Bureau d’étude et maintenance en instrumentation et automatismes",
        "Licence Pro. Métiers de l'instrumentation, de la mesure et du contrôle qualité Parcours Contrôle-Commande en production industrielle","Licence Pro. Métiers de l'instrumentation, de la mesure et du contrôle qualité Parcours Métrologie industrielle ","Licence Pro. Métiers de la protection et de la gestion de l'environnement Parcours Gestion et optimisation des systèmes de traitement de l'eau","Licence Pro. Qualité, hygiène, sécurité, santé, environnement Parcours Prévention et gestion des risques en santé, sécurité, environnement "};




    static String[] code = new String [] {"DCDL","DICM","ACS","CV","MISO","MPGBD","MGMPCSC","MGMPCSM","FSI","ILD","IAAA","CMB","DS","IMSA","MASS","AGREG","MF","CMAB","MMAPDM","IMD","AT","F&S","ISI","WAVES","MPE","MPPP","SFP","IMOVI","IPSI","SIBIOM","LCPC","LCPGP","LCPPC","LIPI","MIAGE","LM","LSPB","LSPMO","LSPN","LSTPM","SATIS","EEA","IM","M2E","PAI","BEM","CC","MI","GOSTE","PGRSSE"};


    public int random(int min, int max) {
        return (int) (min + (Math.random() * ((max - min) + 1)));
    }


    public void generate() throws NotFoundObjectException, ItemExistInListException, FormationParentNotFoundException {

        Formation formationfinal = new Formation("M2-Info","M2 informatique",20, 20 - 2, 15 -2 );
        formationManager.insert(formationfinal);

        Bloc bloc1 = new Bloc("S3","Semestre 3");
        bloc1.setEffectif(new Effectif(30,30,0,0));
        blocManager.insert(bloc1);
        formationManager.addBloc(formationfinal.getId(), bloc1);
        System.err.println("Bloc code = " + bloc1.getCode());

        Bloc bloc2 = new Bloc("S4","Semestre 4");
        bloc1.setEffectif(new Effectif(20,30,0,0));
        blocManager.insert(bloc2);
        formationManager.addBloc(formationfinal.getId(), bloc2);
        System.err.println("Bloc code = " + bloc2.getCode());


        for(int i=0;i<10;i++) {


            int effectif1 = random(0, 50);
            int effectif2 = random(0, 50);
            int effectif3 = random(0, 50);
            int effectif4 = random(0, 50);



            int groupe = random(2,30);

            Formation formation = new Formation(code[i],intitulé[i],groupe, groupe - 2, groupe -2 );
            formationManager.insert(formation);
            System.err.println("formation code = " + formation.getCode());

            for(int j=0; j <2 ; j++){
                Bloc bloc = new Bloc("Bloc_code"+ i + "." + j,"Bloc_intitule"+ i + "." + j);
                bloc.setEffectif(new Effectif(effectif1,effectif2,effectif3,effectif4));
                blocManager.insert(bloc);
                formationManager.addBloc(formation.getId(), bloc);
                System.err.println("Bloc code = " + bloc.getCode());



                for(int g = 0 ; g < 3 ; g++){
                        int effectif_total = random(20,50);
                        int effectifParUe = random(10,30);
                        Option option = new Option("Option_code"+ j + "." + g, "Option_Intitule"+ j + "." + g,0 );
                        optionManager.insert(option);
                        formationManager.addOption(formation.getId(), option);
                        System.err.println("Option code = " + option.getCode());
                        blocManager.linkOption(bloc.getId(), option.getId());

                    }

                    for(int z= 0; z <5; z++){
                        int CM = random(10,14);
                        int TP_TD = random(6,10);
                        int effectif_total = random(20,30);
                        UE ue = new UE("UE_code"+ j + "." + z, "UE_intitulé"+ j + "." + z, 0,CM,TP_TD,TP_TD,CM,TP_TD,TP_TD);
                        ueManager.insert(ue);
                        formationManager.addUE(formation.getId(),ue);
                        System.err.println("UE code = " + ue.getCode());
                        blocManager.linkUE(bloc.getId(), ue.getId());

                    }



            }


        }
    }

}
