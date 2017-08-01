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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * @author dilov
 */
@Entity
@Table(name = "contratista")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contratista.findAll", query = "SELECT c FROM Contratista c"),
    @NamedQuery(name = "Contratista.findByNitcontratista", query = "SELECT c FROM Contratista c WHERE c.nitcontratista = :nitcontratista"),
    @NamedQuery(name = "Contratista.findByNombrecontratista", query = "SELECT c FROM Contratista c WHERE c.nombrecontratista = :nombrecontratista"),
    @NamedQuery(name = "Contratista.findByEstadocontratista", query = "SELECT c FROM Contratista c WHERE c.estadocontratista = :estadocontratista"),
    @NamedQuery(name = "Contratista.findByCodigocontratista", query = "SELECT c FROM Contratista c WHERE c.codigocontratista = :codigocontratista")})
public class Contratista implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nitcontratista")
    private long nitcontratista;
    @Size(max = 50)
    @Column(name = "nombrecontratista")
    private String nombrecontratista;
    @Size(max = 50)
    @Column(name = "estadocontratista")
    private String estadocontratista;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigocontratista")
    private Integer codigocontratista;
    @OneToMany(mappedBy = "contratistaempleado")
    private List<Empleado> empleadoList;

    public Contratista() {
    }

    public Contratista(Integer codigocontratista) {
        this.codigocontratista = codigocontratista;
    }

    public Contratista(Integer codigocontratista, long nitcontratista) {
        this.codigocontratista = codigocontratista;
        this.nitcontratista = nitcontratista;
    }

    public long getNitcontratista() {
        return nitcontratista;
    }

    public void setNitcontratista(long nitcontratista) {
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

    public Integer getCodigocontratista() {
        return codigocontratista;
    }

    public void setCodigocontratista(Integer codigocontratista) {
        this.codigocontratista = codigocontratista;
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
        hash += (codigocontratista != null ? codigocontratista.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contratista)) {
            return false;
        }
        Contratista other = (Contratista) object;
        if ((this.codigocontratista == null && other.codigocontratista != null) || (this.codigocontratista != null && !this.codigocontratista.equals(other.codigocontratista))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Contratista[ codigocontratista=" + codigocontratista + " ]";
    }
    
}
