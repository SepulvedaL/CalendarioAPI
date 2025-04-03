package calendario.api.core;


import java.util.List;

import calendario.api.aplicacion.FestivoDTO;
import calendario.api.dominio.CalendarioEntidad;

public interface ICalendarioService {

    List<CalendarioEntidad> listarCalendario(int anio);
    List<FestivoDTO> listarFestivos(int anio);
    boolean generarCalendario(int anio);
    
}
