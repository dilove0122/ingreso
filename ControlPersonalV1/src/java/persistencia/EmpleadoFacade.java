/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Contratista;
import modelo.Empleado;

/**
 *
 * @author ADMIN
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
    public Empleado findCedula(Object id) {
         String consulta = "select e from Empleado e where e.cedulaempleado = " + id;
        try {
            Query query = em.createQuery(consulta);
            return (Empleado) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public void limpiarCache() {
       getEntityManager().getEntityManagerFactory().getCache().evictAll();
    }
}
