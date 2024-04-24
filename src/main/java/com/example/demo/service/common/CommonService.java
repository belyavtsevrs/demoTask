package com.example.demo.service.common;

import com.example.demo.model.AbstractModel;

import java.util.List;

public interface CommonService <E extends AbstractModel>{
    E create(E data);
    List<E> findAll();
    E findById(Long id);
}
