package ar.edu.utn.ba.ddsi.climalert.models.entities.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.resend.*;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;

@Component
public class AdaptadaCorreo {

    @Value("${resend.api_key}")
    private String apiKey;

    @Value("${resend.from_email}")
    private String fromEmail;

    public Boolean enviarCorreo(String contacto, String asunto, String mensaje) {
        Resend resend = new Resend(this.apiKey);

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from(this.fromEmail)
                .to(contacto)
                .subject(asunto)
                .text(mensaje)
                .build();

        try {
            CreateEmailResponse data = resend.emails().send(params);
            return data != null && data.getId() != null;
        } catch (Exception e) {
            return false;
        }
    }
}