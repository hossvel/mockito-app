package org.hossvel.services;

import org.hossvel.models.Examen;
import org.hossvel.repository.IExamenRepository;
import org.hossvel.repository.IPreguntaRepository;
import org.hossvel.service.ExamenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ExamenServiceImplTest {
    @Mock
    IExamenRepository iexamenRepository;
    @Mock
    IPreguntaRepository ipreguntaRepository;
    @InjectMocks
    ExamenServiceImpl examenServiceImpl;// es la implementacion
    @BeforeEach
    void setUp() {
        //MockitoAnnotations.openMocks(this); // para uso de anotaciones
        System.out.println("Inicio de Metodo");
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
        //given
        when(iexamenRepository.findAll()).thenReturn(Datos.EXAMENES);
        when(ipreguntaRepository.findPreguntasPorExamenId(6L)).thenReturn(Datos.PREGUNTAS);
        //when
        Examen examen = examenServiceImpl.findExamenPorNombreConPreguntas("Lenguaje");
        //then
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


    @Test
    void testGuardarExamen() {
        when(iexamenRepository.guardar(any(Examen.class))).thenReturn(Datos.EXAMEN1);
        Examen examen = examenServiceImpl.guardar(Datos.EXAMEN);
        assertNotNull(examen.getId());
        assertEquals(2L, examen.getId());
        assertEquals("Geografia", examen.getNombre());

        verify(iexamenRepository).guardar(any(Examen.class));

    }
    @Test
    void testGuardarExamenConPregunta() {
        // Given
        Examen newExamen = Datos.EXAMEN;
        newExamen.setPreguntas(Datos.PREGUNTAS);

        when(iexamenRepository.guardar(any(Examen.class))).then(new Answer<Examen>(){
            Long secuencia = 10L;
            @Override
            public Examen answer(InvocationOnMock invocation) throws Throwable {
                Examen examen = invocation.getArgument(0);
                examen.setId(secuencia++);
                return examen;
            }

        });

        // When
        Examen examen = examenServiceImpl.guardar(newExamen);

        // Then
        assertNotNull(examen.getId());
        assertEquals(10L, examen.getId());
        assertEquals("Física", examen.getNombre());

        verify(iexamenRepository).guardar(any(Examen.class));
        verify(ipreguntaRepository).guardarVarias(anyList());
    }
}
