/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Empleado;


/**
 *
 * @author arios
 */
@Stateless
public class EmpleadoFacade extends AbstractFacade<Empleado> implements EmpleadoFacadeLocal {
    @PersistenceContext(unitName = "ControlPersonalV1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpleadoFacade() {
        super(Empleado.class);
    }

    @Override
    public Empleado consultarPorDocumento(Long documento) {
       
                  try {
            String jpql = "SELECT a FROM Empleado a WHERE a.cedulaempleado = :cedulaempleado  ";
            Query query = em.createQuery(jpql);
            query.setParameter("cedulaempleado", documento );
  
            List<Empleado> results = query.getResultList();
            Empleado foundEntity = null;
            if (!results.isEmpty()) {
       
                foundEntity = results.get(0);
            }
            return foundEntity;

        } catch (RuntimeException e) {
            throw e;
        }
        
    }
    
    
 
    
}
