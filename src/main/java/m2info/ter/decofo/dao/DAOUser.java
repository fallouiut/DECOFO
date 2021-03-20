package m2info.ter.decofo.dao;


import m2info.ter.decofo.classes.User;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class DAOUser extends DAO<User> {

    @Override
    User find(int id) {
        User user = this.em.find(User.class, id);

        if(user == null) return null;

        //user.getFormations().size();

        return user;
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
