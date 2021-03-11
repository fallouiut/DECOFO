package m2info.ter.decofo.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public abstract class DAO<T> {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    protected EntityManager em;

    /**
     * a redefinir pour chaque dao
     * @param id
     * @return
     */
    abstract T find(int id);

    /**
     * a redefinir
     * @return
     */
    public abstract List<T> findAll();

    public void insert(T object) {
        this.em.persist(object);
        this.em.flush();
    }

    public void update(T object) {
        try {
            this.em.merge(object);
        } catch (Exception e) {
            System.out.println("DAO.update()");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete(T object) {
        object = em.merge(object);
        em.remove(object);
    }
}
