package calendario.api.infraestructura.repositorio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import calendario.api.dominio.CalendarioEntidad;

// Extiende JpaRepository para interactuar con PostgreSQL.
@Repository
public interface ICalendarioRepository extends JpaRepository<CalendarioEntidad, Long> {
    List<CalendarioEntidad> findByFechaBetween(LocalDate inicio, LocalDate fin);
}
