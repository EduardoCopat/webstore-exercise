package main.account;

public interface Repository<TEntity extends Entity> {
    void save(TEntity entity);
    TEntity getById(String id);
    boolean hasWithId(String id);
}