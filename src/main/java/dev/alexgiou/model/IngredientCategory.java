package dev.alexgiou.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class IngredientCategory extends BaseModel {


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<IngredientsItem> ingredientsItems = new ArrayList<>();
}
