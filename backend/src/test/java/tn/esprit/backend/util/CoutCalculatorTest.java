package tn.esprit.backend.util;

import org.junit.jupiter.api.Test;
import tn.esprit.backend.entity.ProjetDetaille;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CoutCalculatorTest {

    @Test
    void calculerCoutTotal_listeNull_retourneZero() {
        assertEquals(0.0, CoutCalculator.calculerCoutTotal(null));
    }

    @Test
    void calculerCoutTotal_listeVide_retourneZero() {
        assertEquals(0.0, CoutCalculator.calculerCoutTotal(Collections.emptyList()));
    }

    @Test
    void calculerCoutTotal_plusieursProjets_sommeCorrecte() {
        ProjetDetaille p1 = new ProjetDetaille();
        p1.setCoutProvisoire(1000.0);

        ProjetDetaille p2 = new ProjetDetaille();
        p2.setCoutProvisoire(2500.0);

        List<ProjetDetaille> projets = Arrays.asList(p1, p2);

        assertEquals(3500.0, CoutCalculator.calculerCoutTotal(projets));
    }

    @Test
    void appliquerReduction_casNormal_calculCorrect() {
        double resultat = CoutCalculator.appliquerReduction(1000.0, 10);
        assertEquals(900.0, resultat);
    }

    @Test
    void appliquerReduction_reductionZero_retourneMemeMontant() {
        double resultat = CoutCalculator.appliquerReduction(1000.0, 0);
        assertEquals(1000.0, resultat);
    }

    @Test
    void appliquerReduction_pourcentageInvalide_leveException() {
        assertThrows(IllegalArgumentException.class, () ->
                CoutCalculator.appliquerReduction(1000.0, 150));
    }
}
