package lk.sajith.dep8.polling.dao;

import lk.sajith.dep8.polling.entity.SuperEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CrudDAO<T extends SuperEntity, ID extends Serializable> extends SuperDAO {
    T save(T entity);

    void deleteById(ID pk);

    Optional<T> findById(ID pk);

    List<T> findAll();

    long count();

    boolean existsById(ID pk);
}
