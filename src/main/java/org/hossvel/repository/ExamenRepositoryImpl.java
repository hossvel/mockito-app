package org.hossvel.repository;

import org.hossvel.models.Examen;

import java.util.List;

public class ExamenRepositoryImpl implements IExamenRepository {
    @Override
    public List<Examen> findAll() {
        return List.of(
                new Examen(5L, "Matematica"),
                new Examen(6L, "Lenguaje"),
                new Examen(7L, "Historia"),
                new Examen(8L, "Ingles"),
                new Examen(9L, "Algebra")

        );
    }
}
