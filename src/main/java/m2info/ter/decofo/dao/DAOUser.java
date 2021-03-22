package m2info.ter.decofo.dao;


import m2info.ter.decofo.classes.Formation;
import m2info.ter.decofo.classes.User;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class DAOUser extends DAO<User> {

    @Override
    public User find(int id) {
        User user = this.em.find(User.class, id);

        if(user == null) return null;

        return user;
    }

    public User findByEmailAndPassword(User trial) {
        try {
            String query = "SELECT u FROM User u WHERE u.email = :email AND u.motDePasse = :motDePasse";
            TypedQuery<User> q = this.em.createQuery(query, User.class);

            return q.setParameter("email", trial.getEmail())
                    .setParameter("motDePasse", trial.getMotDePasse())
                    .getResultList().get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public User findByEmail(String email) {
        try {
            String query = "SELECT u FROM User u WHERE u.email = :email";
            TypedQuery<User> q = this.em.createQuery(query, User.class);
            return q.setParameter("email", email).getResultList().get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public void unlinkVisitedFormation(User user, Formation formation) {
        user = this.find(user.getId());

        user.removeVisitedFormation(formation);
        this.update(user);
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
