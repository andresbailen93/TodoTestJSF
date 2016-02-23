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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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
@Table(name = "TEST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Test.findAll", query = "SELECT t FROM Test t"),
    @NamedQuery(name = "Test.findByIdTest", query = "SELECT t FROM Test t WHERE t.idTest = :idTest"),
    @NamedQuery(name = "Test.findByNombre", query = "SELECT t FROM Test t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "Test.findByDuracion", query = "SELECT t FROM Test t WHERE t.duracion = :duracion"),
    @NamedQuery(name = "Test.findByResta", query = "SELECT t FROM Test t WHERE t.resta = :resta"),
    @NamedQuery(name = "Test.findByActivo", query = "SELECT t FROM Test t WHERE t.activo = :activo")})
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator="TEST_SEQUENCE") 
    @SequenceGenerator(name="TEST_SEQUENCE",sequenceName="test_seq", allocationSize=1) 
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TEST")
    private Long idTest;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DURACION")
    private int duracion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RESTA")
    private short resta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ACTIVO")
    private short activo;
    @ManyToMany(mappedBy = "testCollection")
    private Collection<Pregunta> preguntaCollection;
    @JoinColumn(name = "DNI", referencedColumnName = "DNI")
    @ManyToOne(optional = false)
    private Usuario dni;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "test")
    private Collection<Examen> examenCollection;

    public Test() {
    }

    public Test(Long idTest) {
        this.idTest = idTest;
    }

    public Test(Long idTest, String nombre, int duracion, short resta, short activo) {
        this.idTest = idTest;
        this.nombre = nombre;
        this.duracion = duracion;
        this.resta = resta;
        this.activo = activo;
    }

    public Long getIdTest() {
        return idTest;
    }

    public void setIdTest(Long idTest) {
        this.idTest = idTest;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public short getResta() {
        return resta;
    }

    public void setResta(short resta) {
        this.resta = resta;
    }

    public short getActivo() {
        return activo;
    }

    public void setActivo(short activo) {
        this.activo = activo;
    }

    @XmlTransient
    public Collection<Pregunta> getPreguntaCollection() {
        return preguntaCollection;
    }

    public void setPreguntaCollection(Collection<Pregunta> preguntaCollection) {
        this.preguntaCollection = preguntaCollection;
    }

    public Usuario getDni() {
        return dni;
    }

    public void setDni(Usuario dni) {
        this.dni = dni;
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
        hash += (idTest != null ? idTest.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Test)) {
            return false;
        }
        Test other = (Test) object;
        if ((this.idTest == null && other.idTest != null) || (this.idTest != null && !this.idTest.equals(other.idTest))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Test[ idTest=" + idTest + " ]";
    }

    public Boolean setActivo(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
