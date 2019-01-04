/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author dilov
 */
public class DatosTabla {
    private String contratistaingreso;
    private Integer codigocontratista;
    private String estado;
    private String fechainicio;
    private String fechafin;
    private List<DatosIngresos> ingresos;

    public DatosTabla() {
    }

    public String getContratistaingreso() {
        return contratistaingreso;
    }

    public void setContratistaingreso(String contratistaingreso) {
        this.contratistaingreso = contratistaingreso;
    }

    public Integer getCodigocontratista() {
        return codigocontratista;
    }

    public void setCodigocontratista(Integer codigocontratista) {
        this.codigocontratista = codigocontratista;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(String fechainicio) {
        this.fechainicio = fechainicio;
    }

    public String getFechafin() {
        return fechafin;
    }

    public void setFechafin(String fechafin) {
        this.fechafin = fechafin;
    }

    public List<DatosIngresos> getIngresos() {
        return ingresos;
    }

    public void setIngresos(List<DatosIngresos> ingresos) {
        this.ingresos = ingresos;
    }
    
    public JRDataSource getIngresosE() {
        return new JRBeanCollectionDataSource(ingresos);
    }
    
}
