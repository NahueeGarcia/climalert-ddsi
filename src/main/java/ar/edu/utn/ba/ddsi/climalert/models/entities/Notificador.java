package ar.edu.utn.ba.ddsi.climalert.models.entities;

import ar.edu.utn.ba.ddsi.climalert.models.entities.adapter.IAdapter;

public class Notificador {
    private IAdapter adapter;

    public Notificador(IAdapter adapter) {
        this.adapter = adapter;
    }

    public void notificar(String contacto, String asunto, String mensaje) {
        this.adapter.notificar(contacto, asunto, mensaje);
    }
}
