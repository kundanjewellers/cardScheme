package com.kundan.CardScheme.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CardEntity")
public class CardEntity {

    @Id
    @Column(name = "card_id")
    private String cardId;

    @Column(name = "batch_name")
    private String batchName;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "mobile_number")
    private long mobileNumber;

    @Column(name = "amount")
    private int amount;

    @Column(name = "group_id",  columnDefinition = "INT DEFAULT 0")
    private int groupId;

    @Column(name = "year")
    private int year;

    @Column(name = "is_completed")
    private boolean isCompleted;

    @Column(name = "isDraw")
    private boolean isDraw;

    @Column(name = "created_at")
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(name = "first")
    private Date first;

    @Column(name = "second")
    private Date second;

    @Column(name = "third")
    private Date third;

    @Column(name = "fourth")
    private Date fourth;

    @Column(name = "fifth")
    private Date fifth;

    @Column(name = "sixth")
    private Date sixth;

    @Column(name = "seventh")
    private Date seventh;

    @Column(name = "eighth")
    private Date eighth;

    @Column(name = "ninth")
    private Date ninth;

    @Column(name = "tenth")
    private Date tenth;

    @Column(name = "eleventh")
    private Date eleventh;

    @Column(name = "twelfth")
    private Date twelfth;

    @Column(name = "thirteen")
    private Date thirteen;

    @Column(name = "fourteen")
    private Date fourteen;

    @Column(name = "fifteenth")
    private Date fifteenth;

}
