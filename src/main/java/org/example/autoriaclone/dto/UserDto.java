package org.example.autoriaclone.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.example.autoriaclone.entity.Car;
import org.example.autoriaclone.view.Views;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class UserDto {
    @JsonView({Views.ManagerAdmin.class, Views.Buyer.class, Views.Seller.class})
    private Integer id;
    @JsonView({Views.ManagerAdmin.class, Views.Buyer.class, Views.Seller.class})
    @NotBlank(message = "username required")
    @Size(min = 3, max = 20, message = "username: min: {min}, max: {max} characters")
    private String username;
    @JsonView({Views.ManagerAdmin.class, Views.Buyer.class, Views.Seller.class})
    @NotBlank(message = "password required")
    @Pattern(regexp = "^(?=.*\\d).{4,8}$", flags = Pattern.Flag.UNICODE_CASE, message = "Password must be between 4 and 8 characters long and contain at least one number.")
    private String password;
    @JsonView({Views.ManagerAdmin.class, Views.Buyer.class, Views.Seller.class})
    @Email(message = "Not a email")
    private String email;
    @JsonView({Views.ManagerAdmin.class, Views.Buyer.class, Views.Seller.class})
    @Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$", message = "Invalid phone number")
    @Size(min = 10, max = 10, message = "phone: min: {min}, max: {max} characters")
    private String phone;
    @JsonView({Views.ManagerAdmin.class})
    private boolean premium;
    @JsonView(Views.ManagerAdmin.class)
    private boolean enabled;
    @JsonView(Views.ManagerAdmin.class)
    private String role;
    @JsonView({Views.ManagerAdmin.class, Views.Buyer.class, Views.Seller.class})
    private List<Car> cars;
    public UserDto(){

    }
}
