package com.ebc.ecard.persistence;

public interface EntityRepository<T, ID> {

    T findById(ID id);
}
