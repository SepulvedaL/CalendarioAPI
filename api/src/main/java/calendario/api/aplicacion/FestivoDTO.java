package calendario.api.aplicacion;

import java.time.LocalDate;

public class FestivoDTO {
    private String festivo;
    private LocalDate fecha;

    public FestivoDTO(String festivo, LocalDate fecha) {
        this.festivo = festivo;
        this.fecha = fecha;
    }

    public String getFestivo() {
        return festivo;
    }

    public void setFestivo(String festivo) {
        this.festivo = festivo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
