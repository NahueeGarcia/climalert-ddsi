package ar.edu.utn.ba.ddsi.climalert.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Propiedades {
    @JsonProperty("temp_c")
    private String temperatura;

    @JsonProperty("humidity")
    private String humedad;
}
