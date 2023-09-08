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
public class ExamenServiceImplTest {
    @Mock
    ExamenRepositoryImpl iexamenRepository;
    @Mock
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


    @Test
    void testManejoException() {
        when(iexamenRepository.findAll()).thenReturn(Datos.EXAMENES_ID_NULL);
        when(ipreguntaRepository.findPreguntasPorExamenId(isNull())).thenThrow(new IllegalArgumentException());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            examenServiceImpl.findExamenPorNombreConPreguntas("Lenguaje");
        });
        assertEquals(IllegalArgumentException.class, exception.getClass());

        verify(iexamenRepository).findAll();
        verify(ipreguntaRepository).findPreguntasPorExamenId(isNull());

    }

    @Test
    void testArgumentMatchers() {
        when(iexamenRepository.findAll()).thenReturn(Datos.EXAMENES);
        when(ipreguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        examenServiceImpl.findExamenPorNombreConPreguntas("Lenguaje");

        verify(iexamenRepository).findAll();
        verify(ipreguntaRepository).findPreguntasPorExamenId(argThat(x -> x.equals(6L)));
    }
    @Test
    void testArgumentMatchers2() {
        when(iexamenRepository.findAll()).thenReturn(Datos.EXAMENES);
        when(ipreguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        examenServiceImpl.findExamenPorNombreConPreguntas("Lenguaje");

        verify(iexamenRepository).findAll();
        verify(ipreguntaRepository).findPreguntasPorExamenId(argThat(arg -> arg != null && arg >= 5L));

    }

    @Test
    void testArgumentMatchers3() {
        when(iexamenRepository.findAll()).thenReturn(Datos.EXAMENES);
        when(ipreguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        examenServiceImpl.findExamenPorNombreConPreguntas("Lenguaje");

        verify(iexamenRepository).findAll();
        verify(ipreguntaRepository).findPreguntasPorExamenId(argThat(arg -> arg != null && arg.equals(6L)));

    }

    @Test
    void testArgumentMatchers4() {
        when(iexamenRepository.findAll()).thenReturn(Datos.EXAMENES);
        when(ipreguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        examenServiceImpl.findExamenPorNombreConPreguntas("Lenguaje");

        verify(iexamenRepository).findAll();
        verify(ipreguntaRepository).findPreguntasPorExamenId(eq(6L));

    }

    @Test
    void testArgumentMatchers5() {
        when(iexamenRepository.findAll()).thenReturn(Datos.EXAMENES_ID_NEGATIVOS);
        when(ipreguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        examenServiceImpl.findExamenPorNombreConPreguntas("Lenguaje");

        verify(iexamenRepository).findAll();
        verify(ipreguntaRepository).findPreguntasPorExamenId(argThat(new MiArgsMatchers()));

    }
    @Test
    void testArgumentMatchersnull() {
        when(iexamenRepository.findAll()).thenReturn(Datos.EXAMENES_ID_NEGATIVOS);
        when(ipreguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        // examenServiceImpl.findExamenPorNombreConPreguntas("Ciencias");// no existe en la lista
        // examenServiceImpl.findExamenPorNombreConPreguntas("Ciencias");//  existe en la lista pero id es null
        examenServiceImpl.findExamenPorNombreConPreguntas("Arte");

        verify(iexamenRepository).findAll();
        verify(ipreguntaRepository).findPreguntasPorExamenId(argThat(new MiArgsMatchers()));

    }

    @Test
    void testArgumentMatchers6() {
        when(iexamenRepository.findAll()).thenReturn(Datos.EXAMENES_ID_NEGATIVOS);
        when(ipreguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        examenServiceImpl.findExamenPorNombreConPreguntas("Arte");

        verify(iexamenRepository).findAll();
        verify(ipreguntaRepository).findPreguntasPorExamenId(argThat( (argument) -> argument != null && argument > 0));

    }

    public static class MiArgsMatchers implements ArgumentMatcher<Long> {
        private Long argument;
        @Override
        public boolean matches(Long argument) {
            this.argument = argument;
            return argument != null && argument > 0;
        }
        @Override
        public String toString() {
            return "es para un mensaje personalizado de error " +
                    "que imprime mockito en caso de que falle el test "
                    + argument + " debe ser un entero positivo";
        }
    }


    @Test
    void testArgumentCaptor() {
        when(iexamenRepository.findAll()).thenReturn(Datos.EXAMENES);
        //when(ipreguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);// no es nesesario
        examenServiceImpl.findExamenPorNombreConPreguntas("Lenguaje");

        ArgumentCaptor<Long> captor1 = ArgumentCaptor.forClass(Long.class);
        verify(ipreguntaRepository).findPreguntasPorExamenId(captor1.capture());

        assertEquals(6L, captor1.getValue());
    }

    @Test
    void testArgumentCaptor1() {
        when(iexamenRepository.findAll()).thenReturn(Datos.EXAMENES);
        examenServiceImpl.findExamenPorNombreConPreguntas("Lenguaje");
        verify(ipreguntaRepository).findPreguntasPorExamenId(captor.capture());

        assertEquals(6L, captor.getValue());
    }

    @Test
    void testDoThrow() {
        Examen examen = Datos.EXAMEN;
        examen.setPreguntas(Datos.PREGUNTAS);
        doThrow(IllegalArgumentException.class).when(ipreguntaRepository).guardarVarias(anyList());

        assertThrows(IllegalArgumentException.class, () -> {
            examenServiceImpl.guardar(examen);
        });
    }

    @Test
    void testDoAnswer() {
        when(iexamenRepository.findAll()).thenReturn(Datos.EXAMENES);
        doAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            return id == 6L? Datos.PREGUNTAS: Collections.emptyList();
        }).when(ipreguntaRepository).findPreguntasPorExamenId(anyLong());

        Examen examen =   examenServiceImpl.findExamenPorNombreConPreguntas("Lenguaje");
        assertEquals(6, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("geometría"));
        assertEquals(6L, examen.getId());
        assertEquals("Lenguaje", examen.getNombre());

        verify(ipreguntaRepository).findPreguntasPorExamenId(anyLong());
    }

    @Test
    void testDoCallRealMethod() {
        when(iexamenRepository.findAll()).thenReturn(Datos.EXAMENES);
//        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        doCallRealMethod().when(ipreguntaRepository).findPreguntasPorExamenId(anyLong());
        Examen examen = examenServiceImpl.findExamenPorNombreConPreguntas("Lenguaje");
        assertEquals(6L, examen.getId());
        assertEquals("Lenguaje", examen.getNombre());

    }

    @Test
    void testSpy() {
        IExamenRepository examenRepository = spy(ExamenRepositoryImpl.class);
        IPreguntaRepository preguntaRepository = spy(PreguntaRepositoryImpl.class);
        IExamenService examenService = new ExamenServiceImpl(examenRepository, preguntaRepository);

        Examen examen = examenService.findExamenPorNombreConPreguntas("Lenguaje");
        assertEquals(6L, examen.getId());
        assertEquals("Lenguaje", examen.getNombre());
        assertEquals(6, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("aritmética"));

        verify(examenRepository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(anyLong());
    }
}
