package org.wsd.app.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.UUID;

@Data
@ToString
@Document
public class Product implements Serializable {
    @Id
    private UUID id;
    private String productName;
    private Double quantity;
    private Double price;
}
