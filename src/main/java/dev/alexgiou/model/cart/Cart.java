package dev.alexgiou.model.cart;

import dev.alexgiou.model.BaseModel;
import dev.alexgiou.model.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"cart\"")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Cart extends BaseModel {

    @OneToOne
    private User customer;

    private Long total;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart", orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();
}
