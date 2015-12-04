package cl.wisse.datomed.core.persistence.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by nhakor on 08-07-14.
 */
@Entity
@Table(name="tbl_usuarios")
public class Usuario implements Serializable {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(generator="SEQ_USUARIO",strategy= GenerationType.SEQUENCE)
    private Integer id;
    @Column(name="RUT")
    private String rut;
    @Column(name="NOMBRE")
    private String nombre;
    @Column(name="APELLIDOPATERNO")
    private String apellidoPaterno;
    @Column(name="APELLIDOMATERNO")
    private String apellidoMaterno;
    @Column(name="SEXO")
    private String sexo;
    @Column(name="CORREO")
    private String correo;
    @Column(name="ESTADO")
    private Integer estado;
    @Column(name="PASSWORD")
    private String password;
    @Column(name="EMPRESA")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name="ID", nullable=false)
    public Empresa getEmpresa(){
        return empresa;
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

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
