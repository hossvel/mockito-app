package org.hossvel.services;

import org.hossvel.models.Examen;
import org.hossvel.repository.ExamenRepositoryImpl;
import org.hossvel.repository.IExamenRepository;
import org.hossvel.repository.IPreguntaRepository;
import org.hossvel.repository.PreguntaRepositoryImpl;
import org.hossvel.service.ExamenServiceImpl;
import org.hossvel.service.IExamenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ExamenServiceImplSpyTest {
    @Spy
    ExamenRepositoryImpl iexamenRepository;
    @Spy
    PreguntaRepositoryImpl ipreguntaRepository;
    @InjectMocks
    ExamenServiceImpl examenServiceImpl;// es la implementacion

    @Captor
    ArgumentCaptor<Long> captor;
    @BeforeEach
    void setUp() {
        //MockitoAnnotations.openMocks(this); // para uso de anotaciones
        System.out.println("Inicio de Metodo");
    }
    @Test
    void testSpy2() {
        IExamenRepository examenRepository = spy(ExamenRepositoryImpl.class);
        IPreguntaRepository preguntaRepository = spy(PreguntaRepositoryImpl.class);
        IExamenService examenService = new ExamenServiceImpl(examenRepository, preguntaRepository);

        List<String> preguntas = Arrays.asList("aritmetica");
        doReturn(preguntas).when(preguntaRepository).findPreguntasPorExamenId(anyLong());

        Examen examen = examenService.findExamenPorNombreConPreguntas("Lenguaje");
        assertEquals(6L, examen.getId());
        assertEquals("Lenguaje", examen.getNombre());
        assertEquals(1, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("aritmetica"));

        verify(examenRepository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(anyLong());
    }
}
