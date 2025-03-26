package calendario.api.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import calendario.api.dominio.CalendarioEntidad;

// Extiende JpaRepository para interactuar con PostgreSQL.
public interface CalendarioRepository extends JpaRepository<CalendarioEntidad, Long> {
}
