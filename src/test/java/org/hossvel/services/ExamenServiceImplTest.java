package org.hossvel.services;

import org.hossvel.models.Examen;
import org.hossvel.repository.ExamenRepositoryImpl;
import org.hossvel.repository.IExamenRepository;
import org.hossvel.service.ExamenServiceImpl;
import org.hossvel.service.IExamenService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExamenServiceImplTest {

    @Test
    void findExamenPorNombre() {

        IExamenRepository iexamenRepository = new ExamenRepositoryImpl();
        IExamenService iexamenService = new ExamenServiceImpl(iexamenRepository);

        Examen examen = iexamenService.findExamenPorNombre("Lenguaje");
        assertNotNull(examen);
        assertEquals(6L,examen.getId());
        assertEquals(examen.getNombre(),"Lenguaje");

    }
}
