package rest.reservoirapi.appConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import rest.reservoirapi.service.impl.PdfReader;

@Component
public class Pdf {

    @Bean
    PdfReader PdfReader (){
        return new PdfReader();
    }
}
