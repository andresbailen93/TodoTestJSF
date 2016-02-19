/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author andresbailen93
 */
@Embeddable
public class ExamenPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "DNI")
    private String dni;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TEST")
    private long idTest;

    public ExamenPK() {
    }

    public ExamenPK(String dni, long idTest) {
        this.dni = dni;
        this.idTest = idTest;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public long getIdTest() {
        return idTest;
    }

    public void setIdTest(long idTest) {
        this.idTest = idTest;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dni != null ? dni.hashCode() : 0);
        hash += (int) idTest;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExamenPK)) {
            return false;
        }
        ExamenPK other = (ExamenPK) object;
        if ((this.dni == null && other.dni != null) || (this.dni != null && !this.dni.equals(other.dni))) {
            return false;
        }
        if (this.idTest != other.idTest) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ExamenPK[ dni=" + dni + ", idTest=" + idTest + " ]";
    }
    
}
