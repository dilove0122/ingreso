/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import javax.ejb.Local;
import modelo.Contratista;
import modelo.Empleado;
import modelo.Ingreso;

/**
 *
 * @author ADMIN
 */
@Local
public interface IngresoFacadeLocal {

    void create(Ingreso ingreso);

    void edit(Ingreso ingreso);

    void remove(Ingreso ingreso);

    Ingreso find(Object id);

    List<Ingreso> findAll();

    List<Ingreso> findRange(int[] range);

    int count();
    
    Ingreso consultarIngresoxFecha(String fecha, Empleado empleado);
    
    List<Ingreso> consultarIngresosContratista(Contratista c);
    
    List<Ingreso> consultarIngresosContratista(Contratista c, String fechai, String fechaf);
    
    List<Ingreso> consultarIngresosEmpleado(Empleado e);
    
    List<Ingreso> consultarIngresosEmpleado(Empleado e, String fechai, String fechaf);
    
    List<Ingreso> consultarIngresosDiarios(String fechaf);
    
}
