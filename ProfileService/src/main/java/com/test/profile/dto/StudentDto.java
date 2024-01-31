package com.test.profile.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudentDto {
    private UUID id;
    private String name;
    private String email;
}
