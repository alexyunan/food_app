package dev.alexgiou.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.alexgiou.dto.RestaurantDto;
import dev.alexgiou.model.Address;
import dev.alexgiou.model.BaseModel;
import dev.alexgiou.model.order.Order;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
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
public class User extends BaseModel {

    private String fullName;

    private String email;

    private String password;

    private ROLE role;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    @ElementCollection
    private List<RestaurantDto> favorites = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

}
