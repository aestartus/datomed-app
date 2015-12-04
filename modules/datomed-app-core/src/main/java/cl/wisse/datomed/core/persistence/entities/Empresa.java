package cl.wisse.datomed.core.persistence.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by nhakor on 22-04-14.
 */
@Entity
@Table(name="tbl_empresas")
public class Empresa implements Serializable {

    @Column(name = "ID", nullable = false)
    @GeneratedValue(generator="SEQ_EMPRESA",strategy=GenerationType.SEQUENCE)
    private Integer id;
    @Column(name="RUT")
    private String rut;
    @Column(name="NOMBRE")
    private String nombre;
    @Column(name="DIRECCION")
    private String direccion;
    @Column(name="CORREO")
    private String correo;
    @Column(name="REPLEGAL")
    private String representanteLegal;
    @Column(name="COORDENADAS")
    private String coordenadas;
    @Column(name="ESTADO")
    private String estado;

    @Column(name="USUARIOS")
    private Set<Usuario> usuarios;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="EMPRESA")
    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRepresentanteLegal() {
        return representanteLegal;
    }

    public void setRepresentanteLegal(String representanteLegal) {
        this.representanteLegal = representanteLegal;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
