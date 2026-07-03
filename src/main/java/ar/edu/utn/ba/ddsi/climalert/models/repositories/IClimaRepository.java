package ar.edu.utn.ba.ddsi.climalert.models.repositories;

import ar.edu.utn.ba.ddsi.climalert.models.entities.Clima;

import java.util.List;
import java.util.Optional;

public interface IClimaRepository {
    List<Clima> findAll();

    Clima findLast();

    Optional<Clima> findById(Long id);

    Clima save(Clima clima);

    void delete(Clima clima);
}
