package io.kokilaw.rupiyal.repository.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by kokilaw on 2023-06-13
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bank")
public class BankEntity {

    @Id
    @Column(name="bank_code")
    private String bankCode;

    @Column(name="short_name")
    private String shortName;

    @Column(name="long_name")
    private String longName;

}
