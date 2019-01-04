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

/**
 *
 * @author dilov
 */
@Stateless
public class ContratistaFacade extends AbstractFacade<Contratista> implements ContratistaFacadeLocal {
    @PersistenceContext(unitName = "ControlPersonalV1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContratistaFacade() {
        super(Contratista.class);
    }
    
    @Override
    public Contratista findNit(Object id) {
        String consulta = "select c from Contratista c where c.nitcontratista = " + id;
        try {
            Query query = em.createQuery(consulta);
            return (Contratista) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    
}
