package ar.edu.utn.ba.ddsi.climalert.models.repositories.impl;

import ar.edu.utn.ba.ddsi.climalert.models.entities.Clima;
import ar.edu.utn.ba.ddsi.climalert.models.repositories.IClimaRepository;
import ar.edu.utn.ba.ddsi.climalert.utils.GeneradorIdSecuencial;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Repository;

@Repository
public class ClimaRepository implements IClimaRepository {
    private final List<Clima> climas = new CopyOnWriteArrayList<>();
    private final GeneradorIdSecuencial generadorId = new GeneradorIdSecuencial();

    @Override
    public List<Clima> findAll() {
        return new ArrayList<>(climas);
    }

    @Override
    public Clima findLast() {
        return this.climas.isEmpty() ? null : this.climas.getLast();
    }

    @Override
    public Optional<Clima> findById(Long id) {
        return climas.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    @Override
    public Clima save(Clima clima) {
        if (clima.getId() == null) {
            clima.setId(generadorId.siguiente());
            climas.add(clima);
            return clima;
        }
        delete(clima);
        climas.add(clima);
        return clima;
    }

    @Override
    public void delete(Clima clima) {
        if (clima.getId() == null) {
            return;
        }
        climas.removeIf(c -> c.getId().equals(clima.getId()));
    }
}
