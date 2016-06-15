/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
 * @author ADMIN
 */
@Entity
@Table(name = "contratista")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contratista.findAll", query = "SELECT c FROM Contratista c"),
    @NamedQuery(name = "Contratista.findByNitcontratista", query = "SELECT c FROM Contratista c WHERE c.nitcontratista = :nitcontratista"),
    @NamedQuery(name = "Contratista.findByNombrecontratista", query = "SELECT c FROM Contratista c WHERE c.nombrecontratista = :nombrecontratista"),
    @NamedQuery(name = "Contratista.findByEstadocontratista", query = "SELECT c FROM Contratista c WHERE c.estadocontratista = :estadocontratista")})
public class Contratista implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "nitcontratista")
    private Long nitcontratista;
    @Size(max = 50)
    @Column(name = "nombrecontratista")
    private String nombrecontratista;
    @Size(max = 50)
    @Column(name = "estadocontratista")
    private String estadocontratista;
    @OneToMany(mappedBy = "contratistaempleado")
    private List<Empleado> empleadoList;

    public Contratista() {
    }

    public Contratista(Long nitcontratista) {
        this.nitcontratista = nitcontratista;
    }

    public Long getNitcontratista() {
        return nitcontratista;
    }

    public void setNitcontratista(Long nitcontratista) {
        this.nitcontratista = nitcontratista;
    }

    public String getNombrecontratista() {
        return nombrecontratista;
    }

    public void setNombrecontratista(String nombrecontratista) {
        this.nombrecontratista = nombrecontratista;
    }

    public String getEstadocontratista() {
        return estadocontratista;
    }

    public void setEstadocontratista(String estadocontratista) {
        this.estadocontratista = estadocontratista;
    }

    @XmlTransient
    public List<Empleado> getEmpleadoList() {
        return empleadoList;
    }

    public void setEmpleadoList(List<Empleado> empleadoList) {
        this.empleadoList = empleadoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nitcontratista != null ? nitcontratista.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contratista)) {
            return false;
        }
        Contratista other = (Contratista) object;
        if ((this.nitcontratista == null && other.nitcontratista != null) || (this.nitcontratista != null && !this.nitcontratista.equals(other.nitcontratista))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Contratista[ nitcontratista=" + nitcontratista + " ]";
    }
    
}
