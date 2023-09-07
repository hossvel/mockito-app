package org.hossvel.services;

import org.hossvel.models.Examen;
import org.hossvel.repository.IExamenRepository;
import org.hossvel.service.ExamenServiceImpl;
import org.hossvel.service.IExamenService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExamenServiceImplTest {

    @Test
    void findExamenPorNombre() {

        IExamenRepository iexamenRepository = mock(IExamenRepository.class);
        IExamenService iexamenService = new ExamenServiceImpl(iexamenRepository);

        when(iexamenRepository.findAll()).thenReturn(Datos.EXAMENES);

        Examen examen = iexamenService.findExamenPorNombre("Lenguaje");
        assertNotNull(examen);
        assertEquals(6L,examen.getId());
        assertEquals(examen.getNombre(),"Lenguaje");

    }
}
