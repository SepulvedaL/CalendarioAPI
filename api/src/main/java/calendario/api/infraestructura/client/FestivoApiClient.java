package calendario.api.infraestructura.client;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import calendario.api.aplicacion.CalendarioDTO;
// import calendario.api.aplicacion.TipoDTO;


// Cliente para consumir la API de Express.js.
@Service
public class FestivoApiClient {
    
    private final WebClient webClient;

    public FestivoApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:3030").build(); // URL de la API en Express.js
    }

    public List<CalendarioDTO> obtenerFestivosPorAnio(int anio) {
        return webClient.get()
                .uri("/festivos/{anio}", anio)
                .retrieve()
                .bodyToFlux(CalendarioDTO.class)
                .collectList()
                .block(); // Bloquea hasta obtener respuesta
    }

    // public List<TipoDTO> obtenerTiposFestivos() {
    //     return webClient.get()
    //             .uri("/tiposFestivos")
    //             .retrieve()
    //             .bodyToFlux(TipoDTO.class)
    //             .collectList()
    //             .block();
    // }
}
