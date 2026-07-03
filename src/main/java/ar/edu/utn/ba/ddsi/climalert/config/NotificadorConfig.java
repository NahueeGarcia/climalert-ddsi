package ar.edu.utn.ba.ddsi.climalert.config;

import ar.edu.utn.ba.ddsi.climalert.models.entities.Notificador;
import ar.edu.utn.ba.ddsi.climalert.models.entities.adapter.AdapterCorreo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificadorConfig {
    @Bean
    public Notificador notificadorCorreo(AdapterCorreo adapterCorreo) {
        return new Notificador(adapterCorreo);
    }
}
