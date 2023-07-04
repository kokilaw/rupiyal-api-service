package io.kokilaw.rupiyal.service.impl;

import io.kokilaw.rupiyal.dto.BankDTO;
import io.kokilaw.rupiyal.repository.BankRepository;
import io.kokilaw.rupiyal.repository.model.BankEntity;
import io.kokilaw.rupiyal.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kokilaw on 2023-07-04
 */
@Service
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public List<BankDTO> getBanks() {
        List<BankEntity> bankEntities = bankRepository.findAll();
        return null;
    }

    @Override
    public BankDTO getBank(String bankCode) {
        BankEntity bankEntity = bankRepository.findById(bankCode)
                .orElseThrow(() -> new RuntimeException(String.format("Bank not found for code - %s", bankCode)));
        return null;
    }

}
