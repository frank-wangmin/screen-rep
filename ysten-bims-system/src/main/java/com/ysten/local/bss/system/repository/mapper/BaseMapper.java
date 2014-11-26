package com.ysten.local.bss.system.repository.mapper;

import java.util.List;

public interface BaseMapper<E, PK> {

    E get(PK id);

    List<E> findAll();

    int delete(PK id);

    int save(E entity);

    int getCount();

    int saveAll(List<E> entitys);

    int update(E entity);

}
