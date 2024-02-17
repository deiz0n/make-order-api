package com.deiz0n.makeorder.domain.services;

import java.util.List;
import java.util.UUID;

public interface ServiceCRUD<T> {

    List<T> getResouces();
    T createResource(T newResourceRequest);
    T updateResource(T t, UUID id);
    void deleteResource(UUID id);

}
