package io.kokilaw.rupiyal.repository;

import io.kokilaw.rupiyal.repository.model.SellingRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by kokilaw on 2023-06-13
 */
@Repository
public interface SellingRateRepository extends JpaRepository<SellingRateEntity, Long> {
}
