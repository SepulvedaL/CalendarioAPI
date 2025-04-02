package calendario.api.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import calendario.api.dominio.TipoEntidad;

// Repositorio para Tipo
@Repository
public interface ITipoRepository extends JpaRepository<TipoEntidad, Long> {
}