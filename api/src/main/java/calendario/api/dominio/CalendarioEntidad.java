package calendario.api.dominio;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "calendario")
public class CalendarioEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "id_tipo", nullable = false)
    private TipoEntidad tipo;

    @Column(nullable = false)
    private String descripcion;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public TipoEntidad getTipo() { return tipo; }
    public void setTipo(TipoEntidad tipo) { this.tipo = tipo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
