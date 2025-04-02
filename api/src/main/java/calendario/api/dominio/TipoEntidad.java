package calendario.api.dominio;
import jakarta.persistence.*;


@Entity
@Table(name = "tipo")
public class TipoEntidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String tipo;

    // Constructor
    public TipoEntidad() { }

    public TipoEntidad(Long id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
