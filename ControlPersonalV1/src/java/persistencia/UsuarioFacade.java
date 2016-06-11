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
    public Usuario consultarPorClave(String clave, String nombre) {

        try {
            String jpql = "SELECT a FROM Usuario a WHERE a.claveusuario = :claveusuario  AND a.nombreusuario= :nombreusuario ";
            Query query = em.createQuery(jpql);
            query.setParameter("claveusuario", clave);
            query.setParameter("nombreusuario", nombre);

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
