/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import logica.ContratistaLogicaLocal;
import logica.EmpleadoLogicaLocal;
import logica.IngresoLogicaLocal;
import modelo.Contratista;
import modelo.DatosIngresoReporte;
import modelo.Empleado;
import modelo.Ingreso;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import org.primefaces.component.calendar.Calendar;

import org.primefaces.component.inputtext.InputText;
import org.primefaces.event.SelectEvent;
import persistencia.IngresoFacadeLocal;

/**
 *
 * @author arios
 */
@ManagedBean(name = "ingresosVista")
@ViewScoped
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
            if (documento.equals("")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Pase la cédula Nuevamente"));
            } else {
                Empleado empleadoIngreso = empleadoLogica.consultarPorDocumento(Long.parseLong(documento));
                if (empleadoIngreso == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "INGRESO NO AUTORIZADO", "¡El empleado no se encuentra registrado!"));
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
                    if (tieneIngreso != null && tieneIngreso.getHorasalidaingreso() == null && tieneIngreso.getAutorizadoingreso().equals("S")) {
                        Date fechaSalida = new Date();
                        tieneIngreso.setHorasalidaingreso(fechaSalida);
                        ingresoLogica.modificar(tieneIngreso);
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "SALIDA REGISTRADA", ""));
                    } else if(tieneIngreso != null && tieneIngreso.getHorasalidaingreso() == null && tieneIngreso.getAutorizadoingreso().equals("N")) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "INGRESO NO AUTORIZADO", "¡El empleado ya tiene un Registro el día de hoy que no Fue Autorizado!"));
                    }else{
                        if (empleadoIngreso.getEpsempleado().equals("N")) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "INGRESO NO AUTORIZADO", "¡El empleado no tiene Activa la EPS!"));
                            nuevoIngreso.setAutorizadoingreso("N");
                        } else if (empleadoIngreso.getArlempleado().equals("N")) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "INGRESO NO AUTORIZADO", "¡El empleado no tiene Activa la ARL!"));
                            nuevoIngreso.setAutorizadoingreso("N");
                        } else if (empleadoIngreso.getEstadoempleado().equals("INACTIVO")) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "INGRESO NO AUTORIZADO", "¡El empleado esta INACTIVO!"));
                            nuevoIngreso.setAutorizadoingreso("N");
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INGRESO AUTORIZADO", ""));
                            nuevoIngreso.setAutorizadoingreso("S");
                            
                        }
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
        if (txtContratista.getValue().toString().equals("")) {
            listaIngresos = ingresoDAO.findAll();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡Debe seleccionar el Contratista!"));
        } else {
            c.setNitcontratista(Long.parseLong(txtContratista.getValue().toString()));
            if (txtFechaIngresoInicioC.getValue() == null || txtFechaIngresoFinC.getValue() == null) {
                listaIngresos = ingresoDAO.consultarIngresosContratista(c);
            } else {
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaI = (Date) txtFechaIngresoInicioC.getValue();
                Date fechaF = (Date) txtFechaIngresoFinC.getValue();
                if (fechaF.before(fechaI)) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡La fecha Hasta debe ser anterior a la fecha Desde!"));
                } else {
                    String fechai = formato.format(fechaI);
                    String fechaf = formato.format(fechaF);
                    listaIngresos = ingresoDAO.consultarIngresosContratista(c, fechai, fechaf);
                }
            }
        }
    }

    public void consultarxContratistaPDF() {
        try {
            Contratista c = new Contratista();
            String fechai = "";
            String fechaf = "";
            File jasper = null;
            String nombreReporte = "";
            Map<String, Object> parametros = new HashMap<>();
            if (txtContratista.getValue().toString().equals("")) {
                listaIngresos = ingresoDAO.findAll();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡Debe seleccionar el Contratista!"));
            } else {
                c.setNitcontratista(Long.parseLong(txtContratista.getValue().toString()));
                if (txtFechaIngresoInicioC.getValue() == null || txtFechaIngresoFinC.getValue() == null) {
                    listaIngresos = ingresoDAO.consultarIngresosContratista(c);
                    jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("reportes/reporteContratista.jasper"));
                    nombreReporte = "reporteContratista" + c.getNitcontratista() + ".pdf";
                } else {
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    Date fechaI = (Date) txtFechaIngresoInicioC.getValue();
                    Date fechaF = (Date) txtFechaIngresoFinC.getValue();
                    if (fechaF.before(fechaI)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡La fecha Hasta debe ser anterior a la fecha Desde!"));
                    } else {
                        jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("reportes/reporteContratistaFechas.jasper"));
                        nombreReporte = "reporteContratista" + c.getNitcontratista() + "xFecha.pdf";
                        fechai = formato.format(fechaI);
                        fechaf = formato.format(fechaF);
                        parametros.put("fechainicio", fechai);
                        parametros.put("fechafin", fechaf);
                        listaIngresos = ingresoDAO.consultarIngresosContratista(c, fechai, fechaf);
                    }
                }
            }

            if (listaIngresos.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡No hay Ingresos que coincidan con los Parámetros!"));
            } else {
                Contratista objC = contratistaLogica.consultarPorNit(c.getNitcontratista());
                parametros.put("nitContratista", c.getNitcontratista().toString());
                parametros.put("nombreContratista", objC.getNombrecontratista());
                parametros.put("banner", this.getClass().getResourceAsStream("bannerControl.png"));
                List<DatosIngresoReporte> listaIngresosReporte = new ArrayList<>();
                for (Ingreso obj : listaIngresos) {
                    DatosIngresoReporte objDatos = new DatosIngresoReporte();
                    SimpleDateFormat formatoF = new SimpleDateFormat("yyyy-MM-dd");
                    Date fecha = obj.getFechaingreso();
                    objDatos.setNumeroingreso(obj.getNumeroingreso().toString());
                    objDatos.setFechaingreso(formatoF.format(fecha));
                    Date horaE = obj.getHoraentradaingreso();
                    Date horaS = obj.getHorasalidaingreso();
                    SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
                    objDatos.setHoraentradaingreso(formato.format(horaE));
                    if (obj.getHorasalidaingreso() != null) {
                        objDatos.setHorasalidaingreso(formato.format(horaS));
                    } else {
                        objDatos.setHorasalidaingreso("-");
                    }
                    objDatos.setAutorizadoingreso(obj.getAutorizadoingreso());
                    objDatos.setNombreempleadoingreso(obj.getEmpleadoingreso().getNombreempleado() + " " + obj.getEmpleadoingreso().getApellidoempleado());
                    listaIngresosReporte.add(objDatos);
                }

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JRBeanCollectionDataSource(listaIngresosReporte));
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.addHeader("Content-disposition", "attachment; filename=" + nombreReporte);
                ServletOutputStream stream = response.getOutputStream();
                JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
                FacesContext.getCurrentInstance().responseComplete();
            }
        } catch (JRException ex) {
            Logger.getLogger(IngresoVista.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IngresoVista.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(IngresoVista.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void consultarxContratistaExcel() {
        try {
            Contratista c = new Contratista();
            String nombreReporte = "";
            String fechai = "";
            String fechaf = "";
            Map<String, Object> parametros = new HashMap<>();
            File jasper = null;
            if (txtContratista.getValue().toString().equals("")) {
                listaIngresos = ingresoDAO.findAll();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡Debe seleccionar el Contratista!"));
            } else {
                c.setNitcontratista(Long.parseLong(txtContratista.getValue().toString()));
                if (txtFechaIngresoInicioC.getValue() == null || txtFechaIngresoFinC.getValue() == null) {
                    listaIngresos = ingresoDAO.consultarIngresosContratista(c);
                    jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("reportes/reporteContratista.jasper"));
                    nombreReporte = "reporteContratista" + c.getNitcontratista();
                } else {
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    Date fechaI = (Date) txtFechaIngresoInicioC.getValue();
                    Date fechaF = (Date) txtFechaIngresoFinC.getValue();
                    if (fechaF.before(fechaI)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡La fecha Hasta debe ser anterior a la fecha Desde!"));
                    } else {
                        jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("reportes/reporteContratistaFechas.jasper"));
                        fechai = formato.format(fechaI);
                        fechaf = formato.format(fechaF);
                        parametros.put("fechainicio", fechai);
                        parametros.put("fechafin", fechaf);
                        nombreReporte = "reporteContratista" + c.getNitcontratista() + "xFecha";
                        listaIngresos = ingresoDAO.consultarIngresosContratista(c, fechai, fechaf);
                    }
                }
            }
            if (listaIngresos.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡No hay Ingresos que coincidan con los Parámetros!"));
            } else {
                Contratista objC = contratistaLogica.consultarPorNit(c.getNitcontratista());
                parametros.put("nitContratista", c.getNitcontratista().toString());
                parametros.put("nombreContratista", objC.getNombrecontratista());
                parametros.put("banner", this.getClass().getResourceAsStream("bannerControl.png"));
                List<DatosIngresoReporte> listaIngresosReporte = new ArrayList<>();
                for (Ingreso obj : listaIngresos) {
                    DatosIngresoReporte objDatos = new DatosIngresoReporte();
                    SimpleDateFormat formatoF = new SimpleDateFormat("yyyy-MM-dd");
                    Date fecha = obj.getFechaingreso();
                    objDatos.setNumeroingreso(obj.getNumeroingreso().toString());
                    objDatos.setFechaingreso(formatoF.format(fecha));
                    Date horaE = obj.getHoraentradaingreso();
                    Date horaS = obj.getHorasalidaingreso();
                    SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
                    objDatos.setHoraentradaingreso(formato.format(horaE));
                    if (obj.getHorasalidaingreso() != null) {
                        objDatos.setHorasalidaingreso(formato.format(horaS));
                    } else {
                        objDatos.setHorasalidaingreso("-");
                    }
                    objDatos.setAutorizadoingreso(obj.getAutorizadoingreso());
                    objDatos.setNombreempleadoingreso(obj.getEmpleadoingreso().getNombreempleado() + " " + obj.getEmpleadoingreso().getApellidoempleado());
                    listaIngresosReporte.add(objDatos);
                }

                JasperPrint print = JasperFillManager.fillReport(jasper.getPath(), parametros, new JRBeanCollectionDataSource(listaIngresosReporte));
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

                OutputStream out = response.getOutputStream();

                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
                JRXlsExporter exporterXLS = new JRXlsExporter();

                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
                exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, arrayOutputStream);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                exporterXLS.exportReport();

                response.setHeader("Content-disposition", "attachment; filename=" + nombreReporte);
                response.setContentType("application/vnd.ms-excel");
                response.setContentLength(arrayOutputStream.toByteArray().length);
                out.write(arrayOutputStream.toByteArray());
                out.flush();
                out.close();
            }
        } catch (JRException ex) {
            Logger.getLogger(IngresoVista.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IngresoVista.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(IngresoVista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultarxEmpleado() {
        Empleado e = new Empleado();
        if (txtEmpleado.getValue().toString().equals("")) {
            listaIngresos = ingresoDAO.findAll();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡Debe seleccionar el Empleado!"));
        } else {
            e.setCedulaempleado(Long.parseLong(txtEmpleado.getValue().toString()));
            if (txtFechaIngresoInicioE.getValue() == null || txtFechaIngresoFinE.getValue() == null) {
                listaIngresos = ingresoDAO.consultarIngresosEmpleado(e);
            } else {
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaI = (Date) txtFechaIngresoInicioE.getValue();
                Date fechaF = (Date) txtFechaIngresoFinE.getValue();
                if (fechaF.before(fechaI)) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡La fecha Hasta debe ser anterior a la fecha Desde!"));
                } else {
                    String fechai = formato.format(fechaI);
                    String fechaf = formato.format(fechaF);
                    listaIngresos = ingresoDAO.consultarIngresosEmpleado(e, fechai, fechaf);
                }
            }
        }
    }

    public void consultarxEmpleadoPDF() {
        try {
            String fechai = "";
            String fechaf = "";
            File jasper = null;
            String nombreReporte = "";
            Map<String, Object> parametros = new HashMap<>();
            Empleado e = new Empleado();
            if (txtEmpleado.getValue().toString().equals("")) {
                listaIngresos = ingresoDAO.findAll();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡Debe seleccionar el Empleado!"));
            } else {
                e.setCedulaempleado(Long.parseLong(txtEmpleado.getValue().toString()));
                if (txtFechaIngresoInicioE.getValue() == null || txtFechaIngresoFinE.getValue() == null) {
                    listaIngresos = ingresoDAO.consultarIngresosEmpleado(e);
                    jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("reportes/reporteEmpleado.jasper"));
                    nombreReporte = "reporteEmpleado" + e.getCedulaempleado() + ".pdf";
                } else {
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    Date fechaI = (Date) txtFechaIngresoInicioE.getValue();
                    Date fechaF = (Date) txtFechaIngresoFinE.getValue();
                    if (fechaF.before(fechaI)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡La fecha Hasta debe ser anterior a la fecha Desde!"));
                    } else {
                        jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("reportes/reporteEmpleadoFechas.jasper"));
                        nombreReporte = "reporteEmpleado" + e.getCedulaempleado() + "xFecha.pdf";

                        fechai = formato.format(fechaI);
                        fechaf = formato.format(fechaF);
                        parametros.put("fechai", fechai);
                        parametros.put("fechafin", fechaf);
                        listaIngresos = ingresoDAO.consultarIngresosEmpleado(e, fechai, fechaf);
                    }
                }
            }

            if (listaIngresos.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡No hay Ingresos que coincidan con los Parámetros!"));
            } else {
                Empleado objE = empleadoLogica.consultarPorDocumento(e.getCedulaempleado());
                parametros.put("banner", this.getClass().getResourceAsStream("bannerControl.png"));
                parametros.put("cedula", objE.getCedulaempleado().toString());
                parametros.put("nombre", objE.getNombreempleado() + " " + objE.getApellidoempleado());
                parametros.put("telefono", objE.getTelefonoempleado());
                parametros.put("correo", objE.getCorreoempleado());
                parametros.put("cargo", objE.getCargoempleado());
                if(objE.getContratistaempleado()!=null){
                    parametros.put("contratista", objE.getContratistaempleado().getNombrecontratista());
                }else{
                    parametros.put("contratista", "No Asignado");
                }
                parametros.put("estado", objE.getEstadoempleado());
                parametros.put("arl", objE.getArlempleado());
                parametros.put("eps", objE.getEpsempleado());
                List<DatosIngresoReporte> listaIngresosReporte = new ArrayList<>();
                for (Ingreso obj : listaIngresos) {
                    DatosIngresoReporte objDatos = new DatosIngresoReporte();
                    SimpleDateFormat formatoF = new SimpleDateFormat("yyyy-MM-dd");
                    Date fecha = obj.getFechaingreso();
                    objDatos.setNumeroingreso(obj.getNumeroingreso().toString());
                    objDatos.setFechaingreso(formatoF.format(fecha));
                    Date horaE = obj.getHoraentradaingreso();
                    Date horaS = obj.getHorasalidaingreso();
                    SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
                    objDatos.setHoraentradaingreso(formato.format(horaE));
                    if (obj.getHorasalidaingreso() != null) {
                        objDatos.setHorasalidaingreso(formato.format(horaS));
                    } else {
                        objDatos.setHorasalidaingreso("-");
                    }
                    objDatos.setAutorizadoingreso(obj.getAutorizadoingreso());
                    listaIngresosReporte.add(objDatos);
                }

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JRBeanCollectionDataSource(listaIngresosReporte));
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                response.addHeader("Content-disposition", "attachment; filename=" + nombreReporte);
                ServletOutputStream stream = response.getOutputStream();
                JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
                FacesContext.getCurrentInstance().responseComplete();
            }
        } catch (JRException ex) {
            Logger.getLogger(IngresoVista.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IngresoVista.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(IngresoVista.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void consultarxEmpleadoExcel() {
        try {
            String fechai = "";
            String fechaf = "";
            File jasper = null;
            String nombreReporte = "";
            Map<String, Object> parametros = new HashMap<>();
            Empleado e = new Empleado();
            if (txtEmpleado.getValue().toString().equals("")) {
                listaIngresos = ingresoDAO.findAll();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡Debe seleccionar el Empleado!"));
            } else {
                e.setCedulaempleado(Long.parseLong(txtEmpleado.getValue().toString()));
                if (txtFechaIngresoInicioE.getValue() == null || txtFechaIngresoFinE.getValue() == null) {
                    listaIngresos = ingresoDAO.consultarIngresosEmpleado(e);
                    jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("reportes/reporteEmpleado.jasper"));
                    nombreReporte = "reporteEmpleado" + e.getCedulaempleado();
                } else {
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    Date fechaI = (Date) txtFechaIngresoInicioE.getValue();
                    Date fechaF = (Date) txtFechaIngresoFinE.getValue();
                    if (fechaF.before(fechaI)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡La fecha Hasta debe ser anterior a la fecha Desde!"));
                    } else {
                        jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("reportes/reporteEmpleadoFechas.jasper"));
                        fechai = formato.format(fechaI);
                        fechaf = formato.format(fechaF);
                        parametros.put("fechai", fechai);
                        parametros.put("fechafin", fechaf);
                        nombreReporte = "reporteEmpleado" + e.getCedulaempleado() + "xFecha";
                        listaIngresos = ingresoDAO.consultarIngresosEmpleado(e, fechai, fechaf);
                    }
                }
            }
            if (listaIngresos.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡No hay Ingresos que coincidan con los Parámetros!"));
            } else {
                Empleado objE = empleadoLogica.consultarPorDocumento(e.getCedulaempleado());
                parametros.put("banner", this.getClass().getResourceAsStream("bannerControl.png"));
                parametros.put("cedula", objE.getCedulaempleado().toString());
                parametros.put("nombre", objE.getNombreempleado() + " " + objE.getApellidoempleado());
                parametros.put("telefono", objE.getTelefonoempleado());
                parametros.put("correo", objE.getCorreoempleado());
                parametros.put("cargo", objE.getCargoempleado());
                if(objE.getContratistaempleado()!=null){
                    parametros.put("contratista", objE.getContratistaempleado().getNombrecontratista());
                }else{
                    parametros.put("contratista", "No Asignado");
                }
                parametros.put("estado", objE.getEstadoempleado());
                parametros.put("arl", objE.getArlempleado());
                parametros.put("eps", objE.getEpsempleado());
                List<DatosIngresoReporte> listaIngresosReporte = new ArrayList<>();
                for (Ingreso obj : listaIngresos) {
                    DatosIngresoReporte objDatos = new DatosIngresoReporte();
                    SimpleDateFormat formatoF = new SimpleDateFormat("yyyy-MM-dd");
                    Date fecha = obj.getFechaingreso();
                    objDatos.setNumeroingreso(obj.getNumeroingreso().toString());
                    objDatos.setFechaingreso(formatoF.format(fecha));
                    Date horaE = obj.getHoraentradaingreso();
                    Date horaS = obj.getHorasalidaingreso();
                    SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
                    objDatos.setHoraentradaingreso(formato.format(horaE));
                    if (obj.getHorasalidaingreso() != null) {
                        objDatos.setHorasalidaingreso(formato.format(horaS));
                    } else {
                        objDatos.setHorasalidaingreso("-");
                    }
                    objDatos.setAutorizadoingreso(obj.getAutorizadoingreso());
                    objDatos.setNombreempleadoingreso(obj.getEmpleadoingreso().getNombreempleado() + " " + obj.getEmpleadoingreso().getApellidoempleado());
                    listaIngresosReporte.add(objDatos);
                }

                JasperPrint print = JasperFillManager.fillReport(jasper.getPath(), parametros, new JRBeanCollectionDataSource(listaIngresosReporte));
                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

                OutputStream out = response.getOutputStream();

                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
                JRXlsExporter exporterXLS = new JRXlsExporter();

                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
                exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, arrayOutputStream);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                exporterXLS.exportReport();

                response.setHeader("Content-disposition", "attachment; filename=" + nombreReporte);
                response.setContentType("application/vnd.ms-excel");
                response.setContentLength(arrayOutputStream.toByteArray().length);
                out.write(arrayOutputStream.toByteArray());
                out.flush();
                out.close();
            }
        } catch (JRException ex) {
            Logger.getLogger(IngresoVista.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IngresoVista.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(IngresoVista.class.getName()).log(Level.SEVERE, null, ex);
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
