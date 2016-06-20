/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import logica.ContratistaLogicaLocal;
import logica.EmpleadoLogicaLocal;
import logica.IngresoLogicaLocal;
import logica.UsuarioLogicaLocal;
import modelo.Contratista;
import modelo.Empleado;
import modelo.Ingreso;
import modelo.Usuario;
import org.primefaces.component.calendar.Calendar;

import org.primefaces.component.inputtext.InputText;
import org.primefaces.event.SelectEvent;
import persistencia.IngresoFacadeLocal;

/**
 *
 * @author arios
 */
@ManagedBean(name = "ingresosVista")
@RequestScoped
public class IngresoVista {

    private InputText txtCedula;

    private String documento;
    private String datoFechaIngreso;
    private String datoHoraIngreso;
    private String datoCargoIngreso;
    private String datoNombreIngreso;
    private List<Ingreso> listaIngresos;
    private Ingreso selectedIngreso;
    //Consultas Contratista
    private InputText txtContratista;
    private Calendar txtFechaIngresoInicioC;
    private Calendar txtFechaIngresoFinC;
    private List<Contratista> listaContratistas;
    private Contratista selectedContratista;
    //Consultas Empleados
    private InputText txtEmpleado;
    private Calendar txtFechaIngresoInicioE;
    private Calendar txtFechaIngresoFinE;
    private List<Empleado> listaEmpleados;
    private Empleado selectedEmpleado;

    @EJB
    private IngresoLogicaLocal ingresoLogica;
    @EJB
    private EmpleadoLogicaLocal empleadoLogica;
    @EJB
    private ContratistaLogicaLocal contratistaLogica;
    @EJB
    private IngresoFacadeLocal ingresoDAO;

    /**
     * Creates a new instance of IngresoVista
     */
    public IngresoVista() {
    }

    public InputText getTxtCedula() {
        return txtCedula;
    }

    public void setTxtCedula(InputText txtCedula) {
        this.txtCedula = txtCedula;
    }


    public String getDatoFechaIngreso() {
        return datoFechaIngreso;
    }

    public void setDatoFechaIngreso(String datoFechaIngreso) {
        this.datoFechaIngreso = datoFechaIngreso;
    }

    public String getDatoHoraIngreso() {
        return datoHoraIngreso;
    }

    public void setDatoHoraIngreso(String datoHoraIngreso) {
        this.datoHoraIngreso = datoHoraIngreso;
    }

    public String getDatoCargoIngreso() {
        return datoCargoIngreso;
    }

    public void setDatoCargoIngreso(String datoCargoIngreso) {
        this.datoCargoIngreso = datoCargoIngreso;
    }

    public String getDatoNombreIngreso() {
        return datoNombreIngreso;
    }

    public void setDatoNombreIngreso(String datoNombreIngreso) {
        this.datoNombreIngreso = datoNombreIngreso;
    }



    public void action_registrar_ingreso() {
        try {
            empleadoLogica.limpiarCache();
            System.out.println("Documento " + documento);
            Empleado empleadoIngreso = empleadoLogica.consultarPorDocumento(Long.parseLong(documento));
            if (empleadoIngreso == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "INGRESO NO AUTORIZADO", "El empleado no se encuentra registrado"));
            } else {
                Ingreso nuevoIngreso = new Ingreso();
                nuevoIngreso.setEmpleadoingreso(empleadoIngreso);
                Date fechaActual = new Date();
                SimpleDateFormat formatoF = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat formatoH = new SimpleDateFormat("HH:mm:ss");
                nuevoIngreso.setFechaingreso(fechaActual);
                nuevoIngreso.setHoraentradaingreso(fechaActual);
                datoFechaIngreso = formatoF.format(fechaActual);
                datoHoraIngreso = formatoH.format(fechaActual);
                datoNombreIngreso = empleadoIngreso.getNombreempleado() + " " + empleadoIngreso.getApellidoempleado();
                datoCargoIngreso = empleadoIngreso.getCargoempleado();
                Ingreso tieneIngreso = ingresoLogica.consultarxFecha(datoFechaIngreso, empleadoIngreso);
                if (tieneIngreso != null && tieneIngreso.getHorasalidaingreso() == null) {
                    Date fechaSalida = new Date();
                    tieneIngreso.setHorasalidaingreso(fechaSalida);
                    ingresoLogica.modificar(tieneIngreso);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "SALIDA REGISTRADA", ""));
                } else {
                    if (empleadoIngreso.getEpsempleado().equals("N")) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "INGRESO NO AUTORIZADO", "El empleado no tiene Activa la EPS"));
                        nuevoIngreso.setAutorizadoingreso("N");
                    } else if (empleadoIngreso.getArlempleado().equals("N")) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "INGRESO NO AUTORIZADO", "El empleado no tiene Activa la ARL"));
                        nuevoIngreso.setAutorizadoingreso("N");
                    } else if (empleadoIngreso.getEstadoempleado().equals("INACTIVO")) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "INGRESO NO AUTORIZADO", "El empleado esta INACTIVO."));
                        nuevoIngreso.setAutorizadoingreso("N");
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INGRESO AUTORIZADO", ""));
                        nuevoIngreso.setAutorizadoingreso("S");
                        ingresoLogica.registrar(nuevoIngreso);
                    }

                }
            }
            limpiar();
        } catch (Exception ex) {
            Logger.getLogger(IngresoVista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void limpiar() {
        txtCedula.setValue("");
      
        documento = "";
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public List<Ingreso> getListaIngresos() {
        if (listaIngresos == null) {
            try {
                listaIngresos = ingresoLogica.consultar();
            } catch (Exception ex) {
                Logger.getLogger(IngresoVista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaIngresos;
    }

    public void setListaIngresos(List<Ingreso> listaIngresos) {
        this.listaIngresos = listaIngresos;
    }

    public Ingreso getSelectedIngreso() {
        return selectedIngreso;
    }

    public void setSelectedIngreso(Ingreso selectedIngreso) {
        this.selectedIngreso = selectedIngreso;
    }

    public InputText getTxtContratista() {
        return txtContratista;
    }

    public void setTxtContratista(InputText txtContratista) {
        this.txtContratista = txtContratista;
    }

    public Calendar getTxtFechaIngresoInicioC() {
        return txtFechaIngresoInicioC;
    }

    public void setTxtFechaIngresoInicioC(Calendar txtFechaIngresoInicioC) {
        this.txtFechaIngresoInicioC = txtFechaIngresoInicioC;
    }

    public Calendar getTxtFechaIngresoFinC() {
        return txtFechaIngresoFinC;
    }

    public void setTxtFechaIngresoFinC(Calendar txtFechaIngresoFinC) {
        this.txtFechaIngresoFinC = txtFechaIngresoFinC;
    }

    public List<Contratista> getListaContratistas() {
        try {
            listaContratistas = contratistaLogica.consultar();
        } catch (Exception ex) {
            Logger.getLogger(IngresoVista.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaContratistas;
    }

    public void setListaContratistas(List<Contratista> listaContratistas) {
        this.listaContratistas = listaContratistas;
    }

    public Contratista getSelectedContratista() {
        return selectedContratista;
    }

    public void setSelectedContratista(Contratista selectedContratista) {
        this.selectedContratista = selectedContratista;
    }

    public void seleccionarContratista(SelectEvent event) {
        Contratista objC = (Contratista) event.getObject();
        txtContratista.setValue(objC.getNitcontratista());
        listaIngresos = null;
    }

    public void seleccionarEmpleado(SelectEvent event) {
        Empleado objE = (Empleado) event.getObject();
        txtEmpleado.setValue(objE.getCedulaempleado());
        listaIngresos = null;
    }

    public InputText getTxtEmpleado() {
        return txtEmpleado;
    }

    public void setTxtEmpleado(InputText txtEmpleado) {
        this.txtEmpleado = txtEmpleado;
    }

    public Calendar getTxtFechaIngresoInicioE() {
        return txtFechaIngresoInicioE;
    }

    public void setTxtFechaIngresoInicioE(Calendar txtFechaIngresoInicioE) {
        this.txtFechaIngresoInicioE = txtFechaIngresoInicioE;
    }

    public Calendar getTxtFechaIngresoFinE() {
        return txtFechaIngresoFinE;
    }

    public void setTxtFechaIngresoFinE(Calendar txtFechaIngresoFinE) {
        this.txtFechaIngresoFinE = txtFechaIngresoFinE;
    }

    public List<Empleado> getListaEmpleados() {
        if (listaEmpleados == null) {
            try {
                listaEmpleados = empleadoLogica.consultar();

            } catch (Exception ex) {
                Logger.getLogger(IngresoVista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public Empleado getSelectedEmpleado() {
        return selectedEmpleado;
    }

    public void setSelectedEmpleado(Empleado selectedEmpleado) {
        this.selectedEmpleado = selectedEmpleado;
    }

    public void consultarxContratista() {
        Contratista c = new Contratista();
        c.setNitcontratista(Long.parseLong(txtContratista.getValue().toString()));
        if (txtFechaIngresoInicioC.getValue() == null || txtFechaIngresoFinC.getValue() == null) {
            listaIngresos = ingresoDAO.consultarIngresosContratista(c);
        } else {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            String fechai = formato.format((Date) txtFechaIngresoInicioC.getValue());
            String fechaf = formato.format((Date) txtFechaIngresoFinC.getValue());
            listaIngresos = ingresoDAO.consultarIngresosContratista(c, fechai, fechaf);
        }
    }

    public void consultarxEmpleado() {
        Empleado e = new Empleado();
        e.setCedulaempleado(Long.parseLong(txtEmpleado.getValue().toString()));
        if (txtFechaIngresoInicioE.getValue() == null || txtFechaIngresoFinE.getValue() == null) {
            listaIngresos = ingresoDAO.consultarIngresosEmpleado(e);
        } else {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            String fechai = formato.format((Date) txtFechaIngresoInicioE.getValue());
            String fechaf = formato.format((Date) txtFechaIngresoFinE.getValue());
            listaIngresos = ingresoDAO.consultarIngresosEmpleado(e, fechai, fechaf);
        }
    }

    public void limpiarConsultaContratista() {
        txtContratista.setValue("");
        txtFechaIngresoInicioC.setValue(null);
        txtFechaIngresoFinC.setValue(null);
        listaIngresos = null;

    }
    
    public void limpiarConsultaEmpleados() {
        txtEmpleado.setValue("");
        txtFechaIngresoInicioE.setValue(null);
        txtFechaIngresoFinE.setValue(null);
        listaIngresos = null;
    }

}
