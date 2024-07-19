package com.kundan.CardScheme.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PaymentEntity")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "cardId")
    private String cardId;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "month")
    private String month;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "amount")
    private int amount;

    @Column(name = "batch")
    private String batch;


}
