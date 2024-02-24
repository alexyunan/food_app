package dev.alexgiou.model.order;

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
public class OrderItem extends BaseModel {

    @ManyToOne
    private Food food;

    private int quantity;

    private Long totalPrice;

    private List<String> ingredients;
}
