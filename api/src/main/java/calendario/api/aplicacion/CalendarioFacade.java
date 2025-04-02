package calendario.api.aplicacion;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import calendario.api.dominio.CalendarioEntidad;
import calendario.api.dominio.TipoEntidad;
import calendario.api.infraestructura.client.FestivoApiClient;
import calendario.api.infraestructura.repositorio.CalendarioRepository;
import calendario.api.infraestructura.repositorio.TipoRepository;

// Orquesta la generación del calendario de festivos, llamando a CalendarioService y a FestivoApiClient
@Service
public class CalendarioFacade {
    private final FestivoApiClient festivoApiClient;
    private final CalendarioRepository calendarioRepository;
    private final TipoRepository tipoRepository;

    public CalendarioFacade(FestivoApiClient festivoApiClient, CalendarioRepository calendarioRepository,
            TipoRepository tipoRepository) {
        this.festivoApiClient = festivoApiClient;
        this.calendarioRepository = calendarioRepository;
        this.tipoRepository = tipoRepository;
    }

    @Transactional(readOnly = true)
    public List<CalendarioEntidad> listarCalendario(int anio) {
        return calendarioRepository.findAll()
                .stream()
                .filter(calendario -> Year.from(calendario.getFecha()).getValue() == anio)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FestivoDTO> listarFestivos(int anio) {
        return calendarioRepository.findAll()
                .stream()
                .filter(calendario -> Year.from(calendario.getFecha()).getValue() == anio &&
                        "Festivo".equals(calendario.getTipo().getTipo()))
                .map(calendario -> new FestivoDTO(
                        calendario.getDescripcion() != null ? calendario.getDescripcion() : "Festivo sin descripción",
                        calendario.getFecha()))
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean generarCalendario(int anio) {
        // Obtener los festivos desde la API de Express.js
        List<CalendarioDTO> festivos = festivoApiClient.obtenerFestivosPorAnio(anio);

        // Generar todos los días del año
        LocalDate inicio = LocalDate.of(anio, 1, 1);
        LocalDate fin = LocalDate.of(anio, 12, 31);

        while (!inicio.isAfter(fin)) {
            LocalDate fechaActual = inicio; // Variable local final para usar dentro del lambda
            String descripcion = "Día laboral";
            TipoEntidad tipo = tipoRepository.findById(1L).orElse(null); // ID 1 = Día Laboral

            // Verificar si es fin de semana
            if (fechaActual.getDayOfWeek() == DayOfWeek.SATURDAY || fechaActual.getDayOfWeek() == DayOfWeek.SUNDAY) {
                descripcion = "Fin de Semana";
                tipo = tipoRepository.findById(2L).orElse(null); // ID 2 = Fin de Semana
            }

            // Verificar si es festivo
            Optional<CalendarioDTO> festivoEncontrado = festivos.stream()
                    .filter(f -> f.getFecha().equals(fechaActual))
                    .findFirst();

            if (festivoEncontrado.isPresent()) {
                descripcion = festivoEncontrado.get().getNombre();
                if (descripcion == null || descripcion.isEmpty()) {
                    descripcion = "Festivo sin descripción"; // 🔹 Asignar valor por defecto
                }
                tipo = tipoRepository.findById(3L).orElse(null); // ID 3 = Festivo
            }

            // 🔹 Asegurar que `tipo` no sea null antes de guardar
            if (tipo == null) {
                System.err.println("⚠️ ERROR: No se encontró el tipo en la BD, asignando 'Día Laboral'.");
                tipo = tipoRepository.findById(1L)
                        .orElseThrow(() -> new IllegalStateException("No se encontró ningún tipo en la base de datos"));
            }

            // Guardar en la base de datos
            CalendarioEntidad calendario = new CalendarioEntidad();
            calendario.setFecha(fechaActual);
            calendario.setTipo(tipo);
            calendario.setDescripcion(descripcion);
            calendarioRepository.save(calendario);

            inicio = inicio.plusDays(1);
        }
        return true;
    }

}
