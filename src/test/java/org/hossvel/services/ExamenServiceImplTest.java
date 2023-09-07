package org.hossvel.services;

import org.hossvel.models.Examen;
import org.hossvel.repository.IExamenRepository;
import org.hossvel.service.ExamenServiceImpl;
import org.hossvel.service.IExamenService;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExamenServiceImplTest {

    @Test
    void findExamenPorNombre() {

        IExamenRepository iexamenRepository = mock(IExamenRepository.class);
        IExamenService examenService = new ExamenServiceImpl(iexamenRepository);

        when(iexamenRepository.findAll()).thenReturn(Datos.EXAMENES);

        Optional<Examen> examen = examenService.findExamenPorNombre("Lenguaje");
        assertTrue(examen.isPresent());
        assertEquals(6L,examen.orElseThrow().getId());
        assertEquals(examen.get().getNombre(),"Lenguaje");

    }
    @Test
    void findExamenPorNombreListaVacia() {
        List<Examen> datos = Collections.emptyList();
        IExamenRepository iexamenRepository = mock(IExamenRepository.class);
        IExamenService examenService = new ExamenServiceImpl(iexamenRepository);

        when(iexamenRepository.findAll()).thenReturn(datos);
        Optional<Examen> examen = examenService.findExamenPorNombre("Lenguaje");

        assertFalse(examen.isPresent());
    }
}
