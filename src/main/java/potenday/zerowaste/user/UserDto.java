package potenday.zerowaste.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private String name;
    private String nickname;
    private String email;

    public static UserDto of(User user) {
        return UserDto.builder()
                .name(user.getName())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build();
    }

}
