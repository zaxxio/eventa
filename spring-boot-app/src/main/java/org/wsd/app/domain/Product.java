package org.wsd.app.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
public class Product implements Serializable {
    @Id
    private UUID id;
    private String productName;
    private Double quantity;
    private Double price;
}
