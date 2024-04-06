package com.example.beenproject.user.model;

import com.example.beenproject.common.ErrorMessage;
import com.example.beenproject.eneities.enums.ProvideType;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
public class UserSelDto {
    @NotBlank(message = ErrorMessage.CAN_NOT_BLANK_EX_MESSAGE)
    private String uid;

    @Range(min = 1)
    private Long iuser;

    private String providerType;
}
