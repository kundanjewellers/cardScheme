package com.kundan.CardScheme.repository;


import com.kundan.CardScheme.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {

    PaymentEntity findPaymentTypeByCardIdAndMonth(String cardNumber, String month);
}
