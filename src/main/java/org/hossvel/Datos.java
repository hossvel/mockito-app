package org.hossvel;

import org.hossvel.models.Examen;

import java.util.Arrays;
import java.util.List;

public class Datos {
    public final static List<Examen> EXAMENES =
            List.of(
                    new Examen(5L, "Matematica"),
                    new Examen(6L, "Lenguaje"),
                    new Examen(7L, "Historia"),
                    new Examen(8L, "Ingles"),
                    new Examen(9L, "Algebra")

            );

    public final static List<String> PREGUNTAS = Arrays.asList(
            "aritmética",
            "integrales",
            "derivadas",
            "trigonometría",
            "geometría",
            "programacion"
    );
    public final static Examen EXAMEN = new Examen(null, "Física");
    public final static Examen EXAMEN1 = new Examen(2L, "Geografia");

    public final static List<Examen> EXAMENES_ID_NULL = Arrays.asList(
            new Examen(null, "Matemáticas"),
            new Examen(null, "Lenguaje"),
            new Examen(null, "Historia")
    );

    public final static List<Examen> EXAMENES_ID_NEGATIVOS = Arrays.asList(
            new Examen(-5L, "Matemáticas"),
            new Examen(-6L, "Lenguaje"),
            new Examen(null, "Historia"),
            new Examen(8L, "Arte")
    );

}