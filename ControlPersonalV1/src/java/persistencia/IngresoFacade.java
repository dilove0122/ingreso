/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import modelo.Empleado;
import modelo.Ingreso;

/**
 *
 * @author DILOVE
 */
@Stateless
public class IngresoFacade extends AbstractFacade<Ingreso> implements IngresoFacadeLocal {
    @PersistenceContext(unitName = "ControlPersonalV1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IngresoFacade() {
        super(Ingreso.class);
    }

    @Override
    public Ingreso consultarIngresoxFecha(String fecha, Empleado empleado) {
        String consulta = "select ingreso from Ingreso ingreso where ingreso.fechaingreso = '"+fecha+"' and ingreso.empleadoingreso.cedulaempleado="+empleado.getCedulaempleado()+" and ingreso.horasalidaingreso=null";
        try{
            Query query = em.createQuery(consulta);
            return (Ingreso) query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
        
    }
    
}
