/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todotest.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author andresbailen93
 */
@Entity
@Table(name = "USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByDni", query = "SELECT u FROM Usuario u WHERE u.dni = :dni"),
    @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre"),
    @NamedQuery(name = "Usuario.findByApellidos", query = "SELECT u FROM Usuario u WHERE u.apellidos = :apellidos"),
    @NamedQuery(name = "Usuario.findByPassword", query = "SELECT u FROM Usuario u WHERE u.password = :password"),
    @NamedQuery(name = "Usuario.findByEsProf", query = "SELECT u FROM Usuario u WHERE u.esProf = :esProf"),
    @NamedQuery(name = "Usuario.findByIdentificador", query = "SELECT u FROM Usuario u WHERE u.identificador = :identificador")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "DNI")
    private String dni;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ES_PROF")
    private short esProf;
    @Column(name = "IDENTIFICADOR")
    private Short identificador;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dni")
    private Collection<Test> testCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<Examen> examenCollection;

    public Usuario() {
    }

    public Usuario(String dni) {
        this.dni = dni;
    }

    public Usuario(String dni, String nombre, String apellidos, String password, short esProf) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.password = password;
        this.esProf = esProf;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public short getEsProf() {
        return esProf;
    }

    public void setEsProf(short esProf) {
        this.esProf = esProf;
    }

    public Short getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Short identificador) {
        this.identificador = identificador;
    }

    @XmlTransient
    public Collection<Test> getTestCollection() {
        return testCollection;
    }

    public void setTestCollection(Collection<Test> testCollection) {
        this.testCollection = testCollection;
    }

    @XmlTransient
    public Collection<Examen> getExamenCollection() {
        return examenCollection;
    }

    public void setExamenCollection(Collection<Examen> examenCollection) {
        this.examenCollection = examenCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dni != null ? dni.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.dni == null && other.dni != null) || (this.dni != null && !this.dni.equals(other.dni))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Usuario[ dni=" + dni + " ]";
    }
    
}
