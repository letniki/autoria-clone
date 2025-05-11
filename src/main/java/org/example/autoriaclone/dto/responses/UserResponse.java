package org.example.autoriaclone.dto.responses;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.autoriaclone.dto.UserDto;
import org.example.autoriaclone.view.Views;

@Data
@AllArgsConstructor
public class UserResponse {
    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    public final UserDto user;
    @JsonView({Views.Seller.class, Views.Buyer.class, Views.ManagerAdmin.class})
    public final String error;
}
