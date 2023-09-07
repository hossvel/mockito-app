package org.hossvel.service;

import org.hossvel.models.Examen;

public interface IExamenService {
    Examen findExamenPorNombre(String nombre);
}
