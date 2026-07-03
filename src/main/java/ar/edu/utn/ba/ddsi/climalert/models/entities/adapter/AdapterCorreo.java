package ar.edu.utn.ba.ddsi.climalert.models.entities.adapter;

import org.springframework.stereotype.Component;

@Component
public class AdapterCorreo  implements  IAdapter{
    private AdaptadaCorreo adaptadaCorreo;

    public AdapterCorreo(AdaptadaCorreo adaptadaCorreo) {
        this.adaptadaCorreo = adaptadaCorreo;
    }

    @Override
    public void notificar(String contacto, String asunto, String mensaje) {
        adaptadaCorreo.enviarCorreo(contacto, asunto, mensaje);
    }
}
