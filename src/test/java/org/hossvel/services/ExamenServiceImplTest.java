package org.hossvel.services;

import org.hossvel.models.Examen;
import org.hossvel.repository.IExamenRepository;
import org.hossvel.repository.IPreguntaRepository;
import org.hossvel.service.ExamenServiceImpl;
import org.hossvel.service.IExamenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExamenServiceImplTest {
    IExamenRepository iexamenRepository;
    IPreguntaRepository ipreguntaRepository;
    IExamenService examenServiceImpl;
    @BeforeEach
    void setUp() {
        System.out.println("Inicio de Metodo");
        iexamenRepository   = mock(IExamenRepository.class);
        ipreguntaRepository   = mock(IPreguntaRepository.class);
        examenServiceImpl = new ExamenServiceImpl(iexamenRepository, ipreguntaRepository);
    }

    @Test
    void findExamenPorNombre() {

        when(iexamenRepository.findAll()).thenReturn(Datos.EXAMENES);

        Optional<Examen> examen = examenServiceImpl.findExamenPorNombre("Lenguaje");
        assertTrue(examen.isPresent());
        assertEquals(6L,examen.orElseThrow().getId());
        assertEquals(examen.get().getNombre(),"Lenguaje");

    }
    @Test
    void findExamenPorNombreListaVacia() {
        List<Examen> datos = Collections.emptyList();

        when(iexamenRepository.findAll()).thenReturn(datos);
        Optional<Examen> examen = examenServiceImpl.findExamenPorNombre("Lenguaje");
        assertFalse(examen.isPresent());
    }

    @Test
    void testPreguntasExamenId() {
        when(iexamenRepository.findAll()).thenReturn(Datos.EXAMENES);
        when(ipreguntaRepository.findPreguntasPorExamenId(6L)).thenReturn(Datos.PREGUNTAS);
        Examen examen = examenServiceImpl.findExamenPorNombreConPreguntas("Lenguaje");
        assertEquals(6, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("integrales"));

    }
    @Test
    void testPreguntasExamenAny() {
        when(iexamenRepository.findAll()).thenReturn(Datos.EXAMENES);
        when(ipreguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        Examen examen = examenServiceImpl.findExamenPorNombreConPreguntas("Lenguaje");
        assertEquals(6, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("integrales"));

    }

    @Test
    void testPreguntasExamenVerify() {
        when(iexamenRepository.findAll()).thenReturn(Datos.EXAMENES);
        when(ipreguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        Examen examen = examenServiceImpl.findExamenPorNombreConPreguntas("Lenguaje");
        assertEquals(6, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("integrales"));

        verify(iexamenRepository).findAll();
        verify(ipreguntaRepository).findPreguntasPorExamenId(anyLong());

    }

    @Test
    void testPreguntasExamenIdVerify() {
        when(iexamenRepository.findAll()).thenReturn(Datos.EXAMENES);
        when(ipreguntaRepository.findPreguntasPorExamenId(6L)).thenReturn(Datos.PREGUNTAS);
        Examen examen = examenServiceImpl.findExamenPorNombreConPreguntas("Lenguaje");
        assertEquals(6, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("integrales"));
        verify(iexamenRepository).findAll();
        verify(ipreguntaRepository).findPreguntasPorExamenId(6L);

    }

    @Test
    void testNoExisteExamenVerify() {
        // given
        when(iexamenRepository.findAll()).thenReturn(Collections.emptyList());
        when(ipreguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

        //when
        Examen examen = examenServiceImpl.findExamenPorNombreConPreguntas("Lenguaje");

        //then
        assertNull(examen);
        verify(iexamenRepository).findAll();
       // verify(ipreguntaRepository).findPreguntasPorExamenId(anyLong()); // No llama porq no encontro examen
    }
}
