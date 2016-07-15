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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "empleado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e"),
    @NamedQuery(name = "Empleado.findByCedulaempleado", query = "SELECT e FROM Empleado e WHERE e.cedulaempleado = :cedulaempleado"),
    @NamedQuery(name = "Empleado.findByNombreempleado", query = "SELECT e FROM Empleado e WHERE e.nombreempleado = :nombreempleado"),
    @NamedQuery(name = "Empleado.findByApellidoempleado", query = "SELECT e FROM Empleado e WHERE e.apellidoempleado = :apellidoempleado"),
    @NamedQuery(name = "Empleado.findByTelefonoempleado", query = "SELECT e FROM Empleado e WHERE e.telefonoempleado = :telefonoempleado"),
    @NamedQuery(name = "Empleado.findByCorreoempleado", query = "SELECT e FROM Empleado e WHERE e.correoempleado = :correoempleado"),
    @NamedQuery(name = "Empleado.findByEpsempleado", query = "SELECT e FROM Empleado e WHERE e.epsempleado = :epsempleado"),
    @NamedQuery(name = "Empleado.findByArlempleado", query = "SELECT e FROM Empleado e WHERE e.arlempleado = :arlempleado"),
    @NamedQuery(name = "Empleado.findByCargoempleado", query = "SELECT e FROM Empleado e WHERE e.cargoempleado = :cargoempleado"),
    @NamedQuery(name = "Empleado.findByEstadoempleado", query = "SELECT e FROM Empleado e WHERE e.estadoempleado = :estadoempleado"),
    @NamedQuery(name = "Empleado.findByCodigoempleado", query = "SELECT e FROM Empleado e WHERE e.codigoempleado = :codigoempleado")})
public class Empleado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cedulaempleado")
    private long cedulaempleado;
    @Size(max = 50)
    @Column(name = "nombreempleado")
    private String nombreempleado;
    @Size(max = 50)
    @Column(name = "apellidoempleado")
    private String apellidoempleado;
    @Size(max = 50)
    @Column(name = "telefonoempleado")
    private String telefonoempleado;
    @Size(max = 50)
    @Column(name = "correoempleado")
    private String correoempleado;
    @Size(max = 50)
    @Column(name = "epsempleado")
    private String epsempleado;
    @Size(max = 50)
    @Column(name = "arlempleado")
    private String arlempleado;
    @Size(max = 50)
    @Column(name = "cargoempleado")
    private String cargoempleado;
    @Size(max = 50)
    @Column(name = "estadoempleado")
    private String estadoempleado;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoempleado")
    private Integer codigoempleado;
    @JoinColumn(name = "contratistaempleado", referencedColumnName = "codigocontratista")
    @ManyToOne
    private Contratista contratistaempleado;
    @OneToMany(mappedBy = "empleadoingreso")
    private List<Ingreso> ingresoList;

    public Empleado() {
    }

    public Empleado(Integer codigoempleado) {
        this.codigoempleado = codigoempleado;
    }

    public Empleado(Integer codigoempleado, long cedulaempleado) {
        this.codigoempleado = codigoempleado;
        this.cedulaempleado = cedulaempleado;
    }

    public long getCedulaempleado() {
        return cedulaempleado;
    }

    public void setCedulaempleado(long cedulaempleado) {
        this.cedulaempleado = cedulaempleado;
    }

    public String getNombreempleado() {
        return nombreempleado;
    }

    public void setNombreempleado(String nombreempleado) {
        this.nombreempleado = nombreempleado;
    }

    public String getApellidoempleado() {
        return apellidoempleado;
    }

    public void setApellidoempleado(String apellidoempleado) {
        this.apellidoempleado = apellidoempleado;
    }

    public String getTelefonoempleado() {
        return telefonoempleado;
    }

    public void setTelefonoempleado(String telefonoempleado) {
        this.telefonoempleado = telefonoempleado;
    }

    public String getCorreoempleado() {
        return correoempleado;
    }

    public void setCorreoempleado(String correoempleado) {
        this.correoempleado = correoempleado;
    }

    public String getEpsempleado() {
        return epsempleado;
    }

    public void setEpsempleado(String epsempleado) {
        this.epsempleado = epsempleado;
    }

    public String getArlempleado() {
        return arlempleado;
    }

    public void setArlempleado(String arlempleado) {
        this.arlempleado = arlempleado;
    }

    public String getCargoempleado() {
        return cargoempleado;
    }

    public void setCargoempleado(String cargoempleado) {
        this.cargoempleado = cargoempleado;
    }

    public String getEstadoempleado() {
        return estadoempleado;
    }

    public void setEstadoempleado(String estadoempleado) {
        this.estadoempleado = estadoempleado;
    }

    public Integer getCodigoempleado() {
        return codigoempleado;
    }

    public void setCodigoempleado(Integer codigoempleado) {
        this.codigoempleado = codigoempleado;
    }

    public Contratista getContratistaempleado() {
        return contratistaempleado;
    }

    public void setContratistaempleado(Contratista contratistaempleado) {
        this.contratistaempleado = contratistaempleado;
    }

    @XmlTransient
    public List<Ingreso> getIngresoList() {
        return ingresoList;
    }

    public void setIngresoList(List<Ingreso> ingresoList) {
        this.ingresoList = ingresoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoempleado != null ? codigoempleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.codigoempleado == null && other.codigoempleado != null) || (this.codigoempleado != null && !this.codigoempleado.equals(other.codigoempleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Empleado[ codigoempleado=" + codigoempleado + " ]";
    }
    
}
