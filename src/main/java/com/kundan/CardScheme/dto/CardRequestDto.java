package com.kundan.CardScheme.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardRequestDto {
    @NotBlank
    @NotNull
    private String batchName;

    @NotEmpty
    private String customerName;

    @NotBlank
    @NotNull
    @Pattern(regexp="\\d{10}", message="Mobile number must be 10 digits")
    private long mobileNumber;

    @NotNull
    private int amount;

}
