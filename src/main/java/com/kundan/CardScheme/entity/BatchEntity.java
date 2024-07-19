package com.kundan.CardScheme.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BatchEntity")
public class BatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "batch_name")
    private String batchName;

    @Column(name = "batch_size")
    private int batchSize;

    @Column(name = "isCompleted")
    private boolean isCompleted;

    @Column(name = "created_at")
    @CreatedDate
    private Date createdAt;

    @Column(name = "counter")
    private int counter;
}
