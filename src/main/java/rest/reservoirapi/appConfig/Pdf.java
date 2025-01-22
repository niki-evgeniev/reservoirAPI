package rest.reservoirapi.appConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Pdf {

    @Bean
    PdfReader PdfReader (){
        return new PdfReader();
    }
}
