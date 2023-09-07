package org.hossvel.service;

import org.hossvel.models.Examen;
import org.hossvel.repository.IExamenRepository;
import org.hossvel.repository.IPreguntaRepository;

import java.util.List;
import java.util.Optional;

public class ExamenServiceImpl implements IExamenService{
    private IExamenRepository iexamenRepository;
    private IPreguntaRepository ipreguntaRepository;

    public ExamenServiceImpl(IExamenRepository examenRepository, IPreguntaRepository ipreguntaRepository) {
        this.iexamenRepository = examenRepository;
        this.ipreguntaRepository = ipreguntaRepository;

    }
    @Override
    public Optional<Examen> findExamenPorNombre(String nombre) {
        return iexamenRepository.findAll()
                .stream()
                .filter(e -> e.getNombre().contains(nombre)).findFirst();

    }

    @Override
    public Examen findExamenPorNombreConPreguntas(String nombre) {
        Optional<Examen> examenOptional = findExamenPorNombre(nombre);
        Examen examen = null;
        if (examenOptional.isPresent()) {
            examen = examenOptional.orElseThrow();
            List<String> preguntas = ipreguntaRepository.findPreguntasPorExamenId(examen.getId());
            examen.setPreguntas(preguntas);
        }
        return examen;
    }
}
