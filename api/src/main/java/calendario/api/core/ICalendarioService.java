package calendario.api.core;


import java.util.List;

import calendario.api.dominio.CalendarioEntidad;

public interface ICalendarioService {
    public List<CalendarioEntidad> listar();
    
}
