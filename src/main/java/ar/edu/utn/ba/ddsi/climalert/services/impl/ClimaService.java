package ar.edu.utn.ba.ddsi.climalert.services.impl;

import ar.edu.utn.ba.ddsi.climalert.dto.ClimaDTO;
import ar.edu.utn.ba.ddsi.climalert.models.entities.Clima;
import ar.edu.utn.ba.ddsi.climalert.models.entities.Notificador;
import ar.edu.utn.ba.ddsi.climalert.models.repositories.IClimaRepository;
import ar.edu.utn.ba.ddsi.climalert.services.IClimaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import org.springframework.stereotype.Service;

@Service
public class ClimaService implements IClimaService {

    private final Notificador notificador;
    private final IClimaRepository climaRepository;
    private final RestTemplate restTemplate;
    private String baseUrl;
    private String ciudad;
    private String apiKey;
    private String correosDestino;

    public ClimaService(Notificador notificador, IClimaRepository climaRepository, RestTemplate restTemplate, @Value("${weather-api.base-url}") String baseUrl, @Value("${ciudad-consulta}") String ciudad, @Value("${weather-api.api-key}") String apiKey, @Value("${correos-destino}") String correosDestino) {
        this.notificador = notificador;
        this.climaRepository = climaRepository;
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.ciudad = ciudad;
        this.apiKey = apiKey;
        this.correosDestino = correosDestino;
    }

    @Scheduled(fixedDelay = 300000)
    @Override
    public void buscarClima() {
        URI uri = UriComponentsBuilder.fromUriString(this.baseUrl)
                .path("/v1/current.json")
                .queryParam("key", this.apiKey)
                .queryParam("q", this.ciudad)
                .build()
                .toUri();

        ClimaDTO clima = this.restTemplate.getForObject(uri, ClimaDTO.class);

        if (clima != null) {
            this.climaRepository.save(toClima(clima));
        }
    }

    private Clima toClima(ClimaDTO climaDTO) {
        return new Clima(climaDTO.getPropiedades().getTemperatura(), climaDTO.getPropiedades().getHumedad());
    }

    @Scheduled(fixedDelay = 60000)
    @Override
    public void procesarAlertas() {
        Clima clima = this.climaRepository.findLast();

        if (clima != null) {
            if (clima.presentaAlerta()) {
                String asunto = "Alerta en " + this.ciudad;
                String mensaje = "Alerta meteorológica detectada.\n" +
                        "Detalle completo del clima:\n" +
                        "- Temperatura actual: " + clima.getTemperatura() + " °C\n" +
                        "- Humedad actual: " + clima.getHumedad() + " %\n" +
                        "Las condiciones superan el límite de " + Clima.getTemperaturaMax() + "°C de temperatura y " + Clima.getHumedadMax() + "% de humedad.";

                if (this.correosDestino != null && !this.correosDestino.trim().isEmpty()) {
                    String[] destinatarios = this.correosDestino.trim().split("\\s+");
                    for (String destinatario : destinatarios) {
                        if (!destinatario.isEmpty()) {
                            notificador.notificar(destinatario, asunto, mensaje);
                        }
                    }
                }
            }
        }
    }
}
