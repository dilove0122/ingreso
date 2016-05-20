/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logica;

import java.util.List;
import javax.ejb.Local;
import modelo.Empleado;


/**
 *
 * @author arios
 */
@Local
public interface EmpleadoLogicaLocal {
    
    
   public void registrar(Empleado empleado) throws Exception;

    public void modificar(Empleado empleado) throws Exception;
    
    public void activar(Empleado empleado) throws Exception;

    public void inactivar(Empleado empleado) throws Exception;
    

    public Empleado consultarPorId(Integer idEmpleado) throws Exception;
        
    public Empleado consultarPorDocumento(Long documento) throws Exception;

    public List<Empleado> consultar() throws Exception;
    
}
