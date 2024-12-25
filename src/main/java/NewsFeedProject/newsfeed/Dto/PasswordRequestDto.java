package NewsFeedProject.newsfeed.Dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PasswordRequestDto {

    @NotBlank
    private  String oldPassword;

    @NotBlank
    private  String newPassword;

    public PasswordRequestDto(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public PasswordRequestDto() { //Todo
    }
}
