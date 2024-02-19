package com.deiz0n.makeorderapi.domain.services;

import java.util.List;
import java.util.UUID;

public interface ServiceCRUD<T, C> {

    List<T> getResouces();
    C createResource(T newResourceRequest);
    C updateResource(T t, UUID id);
    void deleteResource(UUID id);

}
