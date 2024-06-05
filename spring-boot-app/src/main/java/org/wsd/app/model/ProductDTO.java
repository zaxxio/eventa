package org.wsd.app.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductDTO {
    private UUID id;
    private String productName;
    private Double quantity;
    private Double price;
}
