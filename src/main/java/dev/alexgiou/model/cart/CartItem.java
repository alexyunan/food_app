package dev.alexgiou.model.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.alexgiou.model.BaseModel;
import dev.alexgiou.model.food.Food;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CartItem extends BaseModel {

    @JsonIgnore
    @ManyToOne
    private Cart cart;

    @ManyToOne
    private Food food;

    private int quantity;

    private List<String> ingredients;

    private Long totalPrice;
}
