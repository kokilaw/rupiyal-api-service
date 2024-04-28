package io.kokilaw.rupiyal.repository.model;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.HashMap;
import java.util.Map;

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

    @Column(name="logo")
    @Type(JsonType.class)
    @Builder.Default
    private Map<String, String> logo = new HashMap<>();

    @Column(name="theme_config")
    @Type(JsonType.class)
    @Builder.Default
    private Map<String, String> themeConfig = new HashMap<>();

}
