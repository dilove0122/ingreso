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
import modelo.Ingreso;


import persistencia.IngresoFacadeLocal;

/**
 *
 * @author arios
 */
@Stateless
public class IngresoLogica implements IngresoLogicaLocal {

    @EJB
   private IngresoFacadeLocal ingresoDAO;

    @Override
    public void registrar(Ingreso ingreso) throws Exception {
        
        
       ingresoDAO.create(ingreso);
    }

    @Override
    public void modificar(Ingreso ingreso) throws Exception {
      
        ingresoDAO.edit(ingreso);
        
    }

    @Override
    public void eliminar(Ingreso ingreso) throws Exception {
        
      
        ingresoDAO.remove(ingreso);
        
    }

 

    @Override
    public List<Ingreso> consultar() throws Exception {
        
          return ingresoDAO.findAll();
    }

    @Override
    public Ingreso consultarxFecha(String fecha, Empleado empleado) throws Exception {
        return ingresoDAO.consultarIngresoxFecha(fecha, empleado);
    }

    @Override
    public List<Ingreso> consultarIngresosDiarios(String fechaf) throws Exception {
        return ingresoDAO.consultarIngresosDiarios(fechaf);
    } 

    @Override
    public int consultarIngresosContratistasDiarios(String fecha) throws Exception {
        return ingresoDAO.consultarIngresosContratistasDiarios(fecha);
    }
}
