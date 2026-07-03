package ar.edu.utn.ba.ddsi.climalert.models.entities.adapter;

public interface IAdapter {
    void notificar(String contacto, String asunto, String mensaje);
}
