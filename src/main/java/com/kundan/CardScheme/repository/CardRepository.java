package com.kundan.CardScheme.repository;

import com.kundan.CardScheme.entity.CardEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Map;

public interface CardRepository extends JpaRepository<CardEntity, String> {

    @Query(value ="select group_id from card_entity order by group_id desc limit 1" , nativeQuery = true)
    Integer findCurrentGroupId();

    CardEntity findByCardId(String cardNumber);

    @Query(value ="select :month from card_entity where card_id=:cardNumber" , nativeQuery = true)
    Date isMonthPaid(String month, String cardNumber);

    @Transactional
    @Modifying
    @Query(value = "update card_entity set first = :date where card_id= :cardNumber", nativeQuery = true)
    void updateCard( @Param("cardNumber") String cardNumber, @Param("date") Date date);


}
