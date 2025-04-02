package calendario.api.presentacion;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import calendario.api.aplicacion.CalendarioDTO;
import calendario.api.aplicacion.CalendarioFacade;
import calendario.api.aplicacion.FestivoDTO;
import calendario.api.dominio.CalendarioEntidad;


// Controlador REST para gestionar el calendario.
@RestController
@RequestMapping("/calendario")
public class CalendarioController {
    
    private final CalendarioFacade calendarioFacade;

    public CalendarioController(CalendarioFacade calendarioFacade) {
        this.calendarioFacade = calendarioFacade;
    }

    @PostMapping("/generar/{anio}")
    public ResponseEntity<String> generarCalendario(@PathVariable int anio) {
        boolean resultado = calendarioFacade.generarCalendario(anio);
        if (resultado) {
            return ResponseEntity.ok("Calendario generado exitosamente para el año " + anio);
        }
        return ResponseEntity.badRequest().body("No se pudo generar el calendario");
    }

    @GetMapping("/{anio}")
    public ResponseEntity<List<CalendarioEntidad>> listarCalendario(@PathVariable int anio) {
        List<CalendarioEntidad> calendario = calendarioFacade.listarCalendario(anio);
        if (calendario == null || calendario.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(calendario);
    }

    @GetMapping("/listar-festivos/{anio}")
    public List<FestivoDTO> listarFestivos(@PathVariable int anio) {
        return calendarioFacade.listarFestivos(anio);
    }
}
