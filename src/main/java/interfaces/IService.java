package interfaces;

import java.util.List;

public interface IService<T> {
    void addEntity(T t);
    void updateEntity(T t);
    void DeleteEntity(T t);
    List<T> getAllData();
}
