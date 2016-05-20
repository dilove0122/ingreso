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
import modelo.Contratista;

/**
 *
 * @author arios
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
    public Contratista consultarPorNit(Long nit) {
       
                  try {
            String jpql = "SELECT a FROM Contratista a WHERE a.nitcontratista = :nitcontratista  ";
            Query query = em.createQuery(jpql);
            query.setParameter("nitcontratista", nit );
  
            List<Contratista> results = query.getResultList();
            Contratista foundEntity = null;
            if (!results.isEmpty()) {
       
                foundEntity = results.get(0);
            }
            return foundEntity;

        } catch (RuntimeException e) {
            throw e;
        }
        
    }
    
}
