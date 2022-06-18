package lk.sajith.dep8.polling.dao;

import lk.sajith.dep8.polling.entity.SuperEntity;

import java.io.Serializable;

public interface CrudDAO<T extends SuperEntity, ID extends Serializable> extends SuperDAO {
}
