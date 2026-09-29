package tn.esprit.backend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.backend.entity.Projet;
import tn.esprit.backend.entity.ProjetDetaille;
import tn.esprit.backend.repository.ProjetDetailleRepository;
import tn.esprit.backend.repository.ProjetRepository;
import tn.esprit.backend.service.impl.ProjetDetailleServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjetDetailleServiceTest {

    @Mock
    ProjetDetailleRepository projetDetailleRepository;

    @Mock
    ProjetRepository projetRepository;

    @InjectMocks
    ProjetDetailleServiceImpl service;

    private ProjetDetaille creer(Long id, double cout) {
        return ProjetDetaille.builder().id(id).description("Projet " + id)
                .technologie("Spring").coutProvisoire(cout).build();
    }

    @Test
    void addProjetDetaille_retourneLeProjetEnregistre() {
        ProjetDetaille p = creer(1L, 1000.0);
        when(projetDetailleRepository.save(p)).thenReturn(p);

        ProjetDetaille resultat = service.addProjetDetaille(p);

        assertEquals(1000.0, resultat.getCoutProvisoire());
        verify(projetDetailleRepository).save(p);
    }

    @Test
    void updateProjetDetaille_retourneLeProjetModifie() {
        ProjetDetaille p = creer(1L, 2000.0);
        when(projetDetailleRepository.save(p)).thenReturn(p);

        assertEquals(2000.0, service.updateProjetDetaille(p).getCoutProvisoire());
    }

    @Test
    void getProjetDetailleById_existant_retourneLeProjet() {
        when(projetDetailleRepository.findById(1L)).thenReturn(Optional.of(creer(1L, 500.0)));

        ProjetDetaille resultat = service.getProjetDetailleById(1L);

        assertNotNull(resultat);
        assertEquals(1L, resultat.getId());
    }

    @Test
    void getProjetDetailleById_inexistant_retourneNull() {
        when(projetDetailleRepository.findById(99L)).thenReturn(Optional.empty());

        assertNull(service.getProjetDetailleById(99L));
    }

    @Test
    void getAllProjetsDetailles_retourneToutesLesLignes() {
        when(projetDetailleRepository.findAll())
                .thenReturn(Arrays.asList(creer(1L, 100.0), creer(2L, 200.0)));

        List<ProjetDetaille> resultat = service.getAllProjetsDetailles();

        assertEquals(2, resultat.size());
    }

    @Test
    void getProjetDetaillesByProjet_retourneLesProjetsDuProjet() {
        when(projetDetailleRepository.findByProjetId(5L))
                .thenReturn(Arrays.asList(creer(1L, 100.0)));

        assertEquals(1, service.getProjetDetaillesByProjet(5L).size());
    }

    @Test
    void deleteProjetDetaille_appelleLeRepository() {
        service.deleteProjetDetaille(1L);

        verify(projetDetailleRepository, times(1)).deleteById(1L);
    }

    @Test
    void assignProjetDetailleToProjet_associeLeProjet() {
        ProjetDetaille pd = creer(1L, 300.0);
        Projet projet = new Projet();
        when(projetDetailleRepository.findById(1L)).thenReturn(Optional.of(pd));
        when(projetRepository.findById(5L)).thenReturn(Optional.of(projet));
        when(projetDetailleRepository.save(pd)).thenReturn(pd);

        ProjetDetaille resultat = service.assignProjetDetailleToProjet(1L, 5L);

        assertSame(projet, resultat.getProjet());
    }
}
