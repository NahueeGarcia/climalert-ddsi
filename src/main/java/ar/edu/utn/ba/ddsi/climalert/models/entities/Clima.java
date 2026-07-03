package ar.edu.utn.ba.ddsi.climalert.models.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Clima {
    private Long id;
    private String temperatura;
    private String humedad;

    private static Double temperaturaMax = 35.0;
    private static Double humedadMax = 60.0;

    public static Double getTemperaturaMax() {
        return temperaturaMax;
    }

    public static Double getHumedadMax() {
        return humedadMax;
    }

    public Clima(String temperatura, String humedad) {
        this.temperatura = temperatura;
        this.humedad = humedad;
    }

    public Boolean presentaAlerta() {
        return Double.parseDouble(this.temperatura) > Clima.temperaturaMax && Double.parseDouble(this.humedad) > Clima.humedadMax;
    }
}
