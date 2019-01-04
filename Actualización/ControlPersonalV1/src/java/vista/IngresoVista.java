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
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import logica.ContratistaLogicaLocal;
import logica.EmpleadoLogicaLocal;
import logica.IngresoLogicaLocal;
import modelo.Contratista;
import modelo.Contratos;
import modelo.DatosIngresoReporte;
import modelo.DatosIngresos;
import modelo.DatosTabla;
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
import persistencia.EmpleadoFacadeLocal;
import persistencia.IngresoFacadeLocal;

/**
 *
 * @author arios
 */
@ManagedBean(name = "ingresosVista")
@SessionScoped
public class IngresoVista implements Serializable {

    private InputText txtCedula;

    private String documento;
    private String datoFechaIngreso;
    private String datoHoraIngreso;
    private String datoCargoIngreso;
    private String datoNombreIngreso;
    private String datoAutorizadoIngreso = "";
    private String ingresos;
    private String ingresosC;
    private List<Ingreso> listaIngresos;
    private List<Ingreso> listaIngresosFiltro;
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
    @EJB
    private EmpleadoFacadeLocal empleadoFacade;

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

    public void refresh() {
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        ViewHandler viewHandler = application.getViewHandler();
        UIViewRoot viewRoot = viewHandler.createView(context, context
                .getViewRoot().getViewId());
        context.setViewRoot(viewRoot);
        context.renderResponse();
    }

    public void action_registrar_ingreso() {
        try {
            datoFechaIngreso = "";
            datoHoraIngreso = "";
            datoNombreIngreso = "";
            datoCargoIngreso = "";
            datoAutorizadoIngreso = "";
            empleadoLogica.limpiarCache();
            if (documento.equals("")) {
                datoFechaIngreso = "";
                datoHoraIngreso = "";
                datoNombreIngreso = "";
                datoCargoIngreso = "";
                datoAutorizadoIngreso = "";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "Pase la cédula Nuevamente"));
            } else {
                Empleado empleadoIngreso = empleadoLogica.consultarPorDocumento(Long.parseLong(documento));
                if (empleadoIngreso == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "INGRESO NO AUTORIZADO", "¡El empleado no se encuentra registrado!"));
                    datoFechaIngreso = "";
                    datoHoraIngreso = "";
                    datoNombreIngreso = "";
                    datoCargoIngreso = "";
                    datoAutorizadoIngreso = "N";
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
                        datoAutorizadoIngreso = "SA";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "SALIDA REGISTRADA", ""));
                    } else if (tieneIngreso != null && tieneIngreso.getHorasalidaingreso() == null && tieneIngreso.getAutorizadoingreso().equals("N")) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "INGRESO NO AUTORIZADO", "¡El empleado ya tiene un Registro el día de hoy que no Fue Autorizado!"));
                        datoFechaIngreso = "";
                        datoHoraIngreso = "";
                        datoNombreIngreso = "";
                        datoCargoIngreso = "";
                        datoAutorizadoIngreso = "N";
                    } else {
                        if (empleadoIngreso.getEstadoempleado().equals("INACTIVO")) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "INGRESO NO AUTORIZADO", "¡El empleado esta INACTIVO!"));
                            nuevoIngreso.setAutorizadoingreso("N");
                            datoAutorizadoIngreso = "N";
                        } else {
                            Contratos contratoActual = null;
                            List<Contratos> contratos = empleadoIngreso.getContratosList();
                            for (Contratos contrato : contratos) {
                                if (contrato.getFechasalida() == null) {
                                    contratoActual = contrato;
                                }
                            }
                            nuevoIngreso.setContratistaingreso(contratoActual.getCodigocontratista());
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INGRESO AUTORIZADO", ""));
                            nuevoIngreso.setAutorizadoingreso("S");
                            datoAutorizadoIngreso = "S";
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
                Date fechaActual = new Date();
                SimpleDateFormat formato = new SimpleDateFormat("YYYY-MM-dd");
                String fechaA = formato.format(fechaActual);
                System.out.println("Fecha actual " + fechaA);
                listaIngresos = ingresoLogica.consultarIngresosDiarios(fechaA);
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
        if (listaContratistas == null) {
            try {
                listaContratistas = contratistaLogica.consultar();
            } catch (Exception ex) {
                Logger.getLogger(IngresoVista.class.getName()).log(Level.SEVERE, null, ex);
            }
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

    public String validarDia(int dia) {
        String d = "";
        switch (dia) {
            case 1: //Domingo
                d = "DOMINGO";
                break;
            case 2: //Lunes
                d = "LUNES";
                break;
            case 3: //Martes
                d = "MARTES";
                break;
            case 4: //Miercoles
                d = "MIERCOLES";
                break;
            case 5: //Jueves
                d = "JUEVES";
                break;
            case 6: //Viernes
                d = "VIERNES";
                break;
            case 7: //Sabado
                d = "SABADO";
                break;
        }
        return d;
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
                c = contratistaLogica.consultarPorNit(Long.parseLong(txtContratista.getValue().toString()));
                c.setNitcontratista(Long.parseLong(txtContratista.getValue().toString()));
                if (txtFechaIngresoInicioC.getValue() == null || txtFechaIngresoFinC.getValue() == null) {
                    listaIngresos = ingresoDAO.consultarIngresosContratista(c);
                    jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("reportes/reporteContratista.jasper"));
                    nombreReporte = "reporteContratista" + c.getNitcontratista() + ".pdf";
                    if (listaIngresos.isEmpty()) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡No hay Ingresos que coincidan con los Parámetros!"));
                    } else {
                        Contratista objC = contratistaLogica.consultarPorNit(c.getNitcontratista());
                        parametros.put("nitContratista", c.getNitcontratista() + "");
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
                            objDatos.setCedulaempleadoingreso(obj.getEmpleadoingreso().getCedulaempleado() + "");
                            objDatos.setCargoempleadoingreso(obj.getEmpleadoingreso().getCargoempleado());
                            listaIngresosReporte.add(objDatos);
                        }

                        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JRBeanCollectionDataSource(listaIngresosReporte));
                        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                        response.addHeader("Content-disposition", "attachment; filename=" + nombreReporte);
                        ServletOutputStream stream = response.getOutputStream();
                        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
                        FacesContext.getCurrentInstance().responseComplete();
                    }
                } else {
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    Date fechaI = (Date) txtFechaIngresoInicioC.getValue();
                    Date fechaF = (Date) txtFechaIngresoFinC.getValue();
                    int dias = (int) ((fechaF.getTime() - fechaI.getTime()) / 86400000);
                    if (fechaF.before(fechaI)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡La fecha Hasta debe ser anterior a la fecha Desde!"));
                    } else if (dias > 16) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡El rango de fechas debe ser de máximo 16 días!"));

                    } else {
                        jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("reportes/reporteContratistaFechas.jasper"));
                        nombreReporte = "reporteContratista" + c.getNitcontratista() + "xFecha.pdf";
                        fechai = formato.format(fechaI);
                        fechaf = formato.format(fechaF);
                        parametros.put("fechainicio", fechai);
                        parametros.put("fechafin", fechaf);
                        listaIngresos = ingresoDAO.consultarIngresosContratista(c, fechai, fechaf);
                    }
                    if (listaIngresos.isEmpty()) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡No hay Ingresos que coincidan con los Parámetros!"));
                    } else {
                        Contratista objC = contratistaLogica.consultarPorNit(c.getNitcontratista());
                        parametros.put("nitContratista", c.getNitcontratista() + "");
                        parametros.put("nombreContratista", objC.getNombrecontratista());
                        parametros.put("banner", this.getClass().getResourceAsStream("bannerControl.png"));
                        List<DatosIngresoReporte> listaIngresosReporte = new ArrayList<>();
                        List<Integer> listaEmpleados = empleadoFacade.findEmpleadosContratista(objC.getCodigocontratista());
                        parametros.put("dia1", "-");
                        parametros.put("dia2", "-");
                        parametros.put("dia3", "-");
                        parametros.put("dia4", "-");
                        parametros.put("dia5", "-");
                        parametros.put("dia6", "-");
                        parametros.put("dia7", "-");
                        parametros.put("dia8", "-");
                        parametros.put("dia9", "-");
                        parametros.put("dia10", "-");
                        parametros.put("dia11", "-");
                        parametros.put("dia12", "-");
                        parametros.put("dia13", "-");
                        parametros.put("dia14", "-");
                        parametros.put("dia15", "-");
                        parametros.put("dia16", "-");
                        for (Integer objEmpleado : listaEmpleados) {
                            Empleado e = empleadoLogica.consultarPorId(objEmpleado);
                            DatosIngresoReporte objDatos = new DatosIngresoReporte();
                            objDatos.setNombreempleadoingreso(e.getNombreempleado() + " " + e.getApellidoempleado());
                            objDatos.setCedulaempleadoingreso(e.getCedulaempleado() + "");
                            objDatos.setCargoempleadoingreso(e.getCargoempleado());
                            objDatos.setE1("");
                            objDatos.setE2("");
                            objDatos.setE3("");
                            objDatos.setE4("");
                            objDatos.setE5("");
                            objDatos.setE6("");
                            objDatos.setE7("");
                            objDatos.setE8("");
                            objDatos.setE9("");
                            objDatos.setE10("");
                            objDatos.setE11("");
                            objDatos.setE12("");
                            objDatos.setE13("");
                            objDatos.setE14("");
                            objDatos.setE15("");
                            objDatos.setE16("");
                            objDatos.setS1("");
                            objDatos.setS2("");
                            objDatos.setS3("");
                            objDatos.setS4("");
                            objDatos.setS5("");
                            objDatos.setS6("");
                            objDatos.setS7("");
                            objDatos.setS8("");
                            objDatos.setS9("");
                            objDatos.setS10("");
                            objDatos.setS11("");
                            objDatos.setS12("");
                            objDatos.setS13("");
                            objDatos.setS14("");
                            objDatos.setS15("");
                            objDatos.setS16("");

                            Date nuevaFecha = fechaI;
                            for (int j = 1; j <= dias + 1; j++) {

                                java.util.Calendar calendar = java.util.Calendar.getInstance();
                                calendar.setTime(nuevaFecha);
                                String fechaConsulta = formato.format(calendar.getTime());

                                calendar.add(java.util.Calendar.DAY_OF_YEAR, 1);
                                nuevaFecha = calendar.getTime();
                                Ingreso obj = ingresoDAO.consultarIngresoFecha(objC, fechaConsulta, e);

                                SimpleDateFormat formatoH = new SimpleDateFormat("HH:mm:ss");
                                //if (Objects.equals(obj.getEmpleadoingreso().getCedulaempleado(), objEmpleado.getCodigoempleado().getCedulaempleado())) {
                                if (obj != null) {
                                    Date fechaIngreso = obj.getFechaingreso();
                                    int dia = getDayOfTheWeek(fechaIngreso);
                                    Date horaE = obj.getHoraentradaingreso();
                                    Date horaS = obj.getHorasalidaingreso();

                                    switch (j) {
                                        case 1: //Dia 1
                                            parametros.put("dia1", validarDia(dia));
                                            objDatos.setE1(objDatos.getE1() + formatoH.format(horaE) + "\n");
                                            if (horaS != null) {
                                                objDatos.setS1(objDatos.getS1() + formatoH.format(horaS) + "\n");
                                            } else {
                                                objDatos.setHorasalidaingreso(objDatos.getS1() + "- \n");
                                            }
                                            objDatos.setA1(obj.getAutorizadoingreso());
                                            break;
                                        case 2: //Dia 2
                                            parametros.put("dia2", validarDia(dia));
                                            objDatos.setE2(objDatos.getE2() + formatoH.format(horaE) + "\n");
                                            if (horaS != null) {
                                                objDatos.setS2(objDatos.getS2() + formatoH.format(horaS) + "\n");
                                            } else {
                                                objDatos.setHorasalidaingreso(objDatos.getS2() + "-" + "\n");
                                            }
                                            objDatos.setA2(obj.getAutorizadoingreso());
                                            break;
                                        case 3: //Dia 3
                                            parametros.put("dia3", validarDia(dia));
                                            objDatos.setE3(objDatos.getE3() + formatoH.format(horaE) + "\n");
                                            if (horaS != null) {
                                                objDatos.setS3(objDatos.getS3() + formatoH.format(horaS) + "\n");
                                            } else {
                                                objDatos.setHorasalidaingreso(objDatos.getS3() + "-" + "\n");
                                            }
                                            objDatos.setA3(obj.getAutorizadoingreso());
                                            break;
                                        case 4: //Dia 4
                                            parametros.put("dia4", validarDia(dia));
                                            objDatos.setE4(objDatos.getE4() + formatoH.format(horaE) + "\n");
                                            if (horaS != null) {
                                                objDatos.setS4(objDatos.getS4() + formatoH.format(horaS) + "\n");
                                            } else {
                                                objDatos.setHorasalidaingreso(objDatos.getS4() + "-" + "\n");
                                            }
                                            objDatos.setA4(obj.getAutorizadoingreso());
                                            break;
                                        case 5: //Dia 5
                                            parametros.put("dia5", validarDia(dia));
                                            objDatos.setE5(objDatos.getE5() + formatoH.format(horaE) + "\n");
                                            if (horaS != null) {
                                                objDatos.setS5(objDatos.getS5() + formatoH.format(horaS) + "\n");
                                            } else {
                                                objDatos.setHorasalidaingreso(objDatos.getS5() + "-" + "\n");
                                            }
                                            objDatos.setA5(obj.getAutorizadoingreso());
                                            break;
                                        case 6: //Dia 6
                                            parametros.put("dia6", validarDia(dia));
                                            objDatos.setE6(objDatos.getE6() + formatoH.format(horaE) + "\n");
                                            if (horaS != null) {
                                                objDatos.setS6(objDatos.getS6() + formatoH.format(horaS) + "\n");
                                            } else {
                                                objDatos.setHorasalidaingreso(objDatos.getS6() + "-" + "\n");
                                            }
                                            objDatos.setA6(obj.getAutorizadoingreso());
                                            break;
                                        case 7: //Dia 7
                                            parametros.put("dia7", validarDia(dia));
                                            objDatos.setE7(objDatos.getE7() + formatoH.format(horaE) + "\n");
                                            if (horaS != null) {
                                                objDatos.setS7(objDatos.getS7() + formatoH.format(horaS) + "\n");
                                            } else {
                                                objDatos.setHorasalidaingreso(objDatos.getS7() + "-" + "\n");
                                            }
                                            objDatos.setA7(obj.getAutorizadoingreso());
                                            break;
                                        case 8: //Dia 8
                                            parametros.put("dia8", validarDia(dia));
                                            objDatos.setE8(objDatos.getE8() + formatoH.format(horaE) + "\n");
                                            if (horaS != null) {
                                                objDatos.setS8(objDatos.getS8() + formatoH.format(horaS) + "\n");
                                            } else {
                                                objDatos.setHorasalidaingreso(objDatos.getS8() + "-" + "\n");
                                            }
                                            objDatos.setA8(obj.getAutorizadoingreso());
                                            break;
                                        case 9: //Dia 9
                                            parametros.put("dia9", validarDia(dia));
                                            objDatos.setE9(objDatos.getE9() + formatoH.format(horaE) + "\n");
                                            if (horaS != null) {
                                                objDatos.setS9(objDatos.getS9() + formatoH.format(horaS) + "\n");
                                            } else {
                                                objDatos.setHorasalidaingreso(objDatos.getS9() + "-" + "\n");
                                            }
                                            objDatos.setA9(obj.getAutorizadoingreso());
                                            break;
                                        case 10: //Dia 10
                                            parametros.put("dia10", validarDia(dia));
                                            objDatos.setE10(objDatos.getE10() + formatoH.format(horaE) + "\n");
                                            if (horaS != null) {
                                                objDatos.setS10(objDatos.getS10() + formatoH.format(horaS) + "\n");
                                            } else {
                                                objDatos.setHorasalidaingreso(objDatos.getS10() + "-" + "\n");
                                            }
                                            objDatos.setA10(obj.getAutorizadoingreso());
                                            break;
                                        case 11: //Dia 11
                                            parametros.put("dia11", validarDia(dia));
                                            objDatos.setE11(objDatos.getE11() + formatoH.format(horaE) + "\n");
                                            if (horaS != null) {
                                                objDatos.setS11(objDatos.getS11() + formatoH.format(horaS) + "\n");
                                            } else {
                                                objDatos.setHorasalidaingreso(objDatos.getS11() + "-" + "\n");
                                            }
                                            objDatos.setA11(obj.getAutorizadoingreso());
                                            break;
                                        case 12: //Dia 12
                                            parametros.put("dia12", validarDia(dia));
                                            objDatos.setE12(objDatos.getE12() + formatoH.format(horaE) + "\n");
                                            if (horaS != null) {
                                                objDatos.setS12(objDatos.getS12() + formatoH.format(horaS) + "\n");
                                            } else {
                                                objDatos.setHorasalidaingreso(objDatos.getS12() + "-" + "\n");
                                            }
                                            objDatos.setA12(obj.getAutorizadoingreso());
                                            break;
                                        case 13: //Dia 13
                                            parametros.put("dia13", validarDia(dia));
                                            objDatos.setE13(objDatos.getE13() + formatoH.format(horaE) + "\n");
                                            if (horaS != null) {
                                                objDatos.setS13(objDatos.getS13() + formatoH.format(horaS) + "\n");
                                            } else {
                                                objDatos.setHorasalidaingreso(objDatos.getS13() + "-" + "\n");
                                            }
                                            objDatos.setA13(obj.getAutorizadoingreso());
                                            break;
                                        case 14: //Dia 14
                                            parametros.put("dia14", validarDia(dia));
                                            objDatos.setE14(objDatos.getE14() + formatoH.format(horaE) + "\n");
                                            if (horaS != null) {
                                                objDatos.setS14(objDatos.getS6() + formatoH.format(horaS) + "\n");
                                            } else {
                                                objDatos.setHorasalidaingreso(objDatos.getS14() + "-" + "\n");
                                            }
                                            objDatos.setA14(obj.getAutorizadoingreso());
                                            break;
                                        case 15: //Dia 15
                                            parametros.put("dia15", validarDia(dia));
                                            objDatos.setE15(objDatos.getE15() + formatoH.format(horaE) + "\n");
                                            if (horaS != null) {
                                                objDatos.setS15(objDatos.getS15() + formatoH.format(horaS) + "\n");
                                            } else {
                                                objDatos.setHorasalidaingreso(objDatos.getS15() + "-" + "\n");
                                            }
                                            objDatos.setA15(obj.getAutorizadoingreso());
                                            break;
                                        case 16: //Dia 16
                                            parametros.put("dia16", validarDia(dia));
                                            objDatos.setE16(objDatos.getE16() + formatoH.format(horaE) + "\n");
                                            if (horaS != null) {
                                                objDatos.setS16(objDatos.getS16() + formatoH.format(horaS) + "\n");
                                            } else {
                                                objDatos.setHorasalidaingreso(objDatos.getS16() + "-" + "\n");
                                            }
                                            objDatos.setA16(obj.getAutorizadoingreso());
                                            break;
                                    }
                                }
                            }
                            listaIngresosReporte.add(objDatos);
                        }

                        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, new JRBeanCollectionDataSource(listaIngresosReporte));
                        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
                        response.addHeader("Content-disposition", "attachment; filename=" + nombreReporte);
                        ServletOutputStream stream = response.getOutputStream();
                        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
                        FacesContext.getCurrentInstance().responseComplete();
                    }
                }
            }

        } catch (JRException ex) {
            Logger.getLogger(IngresoVista.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IngresoVista.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(IngresoVista.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static int getDayOfTheWeek(Date d) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(d);
        return cal.get(java.util.Calendar.DAY_OF_WEEK);
    }

    public void consultarxContratistaExcel() {
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
                c = contratistaLogica.consultarPorNit(Long.parseLong(txtContratista.getValue().toString()));
                c.setNitcontratista(Long.parseLong(txtContratista.getValue().toString()));
                if (txtFechaIngresoInicioC.getValue() == null || txtFechaIngresoFinC.getValue() == null) {
                    listaIngresos = ingresoDAO.consultarIngresosContratista(c);
                    jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("reportes/reporteContratista.jasper"));
                    nombreReporte = "reporteContratista" + c.getNitcontratista() + ".xls";
                    if (listaIngresos.isEmpty()) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡No hay Ingresos que coincidan con los Parámetros!"));
                    } else {
                        Contratista objC = contratistaLogica.consultarPorNit(c.getNitcontratista());
                        parametros.put("nitContratista", c.getNitcontratista() + "");
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
                            objDatos.setCedulaempleadoingreso(obj.getEmpleadoingreso().getCedulaempleado() + "");
                            objDatos.setCargoempleadoingreso(obj.getEmpleadoingreso().getCargoempleado());
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
                } else {
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    Date fechaI = (Date) txtFechaIngresoInicioC.getValue();
                    Date fechaF = (Date) txtFechaIngresoFinC.getValue();
                    int dias = (int) ((fechaF.getTime() - fechaI.getTime()) / 86400000);
                    if (fechaF.before(fechaI)) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡La fecha Hasta debe ser anterior a la fecha Desde!"));
                    } else if (dias > 16) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡El rango de fechas debe ser de máximo 16 días!"));

                    } else {
                        jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("reportes/reporteContratistaFechas.jasper"));
                        nombreReporte = "reporteContratista" + c.getNitcontratista() + "xFecha.xls";
                        fechai = formato.format(fechaI);
                        fechaf = formato.format(fechaF);
                        parametros.put("fechainicio", fechai);
                        parametros.put("fechafin", fechaf);
                        listaIngresos = ingresoDAO.consultarIngresosContratista(c, fechai, fechaf);
                    }
                    if (listaIngresos.isEmpty()) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡No hay Ingresos que coincidan con los Parámetros!"));
                    } else {
                        Contratista objC = contratistaLogica.consultarPorNit(c.getNitcontratista());
                        parametros.put("nitContratista", c.getNitcontratista() + "");
                        parametros.put("nombreContratista", objC.getNombrecontratista());
                        parametros.put("banner", this.getClass().getResourceAsStream("bannerControl.png"));
                        List<DatosIngresoReporte> listaIngresosReporte = new ArrayList<>();
                        List<Contratos> listaEmpleados = objC.getContratosList();
                        parametros.put("dia1", "-");
                        parametros.put("dia2", "-");
                        parametros.put("dia3", "-");
                        parametros.put("dia4", "-");
                        parametros.put("dia5", "-");
                        parametros.put("dia6", "-");
                        parametros.put("dia7", "-");
                        parametros.put("dia8", "-");
                        parametros.put("dia9", "-");
                        parametros.put("dia10", "-");
                        parametros.put("dia11", "-");
                        parametros.put("dia12", "-");
                        parametros.put("dia13", "-");
                        parametros.put("dia14", "-");
                        parametros.put("dia15", "-");
                        parametros.put("dia16", "-");
                        for (Contratos objEmpleado : listaEmpleados) {
                            DatosIngresoReporte objDatos = new DatosIngresoReporte();
                            if (objEmpleado.getFechasalida() == null) {
                                objDatos.setNombreempleadoingreso(objEmpleado.getCodigoempleado().getNombreempleado() + " " + objEmpleado.getCodigoempleado().getApellidoempleado());
                                objDatos.setCedulaempleadoingreso(objEmpleado.getCodigoempleado().getCedulaempleado() + "");
                                objDatos.setCargoempleadoingreso(objEmpleado.getCodigoempleado().getCargoempleado());
                                objDatos.setE1("");
                                objDatos.setE2("");
                                objDatos.setE3("");
                                objDatos.setE4("");
                                objDatos.setE5("");
                                objDatos.setE6("");
                                objDatos.setE7("");
                                objDatos.setE8("");
                                objDatos.setE9("");
                                objDatos.setE10("");
                                objDatos.setE11("");
                                objDatos.setE12("");
                                objDatos.setE13("");
                                objDatos.setE14("");
                                objDatos.setE15("");
                                objDatos.setE16("");
                                objDatos.setS1("");
                                objDatos.setS2("");
                                objDatos.setS3("");
                                objDatos.setS4("");
                                objDatos.setS5("");
                                objDatos.setS6("");
                                objDatos.setS7("");
                                objDatos.setS8("");
                                objDatos.setS9("");
                                objDatos.setS10("");
                                objDatos.setS11("");
                                objDatos.setS12("");
                                objDatos.setS13("");
                                objDatos.setS14("");
                                objDatos.setS15("");
                                objDatos.setS16("");

                                Date nuevaFecha = fechaI;
                                for (int j = 1; j <= dias + 1; j++) {

                                    java.util.Calendar calendar = java.util.Calendar.getInstance();
                                    calendar.setTime(nuevaFecha);
                                    String fechaConsulta = formato.format(calendar.getTime());

                                    calendar.add(java.util.Calendar.DAY_OF_YEAR, 1);
                                    nuevaFecha = calendar.getTime();
                                    Ingreso obj = ingresoDAO.consultarIngresoFecha(objC, fechaConsulta, objEmpleado.getCodigoempleado());

                                    SimpleDateFormat formatoH = new SimpleDateFormat("HH:mm:ss");
                                    //if (Objects.equals(obj.getEmpleadoingreso().getCedulaempleado(), objEmpleado.getCodigoempleado().getCedulaempleado())) {
                                    if (obj != null) {
                                        Date fechaIngreso = obj.getFechaingreso();
                                        int dia = getDayOfTheWeek(fechaIngreso);
                                        Date horaE = obj.getHoraentradaingreso();
                                        Date horaS = obj.getHorasalidaingreso();

                                        switch (j) {
                                            case 1: //Dia 1
                                                parametros.put("dia1", validarDia(dia));
                                                objDatos.setE1(objDatos.getE1() + formatoH.format(horaE) + "\n");
                                                if (horaS != null) {
                                                    objDatos.setS1(objDatos.getS1() + formatoH.format(horaS) + "\n");
                                                } else {
                                                    objDatos.setHorasalidaingreso(objDatos.getS1() + "- \n");
                                                }
                                                objDatos.setA1(obj.getAutorizadoingreso());
                                                break;
                                            case 2: //Dia 2
                                                parametros.put("dia2", validarDia(dia));
                                                objDatos.setE2(objDatos.getE2() + formatoH.format(horaE) + "\n");
                                                if (horaS != null) {
                                                    objDatos.setS2(objDatos.getS2() + formatoH.format(horaS) + "\n");
                                                } else {
                                                    objDatos.setHorasalidaingreso(objDatos.getS2() + "-" + "\n");
                                                }
                                                objDatos.setA2(obj.getAutorizadoingreso());
                                                break;
                                            case 3: //Dia 3
                                                parametros.put("dia3", validarDia(dia));
                                                objDatos.setE3(objDatos.getE3() + formatoH.format(horaE) + "\n");
                                                if (horaS != null) {
                                                    objDatos.setS3(objDatos.getS3() + formatoH.format(horaS) + "\n");
                                                } else {
                                                    objDatos.setHorasalidaingreso(objDatos.getS3() + "-" + "\n");
                                                }
                                                objDatos.setA3(obj.getAutorizadoingreso());
                                                break;
                                            case 4: //Dia 4
                                                parametros.put("dia4", validarDia(dia));
                                                objDatos.setE4(objDatos.getE4() + formatoH.format(horaE) + "\n");
                                                if (horaS != null) {
                                                    objDatos.setS4(objDatos.getS4() + formatoH.format(horaS) + "\n");
                                                } else {
                                                    objDatos.setHorasalidaingreso(objDatos.getS4() + "-" + "\n");
                                                }
                                                objDatos.setA4(obj.getAutorizadoingreso());
                                                break;
                                            case 5: //Dia 5
                                                parametros.put("dia5", validarDia(dia));
                                                objDatos.setE5(objDatos.getE5() + formatoH.format(horaE) + "\n");
                                                if (horaS != null) {
                                                    objDatos.setS5(objDatos.getS5() + formatoH.format(horaS) + "\n");
                                                } else {
                                                    objDatos.setHorasalidaingreso(objDatos.getS5() + "-" + "\n");
                                                }
                                                objDatos.setA5(obj.getAutorizadoingreso());
                                                break;
                                            case 6: //Dia 6
                                                parametros.put("dia6", validarDia(dia));
                                                objDatos.setE6(objDatos.getE6() + formatoH.format(horaE) + "\n");
                                                if (horaS != null) {
                                                    objDatos.setS6(objDatos.getS6() + formatoH.format(horaS) + "\n");
                                                } else {
                                                    objDatos.setHorasalidaingreso(objDatos.getS6() + "-" + "\n");
                                                }
                                                objDatos.setA6(obj.getAutorizadoingreso());
                                                break;
                                            case 7: //Dia 7
                                                parametros.put("dia7", validarDia(dia));
                                                objDatos.setE7(objDatos.getE7() + formatoH.format(horaE) + "\n");
                                                if (horaS != null) {
                                                    objDatos.setS7(objDatos.getS7() + formatoH.format(horaS) + "\n");
                                                } else {
                                                    objDatos.setHorasalidaingreso(objDatos.getS7() + "-" + "\n");
                                                }
                                                objDatos.setA7(obj.getAutorizadoingreso());
                                                break;
                                            case 8: //Dia 8
                                                parametros.put("dia8", validarDia(dia));
                                                objDatos.setE8(objDatos.getE8() + formatoH.format(horaE) + "\n");
                                                if (horaS != null) {
                                                    objDatos.setS8(objDatos.getS8() + formatoH.format(horaS) + "\n");
                                                } else {
                                                    objDatos.setHorasalidaingreso(objDatos.getS8() + "-" + "\n");
                                                }
                                                objDatos.setA8(obj.getAutorizadoingreso());
                                                break;
                                            case 9: //Dia 9
                                                parametros.put("dia9", validarDia(dia));
                                                objDatos.setE9(objDatos.getE9() + formatoH.format(horaE) + "\n");
                                                if (horaS != null) {
                                                    objDatos.setS9(objDatos.getS9() + formatoH.format(horaS) + "\n");
                                                } else {
                                                    objDatos.setHorasalidaingreso(objDatos.getS9() + "-" + "\n");
                                                }
                                                objDatos.setA9(obj.getAutorizadoingreso());
                                                break;
                                            case 10: //Dia 10
                                                parametros.put("dia10", validarDia(dia));
                                                objDatos.setE10(objDatos.getE10() + formatoH.format(horaE) + "\n");
                                                if (horaS != null) {
                                                    objDatos.setS10(objDatos.getS10() + formatoH.format(horaS) + "\n");
                                                } else {
                                                    objDatos.setHorasalidaingreso(objDatos.getS10() + "-" + "\n");
                                                }
                                                objDatos.setA10(obj.getAutorizadoingreso());
                                                break;
                                            case 11: //Dia 11
                                                parametros.put("dia11", validarDia(dia));
                                                objDatos.setE11(objDatos.getE11() + formatoH.format(horaE) + "\n");
                                                if (horaS != null) {
                                                    objDatos.setS11(objDatos.getS11() + formatoH.format(horaS) + "\n");
                                                } else {
                                                    objDatos.setHorasalidaingreso(objDatos.getS11() + "-" + "\n");
                                                }
                                                objDatos.setA11(obj.getAutorizadoingreso());
                                                break;
                                            case 12: //Dia 12
                                                parametros.put("dia12", validarDia(dia));
                                                objDatos.setE12(objDatos.getE12() + formatoH.format(horaE) + "\n");
                                                if (horaS != null) {
                                                    objDatos.setS12(objDatos.getS12() + formatoH.format(horaS) + "\n");
                                                } else {
                                                    objDatos.setHorasalidaingreso(objDatos.getS12() + "-" + "\n");
                                                }
                                                objDatos.setA12(obj.getAutorizadoingreso());
                                                break;
                                            case 13: //Dia 13
                                                parametros.put("dia13", validarDia(dia));
                                                objDatos.setE13(objDatos.getE13() + formatoH.format(horaE) + "\n");
                                                if (horaS != null) {
                                                    objDatos.setS13(objDatos.getS13() + formatoH.format(horaS) + "\n");
                                                } else {
                                                    objDatos.setHorasalidaingreso(objDatos.getS13() + "-" + "\n");
                                                }
                                                objDatos.setA13(obj.getAutorizadoingreso());
                                                break;
                                            case 14: //Dia 14
                                                parametros.put("dia14", validarDia(dia));
                                                objDatos.setE14(objDatos.getE14() + formatoH.format(horaE) + "\n");
                                                if (horaS != null) {
                                                    objDatos.setS14(objDatos.getS6() + formatoH.format(horaS) + "\n");
                                                } else {
                                                    objDatos.setHorasalidaingreso(objDatos.getS14() + "-" + "\n");
                                                }
                                                objDatos.setA14(obj.getAutorizadoingreso());
                                                break;
                                            case 15: //Dia 15
                                                parametros.put("dia15", validarDia(dia));
                                                objDatos.setE15(objDatos.getE15() + formatoH.format(horaE) + "\n");
                                                if (horaS != null) {
                                                    objDatos.setS15(objDatos.getS15() + formatoH.format(horaS) + "\n");
                                                } else {
                                                    objDatos.setHorasalidaingreso(objDatos.getS15() + "-" + "\n");
                                                }
                                                objDatos.setA15(obj.getAutorizadoingreso());
                                                break;
                                            case 16: //Dia 16
                                                parametros.put("dia16", validarDia(dia));
                                                objDatos.setE16(objDatos.getE16() + formatoH.format(horaE) + "\n");
                                                if (horaS != null) {
                                                    objDatos.setS16(objDatos.getS16() + formatoH.format(horaS) + "\n");
                                                } else {
                                                    objDatos.setHorasalidaingreso(objDatos.getS16() + "-" + "\n");
                                                }
                                                objDatos.setA16(obj.getAutorizadoingreso());
                                                break;
                                        }
                                    }
                                }
                                listaIngresosReporte.add(objDatos);
                            }

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
                }
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
//            if (txtFechaIngresoInicioE.getValue() == null || txtFechaIngresoFinE.getValue() == null) {
            listaIngresos = ingresoDAO.consultarIngresosEmpleado(e);
//            } else {
//                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
//                Date fechaI = (Date) txtFechaIngresoInicioE.getValue();
//                Date fechaF = (Date) txtFechaIngresoFinE.getValue();
//                if (fechaF.before(fechaI)) {
//                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡La fecha Hasta debe ser anterior a la fecha Desde!"));
//                } else {
//                    String fechai = formato.format(fechaI);
//                    String fechaf = formato.format(fechaF);
//                    listaIngresos = ingresoDAO.consultarIngresosEmpleado(e, fechai, fechaf);
//                }
//            }
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
//                if (txtFechaIngresoInicioE.getValue() == null || txtFechaIngresoFinE.getValue() == null) {
                listaIngresos = ingresoDAO.consultarIngresosEmpleado(e);
                jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("reportes/reporteEmpleado.jasper"));
                nombreReporte = "reporteEmpleado" + e.getCedulaempleado() + ".pdf";
//                } else {
//                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
//                    Date fechaI = (Date) txtFechaIngresoInicioE.getValue();
//                    Date fechaF = (Date) txtFechaIngresoFinE.getValue();
//                    if (fechaF.before(fechaI)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡La fecha Hasta debe ser anterior a la fecha Desde!"));
//                    } else {
//                        jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("reportes/reporteEmpleadoFechas.jasper"));
//                        nombreReporte = "reporteEmpleado" + e.getCedulaempleado() + "xFecha.pdf";
//
//                        fechai = formato.format(fechaI);
//                        fechaf = formato.format(fechaF);
//                        parametros.put("fechai", fechai);
//                        parametros.put("fechafin", fechaf);
//                        listaIngresos = ingresoDAO.consultarIngresosEmpleado(e, fechai, fechaf);
//                    }
//                }
            }

            if (listaIngresos.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡No hay Ingresos que coincidan con los Parámetros!"));
            } else {
                Empleado objE = empleadoLogica.consultarPorDocumento(e.getCedulaempleado());
                parametros.put("banner", this.getClass().getResourceAsStream("bannerControl.png"));
                parametros.put("cedula", objE.getCedulaempleado() + "");
                parametros.put("nombre", objE.getNombreempleado() + " " + objE.getApellidoempleado());
                parametros.put("telefono", objE.getTelefonoempleado());
                parametros.put("cargo", objE.getCargoempleado());
                List<Contratos> contratos = objE.getContratosList();
                List<DatosTabla> listaIngresosReporte = new ArrayList<>();
                for (Contratos obj : contratos) {
                    DatosTabla objDatos = new DatosTabla();
                    SimpleDateFormat formatoF = new SimpleDateFormat("yyyy-MM-dd");
                    Date fecha = obj.getFechaingreso();
                    objDatos.setCodigocontratista(obj.getCodigocontratista().getCodigocontratista());
                    objDatos.setContratistaingreso(obj.getCodigocontratista().getNombrecontratista());
                    if (obj.getFechasalida() == null) {
                        objDatos.setEstado("ACTIVO");
                    } else {
                        objDatos.setEstado("FINALIZADO");
                    }
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat formatoH = new SimpleDateFormat("HH:mm:ss");
                    objDatos.setFechainicio(formato.format(obj.getFechaingreso()));
                    if (obj.getFechasalida() != null) {
                        objDatos.setFechafin(formato.format(obj.getFechasalida()));
                        listaIngresos = ingresoDAO.consultarIngresosContratistaEmpleado(obj.getCodigocontratista(), obj.getCodigoempleado(), formato.format(obj.getFechaingreso()), formato.format(obj.getFechasalida()));
                    } else {
                        objDatos.setFechafin("");
                        listaIngresos = ingresoDAO.consultarIngresosContratistaEmpleado(obj.getCodigocontratista(), obj.getCodigoempleado(), formato.format(obj.getFechaingreso()), null);
                    }
                    List<DatosIngresos> listaDatos = new ArrayList<>();
                    for (Ingreso ingreso : listaIngresos) {
                        DatosIngresos datos = new DatosIngresos();
                        datos.setAutorizadoingreso(ingreso.getAutorizadoingreso());
                        datos.setFechaingreso(formato.format(ingreso.getFechaingreso()));
                        datos.setHoraentradaingreso(formatoH.format(ingreso.getHoraentradaingreso()));
                        if (ingreso.getHorasalidaingreso() == null) {
                            datos.setHorasalidaingreso("");
                        } else {
                            datos.setHorasalidaingreso(formatoH.format(ingreso.getHorasalidaingreso()));
                        }
                        listaDatos.add(datos);
                    }
                    objDatos.setIngresos(listaDatos);
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
//                if (txtFechaIngresoInicioE.getValue() == null || txtFechaIngresoFinE.getValue() == null) {
                listaIngresos = ingresoDAO.consultarIngresosEmpleado(e);
                jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("reportes/reporteEmpleado.jasper"));
                nombreReporte = "reporteEmpleado" + e.getCedulaempleado() + ".xls";
//                } else {
//                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
//                    Date fechaI = (Date) txtFechaIngresoInicioE.getValue();
//                    Date fechaF = (Date) txtFechaIngresoFinE.getValue();
//                    if (fechaF.before(fechaI)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡La fecha Hasta debe ser anterior a la fecha Desde!"));
//                    } else {
//                        jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("reportes/reporteEmpleadoFechas.jasper"));
//                        nombreReporte = "reporteEmpleado" + e.getCedulaempleado() + "xFecha.pdf";
//
//                        fechai = formato.format(fechaI);
//                        fechaf = formato.format(fechaF);
//                        parametros.put("fechai", fechai);
//                        parametros.put("fechafin", fechaf);
//                        listaIngresos = ingresoDAO.consultarIngresosEmpleado(e, fechai, fechaf);
//                    }
//                }
            }

            if (listaIngresos.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡No hay Ingresos que coincidan con los Parámetros!"));
            } else {
                Empleado objE = empleadoLogica.consultarPorDocumento(e.getCedulaempleado());
                parametros.put("banner", this.getClass().getResourceAsStream("bannerControl.png"));
                parametros.put("cedula", objE.getCedulaempleado() + "");
                parametros.put("nombre", objE.getNombreempleado() + " " + objE.getApellidoempleado());
                parametros.put("telefono", objE.getTelefonoempleado());
                parametros.put("cargo", objE.getCargoempleado());
                List<Contratos> contratos = objE.getContratosList();
                List<DatosTabla> listaIngresosReporte = new ArrayList<>();
                for (Contratos obj : contratos) {
                    DatosTabla objDatos = new DatosTabla();
                    SimpleDateFormat formatoF = new SimpleDateFormat("yyyy-MM-dd");
                    Date fecha = obj.getFechaingreso();
                    objDatos.setCodigocontratista(obj.getCodigocontratista().getCodigocontratista());
                    objDatos.setContratistaingreso(obj.getCodigocontratista().getNombrecontratista());
                    if (obj.getFechasalida() == null) {
                        objDatos.setEstado("ACTIVO");
                    } else {
                        objDatos.setEstado("FINALIZADO");
                    }
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat formatoH = new SimpleDateFormat("HH:mm:ss");
                    objDatos.setFechainicio(formato.format(obj.getFechaingreso()));
                    if (obj.getFechasalida() != null) {
                        objDatos.setFechafin(formato.format(obj.getFechasalida()));
                        listaIngresos = ingresoDAO.consultarIngresosContratistaEmpleado(obj.getCodigocontratista(), obj.getCodigoempleado(), formato.format(obj.getFechaingreso()), formato.format(obj.getFechasalida()));
                    } else {
                        objDatos.setFechafin("");
                        listaIngresos = ingresoDAO.consultarIngresosContratistaEmpleado(obj.getCodigocontratista(), obj.getCodigoempleado(), formato.format(obj.getFechaingreso()), null);
                    }
                    List<DatosIngresos> listaDatos = new ArrayList<>();
                    for (Ingreso ingreso : listaIngresos) {
                        DatosIngresos datos = new DatosIngresos();
                        datos.setAutorizadoingreso(ingreso.getAutorizadoingreso());
                        datos.setFechaingreso(formato.format(ingreso.getFechaingreso()));
                        datos.setHoraentradaingreso(formatoH.format(ingreso.getHoraentradaingreso()));
                        datos.setHorasalidaingreso(formatoH.format(ingreso.getHorasalidaingreso()));
                    }
                    objDatos.setIngresos(listaDatos);
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
        /////

//        try {
//            String fechai = "";
//            String fechaf = "";
//            File jasper = null;
//            String nombreReporte = "";
//            Map<String, Object> parametros = new HashMap<>();
//            Empleado e = new Empleado();
//            if (txtEmpleado.getValue().toString().equals("")) {
//                listaIngresos = ingresoDAO.findAll();
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡Debe seleccionar el Empleado!"));
//            } else {
//                e.setCedulaempleado(Long.parseLong(txtEmpleado.getValue().toString()));
//                if (txtFechaIngresoInicioE.getValue() == null || txtFechaIngresoFinE.getValue() == null) {
//                    listaIngresos = ingresoDAO.consultarIngresosEmpleado(e);
//                    jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("reportes/reporteEmpleado.jasper"));
//                    nombreReporte = "reporteEmpleado" + e.getCedulaempleado() + ".xls";
//                } else {
//                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
//                    Date fechaI = (Date) txtFechaIngresoInicioE.getValue();
//                    Date fechaF = (Date) txtFechaIngresoFinE.getValue();
//                    if (fechaF.before(fechaI)) {
//                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡La fecha Hasta debe ser anterior a la fecha Desde!"));
//                    } else {
//                        jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("reportes/reporteEmpleadoFechas.jasper"));
//                        fechai = formato.format(fechaI);
//                        fechaf = formato.format(fechaF);
//                        parametros.put("fechai", fechai);
//                        parametros.put("fechafin", fechaf);
//                        nombreReporte = "reporteEmpleado" + e.getCedulaempleado() + "xFecha.xls";
//                        listaIngresos = ingresoDAO.consultarIngresosEmpleado(e, fechai, fechaf);
//                    }
//                }
//            }
//            if (listaIngresos.isEmpty()) {
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "¡No hay Ingresos que coincidan con los Parámetros!"));
//            } else {
//                Empleado objE = empleadoLogica.consultarPorDocumento(e.getCedulaempleado());
//                parametros.put("banner", this.getClass().getResourceAsStream("bannerControl.png"));
//                parametros.put("cedula", objE.getCedulaempleado() + "");
//                parametros.put("nombre", objE.getNombreempleado() + " " + objE.getApellidoempleado());
//                parametros.put("telefono", objE.getTelefonoempleado());
//                parametros.put("correo", "");
//                parametros.put("cargo", objE.getCargoempleado());
//                List<Contratos> contratos = objE.getContratosList();
//                if (objE.getEstadoempleado().equals("ACTIVO")) {
//                    //Arreglar Aqui para mandar el contratista actual
//                    Contratista contratistaActual = null;
//                    for (Contratos contrato : contratos) {
//                        if (contrato.getFechasalida() == null) {
//                            contratistaActual = contrato.getCodigocontratista();
//                        }
//                    }
//                    parametros.put("contratista", contratistaActual.getNombrecontratista());
//                } else {
//                    //Buscar el ultimo contrato que tuvo
//                    Contratista ultimoContratista = null;
//                    Date fechaMayor = contratos.get(0).getFechasalida();
//                    int posicionContrato = 0;
//                    for (int i = 1; i < contratos.size(); i++) {
//                        if (contratos.get(i).getFechasalida().before(fechaMayor)) {
//                            fechaMayor = contratos.get(i).getFechasalida();
//                            posicionContrato = i;
//                        }
//                    }
//                    ultimoContratista = contratos.get(posicionContrato).getCodigocontratista();
//                    parametros.put("contratista", ultimoContratista.getNombrecontratista());
//                }
//                parametros.put("estado", objE.getEstadoempleado());
//                parametros.put("arl", "");
//                parametros.put("eps", "");
//                List<DatosIngresoReporte> listaIngresosReporte = new ArrayList<>();
//                for (Ingreso obj : listaIngresos) {
//                    DatosIngresoReporte objDatos = new DatosIngresoReporte();
//                    SimpleDateFormat formatoF = new SimpleDateFormat("yyyy-MM-dd");
//                    Date fecha = obj.getFechaingreso();
//                    objDatos.setNumeroingreso(obj.getNumeroingreso().toString());
//                    objDatos.setFechaingreso(formatoF.format(fecha));
//                    Date horaE = obj.getHoraentradaingreso();
//                    Date horaS = obj.getHorasalidaingreso();
//                    SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
//                    objDatos.setHoraentradaingreso(formato.format(horaE));
//                    if (obj.getHorasalidaingreso() != null) {
//                        objDatos.setHorasalidaingreso(formato.format(horaS));
//                    } else {
//                        objDatos.setHorasalidaingreso("-");
//                    }
//                    objDatos.setAutorizadoingreso(obj.getAutorizadoingreso());
//                    objDatos.setNombreempleadoingreso(obj.getEmpleadoingreso().getNombreempleado() + " " + obj.getEmpleadoingreso().getApellidoempleado());
//                    listaIngresosReporte.add(objDatos);
//                }
//
//                JasperPrint print = JasperFillManager.fillReport(jasper.getPath(), parametros, new JRBeanCollectionDataSource(listaIngresosReporte));
//                HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//
//                OutputStream out = response.getOutputStream();
//
//                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
//                JRXlsExporter exporterXLS = new JRXlsExporter();
//
//                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
//                exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, arrayOutputStream);
//                exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
//                exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.FALSE);
//                exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
//                exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
//                exporterXLS.exportReport();
//
//                response.setHeader("Content-disposition", "attachment; filename=" + nombreReporte);
//                response.setContentType("application/vnd.ms-excel");
//                response.setContentLength(arrayOutputStream.toByteArray().length);
//                out.write(arrayOutputStream.toByteArray());
//                out.flush();
//                out.close();
//            }
//        } catch (JRException ex) {
//            Logger.getLogger(IngresoVista.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(IngresoVista.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception ex) {
//            Logger.getLogger(IngresoVista.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public void limpiarConsultaContratista() {
        txtContratista.setValue("");
        txtFechaIngresoInicioC.setValue(null);
        txtFechaIngresoFinC.setValue(null);
        listaIngresos = null;

    }

    public void limpiarConsultaEmpleados() {
        txtEmpleado.setValue("");
//        txtFechaIngresoInicioE.setValue(null);
//        txtFechaIngresoFinE.setValue(null);
        listaIngresos = null;
    }

    public String getDatoAutorizadoIngreso() {
        return datoAutorizadoIngreso;
    }

    public void setDatoAutorizadoIngreso(String datoAutorizadoIngreso) {
        this.datoAutorizadoIngreso = datoAutorizadoIngreso;
    }

    public List<Ingreso> getListaIngresosFiltro() {
        return listaIngresosFiltro;
    }

    public void setListaIngresosFiltro(List<Ingreso> listaIngresosFiltro) {
        this.listaIngresosFiltro = listaIngresosFiltro;
    }

    public String getIngresos() {
        List<Ingreso> ingresosHoy = new ArrayList<>();
        try {
            //Llamar a la logica de ingreso            
            Date fechaActual = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("YYYY-MM-dd");
            String fechaA = formato.format(fechaActual);
            System.out.println("Fecha actual " + fechaA);
            ingresosHoy = ingresoLogica.consultarIngresosDiarios(fechaA);
            int enObra = 0;
            int noAutorizados = 0;
            for (Ingreso ingreso : ingresosHoy) {
                if (ingreso.getHorasalidaingreso() == null && ingreso.getAutorizadoingreso().equals("S")) {
                    enObra++;
                }
                if (ingreso.getAutorizadoingreso().equals("N")) {
                    noAutorizados++;
                }
            }
            ingresos = "Total de Ingresos empleados hoy: ( " + ingresosHoy.size() + " ). En Obra: ( " + enObra + " ). Salidas: ( " + (ingresosHoy.size() - enObra - noAutorizados) + " ). Ingresos NO autorizados: ( " + noAutorizados + " )";

        } catch (Exception ex) {
            Logger.getLogger(IngresoVista.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ingresos;
    }

    public void setIngresos(String ingresos) {
        this.ingresos = ingresos;
    }

    public String getIngresosC() {
        int ingresosHoy = 0;
        try {
            //Llamar a la logica de ingreso            
            Date fechaActual = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("YYYY-MM-dd");
            String fechaA = formato.format(fechaActual);
            ingresosHoy = ingresoLogica.consultarIngresosContratistasDiarios(fechaA);
            ingresosC = "Total de Contratistas hoy: ( " + ingresosHoy + " ).";

        } catch (Exception ex) {
            Logger.getLogger(IngresoVista.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ingresosC;
    }

    public void setIngresosC(String ingresosC) {
        this.ingresosC = ingresosC;
    }

}
