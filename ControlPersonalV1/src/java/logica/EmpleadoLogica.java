/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import modelo.Empleado;
import persistencia.EmpleadoFacadeLocal;

/**
 *
 * @author arios
 */
@Stateless
public class EmpleadoLogica implements EmpleadoLogicaLocal {

    @EJB
    private EmpleadoFacadeLocal empleadoDAO;

    @Override
    public void registrar(Empleado empleado) throws Exception {

        if (empleado == null) {
            throw new Exception("¡El empleado no existe!");
        }
        Empleado objEmpleado = empleadoDAO.find(empleado.getCedulaempleado());
        if (objEmpleado != null) {
            throw new Exception("¡El empleado que desea registrar ya existe!");
        } else {
            empleadoDAO.create(empleado);
        }

    }

    @Override
    public void modificar(Empleado empleado) throws Exception {

        if (empleado == null) {
            throw new Exception("¡El empleado no existe!");
        }
        Empleado objEmpleado = empleadoDAO.find(empleado.getCedulaempleado());
        if (objEmpleado == null) {
            throw new Exception("¡El empleado que desea modificar no existe!");
        } else {
            
            objEmpleado.setNombreempleado(empleado.getNombreempleado());
            objEmpleado.setApellidoempleado(empleado.getApellidoempleado());
            objEmpleado.setCedulaempleado(empleado.getCedulaempleado());
            objEmpleado.setArlempleado(empleado.getArlempleado());
            objEmpleado.setEpsempleado(empleado.getEpsempleado());
            objEmpleado.setCorreoempleado(empleado.getCorreoempleado());
            objEmpleado.setTelefonoempleado(empleado.getTelefonoempleado());
            objEmpleado.setCargoempleado(empleado.getCargoempleado());
            objEmpleado.setContratistaempleado(empleado.getContratistaempleado());
            empleadoDAO.edit(objEmpleado);
        }

    }

    
    @Override
    public void activar(Empleado empleado) throws Exception {

        if (empleado == null) {
            throw new Exception("¡El empleado esta vacio!");
        }
        Empleado objEmpleado = empleadoDAO.find(empleado.getCedulaempleado());
        if (objEmpleado == null) {
            throw new Exception("¡El empleado no existe!");
        } else {
            objEmpleado.setEstadoempleado("ACTIVO");
            empleadoDAO.edit(objEmpleado);
        }

    }
    
    @Override
    public void inactivar(Empleado empleado) throws Exception {

        if (empleado == null) {
            throw new Exception("¡El empleado esta vacio!");
        }
        Empleado objEmpleado = empleadoDAO.find(empleado.getCedulaempleado());
        if (objEmpleado == null) {
            throw new Exception("¡El empleado no existe!");
        } else {
            objEmpleado.setEstadoempleado("INACTIVO");
            empleadoDAO.edit(objEmpleado);
        }

    }

    @Override
    public Empleado consultarPorId(Integer idEmpleado) throws Exception {

        if (idEmpleado == null || idEmpleado == 0) {
            throw new Exception("¡Número del empleado es incorrecto!");
        } else {
            return empleadoDAO.find((Object) idEmpleado);
        }

    }

    @Override
    public List<Empleado> consultar() throws Exception {

        return empleadoDAO.findAll();
    }

    @Override
    public Empleado consultarPorDocumento(Long documento) throws Exception {

        if (documento == null || documento == 0) {
            throw new Exception("¡Número del empleado es incorrecto!");
        } else {
            return empleadoDAO.find(documento);
        }

    }

    @Override
    public void limpiarCache() {
        empleadoDAO.limpiarCache();
    }

}
