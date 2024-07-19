package com.kundan.CardScheme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardResponseDto {

    private List<String> cardIds;
    private int groupId;
    private String message;

}
