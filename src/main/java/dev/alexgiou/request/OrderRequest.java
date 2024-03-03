package dev.alexgiou.request;

import dev.alexgiou.model.Address;
import lombok.Data;

@Data
public class OrderRequest {

    private Long restaurantId;
    private Address address;
}
