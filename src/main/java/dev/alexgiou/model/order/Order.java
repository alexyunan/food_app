package dev.alexgiou.model.order;

import dev.alexgiou.model.Address;
import dev.alexgiou.model.BaseModel;
import dev.alexgiou.model.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "\"order\"")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseModel {

    @ManyToOne
    private User customer;

    private Long totalAmount;

    private String orderStatus;

    @ManyToOne
    private Address deliveryAddress;

    @OneToMany
    private List<OrderItem> orderItems;
    private int totalItem;

    private Long totalPrice;
}
