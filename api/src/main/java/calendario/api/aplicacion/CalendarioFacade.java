package calendario.api.aplicacion;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import calendario.api.core.ICalendarioService;
import calendario.api.dominio.CalendarioEntidad;
import calendario.api.dominio.TipoEntidad;
import calendario.api.infraestructura.client.FestivoApiClient;
import calendario.api.infraestructura.repositorio.ICalendarioRepository;
import calendario.api.infraestructura.repositorio.ITipoRepository;

// Orquesta la generación del calendario de festivos, llamando a ICalendarioService y a FestivoApiClient
@Service
public class CalendarioFacade implements ICalendarioService {
    private final FestivoApiClient festivoApiClient;
    private final ICalendarioRepository calendarioRepository;
    private final ITipoRepository tipoRepository;

    public CalendarioFacade(FestivoApiClient festivoApiClient, ICalendarioRepository calendarioRepository,
            ITipoRepository tipoRepository) {
        this.festivoApiClient = festivoApiClient;
        this.calendarioRepository = calendarioRepository;
        this.tipoRepository = tipoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CalendarioEntidad> listarCalendario(int anio) {
        return calendarioRepository.findAll()
                .stream()
                .filter(calendario -> Year.from(calendario.getFecha()).getValue() == anio)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FestivoDTO> listarFestivos(int anio) {
        return calendarioRepository.findAll()
                .stream()
                .filter(calendario -> Year.from(calendario.getFecha()).getValue() == anio &&
                        "Día Festivo".equals(calendario.getTipo().getTipo()))
                .map(calendario -> new FestivoDTO(
                        calendario.getDescripcion() != null ? calendario.getDescripcion() : "Festivo sin descripción",
                        calendario.getFecha()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean generarCalendario(int anio) {
        // Verificar si el calendario ya existe antes de generarlo
        if (!calendarioRepository.findByFechaBetween(LocalDate.of(anio, 1, 1), LocalDate.of(anio, 12, 31)).isEmpty()) {
            System.out.println("El calendario del año " + anio + " ya existe. No se generará nuevamente.");
            return false;
        }

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
                    descripcion = "Festivo sin descripción"; // Asignar valor por defecto
                }
                tipo = tipoRepository.findById(3L).orElse(null); // ID 3 = Festivo
            }

            // Asegurar que `tipo` no sea null antes de guardar
            if (tipo == null) {
                System.err.println(" ERROR: No se encontró el tipo en la BD, asignando 'Día Laboral'.");
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
