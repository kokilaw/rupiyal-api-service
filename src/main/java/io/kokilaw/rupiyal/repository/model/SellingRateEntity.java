package io.kokilaw.rupiyal.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by kokilaw on 2023-06-13
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "selling_rate", uniqueConstraints = @UniqueConstraint(
        name = "currency_entry_unique_key",
        columnNames = {"bank_code", "currency_code", "date", "rate"}
))
public class SellingRateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "date")
    private LocalDate date;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "bank_code")
    private BankEntity bank;

}
