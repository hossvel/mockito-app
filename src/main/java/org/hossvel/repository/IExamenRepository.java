package org.hossvel.repository;

import org.hossvel.models.Examen;

import java.util.List;

public interface IExamenRepository {
    List<Examen> findAll();

    Examen guardar(Examen examen);
}
