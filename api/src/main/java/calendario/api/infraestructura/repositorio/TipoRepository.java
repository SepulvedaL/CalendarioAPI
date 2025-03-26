package calendario.api.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import calendario.api.dominio.TipoEntidad;

// Repositorio para Tipo
public interface TipoRepository extends JpaRepository<TipoEntidad, Long> {
}