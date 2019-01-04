/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author dilov
 */
public class DatosIngresos {
    private String fechaingreso;
    private String horaentradaingreso;
    private String horasalidaingreso;
    private String autorizadoingreso;

    public DatosIngresos() {
    }

    public String getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(String fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public String getHoraentradaingreso() {
        return horaentradaingreso;
    }

    public void setHoraentradaingreso(String horaentradaingreso) {
        this.horaentradaingreso = horaentradaingreso;
    }

    public String getHorasalidaingreso() {
        return horasalidaingreso;
    }

    public void setHorasalidaingreso(String horasalidaingreso) {
        this.horasalidaingreso = horasalidaingreso;
    }

    public String getAutorizadoingreso() {
        return autorizadoingreso;
    }

    public void setAutorizadoingreso(String autorizadoingreso) {
        this.autorizadoingreso = autorizadoingreso;
    }
    
    
}
