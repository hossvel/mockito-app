package org.hossvel.service;

import org.hossvel.models.Examen;

import java.util.Optional;

public interface IExamenService {
    Optional<Examen> findExamenPorNombre(String nombre);
}
