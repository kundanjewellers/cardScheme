package com.kundan.CardScheme.service;

import com.kundan.CardScheme.dto.CardRequestDto;
import com.kundan.CardScheme.dto.CardResponseDto;
import com.kundan.CardScheme.dto.CardViewResponseDto;
import com.kundan.CardScheme.dto.UpdateCardResponseDto;
import com.kundan.CardScheme.entity.CardEntity;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CardService {

    String createBatch(String batchName, int batchCount);

    CardResponseDto createCard(CardRequestDto requestDto);

    Map<String, List<String>> getCardDetails(String cardNumber);

    List<String> getActiveBatch();

    Integer getAvailableBatchCount( String batch);

    String updateCard(String cardNumber, int month, String date, boolean allowMultiple, String paymentType) throws ParseException;

    boolean isDraw( String cardNumber);
}
