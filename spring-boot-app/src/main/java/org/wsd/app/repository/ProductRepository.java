package org.wsd.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.wsd.app.domain.Product;

import java.util.UUID;

public interface ProductRepository extends MongoRepository<Product, UUID> {
}
