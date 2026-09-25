package tn.esprit.backend.util;

import tn.esprit.backend.entity.ProjetDetaille;

import java.util.List;

public class CoutCalculator {

    public static double calculerCoutTotal(List<ProjetDetaille> projets) {
        if (projets == null) {
            return 0.0;
        }
        return projets.stream()
                .mapToDouble(p -> p.getCoutProvisoire() != null ? p.getCoutProvisoire() : 0.0)
                .sum();
    }

    public static double appliquerReduction(double coutTotal, double pourcentageReduction) {
        if (pourcentageReduction < 0 || pourcentageReduction > 100) {
            throw new IllegalArgumentException("Le pourcentage doit être entre 0 et 100");
        }
        return coutTotal - (coutTotal * pourcentageReduction / 100);
    }
}
