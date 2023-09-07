package org.hossvel.service;

import org.hossvel.models.Examen;
import org.hossvel.repository.IExamenRepository;

import java.util.Optional;

public class ExamenServiceImpl implements IExamenService{
    private IExamenRepository iexamenRepository;

    public ExamenServiceImpl(IExamenRepository examenRepository) {
        this.iexamenRepository = examenRepository;

    }
    @Override
    public Optional<Examen> findExamenPorNombre(String nombre) {
        return iexamenRepository.findAll()
                .stream()
                .filter(e -> e.getNombre().contains(nombre)).findFirst();

    }
}
