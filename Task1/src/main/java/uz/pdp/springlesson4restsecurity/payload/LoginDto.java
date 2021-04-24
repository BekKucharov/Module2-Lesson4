package uz.pdp.springlesson4restsecurity.payload;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
}
