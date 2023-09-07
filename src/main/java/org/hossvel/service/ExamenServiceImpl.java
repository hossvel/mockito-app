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
    public Examen findExamenPorNombre(String nombre) {
        Optional<Examen> exaopt = iexamenRepository.findAll()
                .stream()
                .filter(e -> e.getNombre().contains(nombre)).findFirst();
        Examen ex = null;
        if(exaopt.isPresent()) ex = exaopt.orElseThrow();
        return ex;
    }
}
