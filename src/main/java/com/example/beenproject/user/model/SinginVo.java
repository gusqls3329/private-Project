package com.example.beenproject.user.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SinginVo {
    private long result;
    private String accesstoken;
    private Long iuser;
}
