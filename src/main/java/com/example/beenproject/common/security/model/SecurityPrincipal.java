package com.example.beenproject.common.security.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SecurityPrincipal {
    private Long iuser;
    private String auth;

    @Builder.Default
    private List<String> roles = new ArrayList<>();
}
