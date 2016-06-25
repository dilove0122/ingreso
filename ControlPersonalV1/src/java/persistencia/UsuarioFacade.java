/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Usuario;

/**
 *
 * @author DILOVE
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    @PersistenceContext(unitName = "ControlPersonalV1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    @Override
    public Usuario findUsuario(String nombre) {
        getEntityManager().getEntityManagerFactory().getCache().evictAll();
        try {
            String jpql = "SELECT u FROM Usuario u WHERE u.nombreusuario = :nombreusuario";
            Query query = em.createQuery(jpql);
            query.setParameter("nombreusuario", nombre);
            
            return (Usuario) query.getSingleResult();

        } catch (RuntimeException e) {
            return null;
        }

    }

    @Override
    public Usuario consultarPorClave(String clave) {

        try {
            String jpql = "SELECT a FROM Usuario a WHERE a.claveusuario = :claveusuario  ";
            Query query = em.createQuery(jpql);
            query.setParameter("claveusuario", clave);

            List<Usuario> results = query.getResultList();
            Usuario foundEntity = null;
            if (!results.isEmpty()) {

                foundEntity = results.get(0);
            }
            return foundEntity;

        } catch (RuntimeException e) {
            throw e;
        }

    }

}
