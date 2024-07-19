package com.kundan.CardScheme.controller;

import com.kundan.CardScheme.dto.CardRequestDto;
import com.kundan.CardScheme.dto.CardResponseDto;
import com.kundan.CardScheme.dto.UpdateCardResponseDto;
import com.kundan.CardScheme.entity.CardEntity;
import com.kundan.CardScheme.repository.CardRepository;
import com.kundan.CardScheme.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    CardService cardService;

    @PostMapping("/batch/{name}/{size}")
    public String createBatch(@PathVariable("name") String name, @PathVariable("size") int size) {
        return cardService.createBatch(name.toUpperCase(), size);
    }

    @PostMapping
    public CardResponseDto createCard(@RequestBody @Valid CardRequestDto requestDto) throws Exception {
        try {
            return cardService.createCard(requestDto);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @GetMapping("{cardNumber}")
    public Map<String, List<String>> getCard(@PathVariable(value = "cardNumber") String cardNumber){

        return cardService.getCardDetails(cardNumber);
    }

    @GetMapping("/batch/active")
    public List<String> getActiveBatch(){

        return cardService.getActiveBatch().stream().sorted().toList() ;
    }

    @GetMapping("/batch/available/count")
    public Integer getAvailableBatchCount(@RequestParam(value = "batchNumber") String batchNumber) {

            if (ObjectUtils.isEmpty(batchNumber)){
                return null;
            }
            return cardService.getAvailableBatchCount( batchNumber);
    }

    @PutMapping("/entry")
    public String updateCard(@RequestParam("cardNumber") String cardNumber,
                             @RequestParam("month") int month,
                             @RequestParam(value = "date", required = false) String date,
                             @RequestParam(value = "allowMultiple") boolean allowMultiple,
                             @RequestParam(value = "paymentType") String paymentType ) throws Exception {
        try {
            if (month<=0){
                return "Month Required";
            }
            return cardService.updateCard(cardNumber, month, date, allowMultiple, paymentType);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @GetMapping("/draw/{cardNumber}")
    public boolean isDraw(@PathVariable(value = "cardNumber") String cardNumber) {
        return cardService.isDraw( cardNumber);
    }
}
