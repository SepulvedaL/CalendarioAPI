package calendario.api.aplicacion;

// Define cómo se transfieren los datos de TipoEntidad
public class TipoDTO {
    private Long id;
    private String tipo;


    //Constructores
    public TipoDTO() {
    }

    public TipoDTO(Long id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    // Getter y Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
