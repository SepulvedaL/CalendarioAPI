package calendario.api.presentacion;

import java.util.List;

import org.springframework.http.HttpStatus;
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
        boolean generado = calendarioFacade.generarCalendario(anio);
        if (!generado) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El calendario del año " + anio + " ya existe.");
        }
        return ResponseEntity.ok("Calendario generado con éxito para el año " + anio);
    }

    @GetMapping("/{anio}")
    public ResponseEntity<List<CalendarioEntidad>> listarCalendario(@PathVariable int anio) {
        List<CalendarioEntidad> calendario = calendarioFacade.listarCalendario(anio);
        if (calendario == null || calendario.isEmpty()) {
            calendarioFacade.generarCalendario(anio);
            calendario = calendarioFacade.listarCalendario(anio);
            return ResponseEntity.ok(calendario);
            // return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(calendario);
    }

    @GetMapping("/listar-festivos/{anio}")
    public ResponseEntity<List<FestivoDTO>> listarFestivos(@PathVariable int anio) {
        List<FestivoDTO> calendario = calendarioFacade.listarFestivos(anio);
        if (calendario == null || calendario.isEmpty()) {
            calendarioFacade.generarCalendario(anio);
            calendario = calendarioFacade.listarFestivos(anio);
            return ResponseEntity.ok(calendario);
            // return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(calendario);
        //return calendarioFacade.listarFestivos(anio);
    }
}
