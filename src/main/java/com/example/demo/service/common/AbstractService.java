package com.example.demo.service.common;

import com.example.demo.model.AbstractModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class AbstractService <E extends AbstractModel , T extends JpaRepository<E,Long>> implements CommonService<E> {
    protected T storage;
    public AbstractService(T storage) {
        this.storage = storage;
    }
    @Override
    public E create(E data) {
        return storage.save(data);
    }
    @Override
    public List<E> findAll() {
        return storage.findAll();
    }
    @Override
    public E findById(Long id) {
        return storage.findById(id).orElse(null);
    }
}
