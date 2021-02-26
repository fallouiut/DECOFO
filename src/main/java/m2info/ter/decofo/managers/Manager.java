package m2info.ter.decofo.managers;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public interface Manager<T> {

    public void insert(T object);
    public void update(T object);

    public void delete(int id) throws Exception;
    public T findOne(int id);
    public List<T> findAll();

}
