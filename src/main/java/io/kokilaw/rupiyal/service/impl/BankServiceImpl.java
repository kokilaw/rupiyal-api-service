package io.kokilaw.rupiyal.service.impl;

import io.kokilaw.rupiyal.dto.BankDTO;
import io.kokilaw.rupiyal.mapper.BankMapper;
import io.kokilaw.rupiyal.repository.BankRepository;
import io.kokilaw.rupiyal.repository.model.BankEntity;
import io.kokilaw.rupiyal.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kokilaw on 2023-07-04
 */
@Service
public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository;
    private final BankMapper bankMapper;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository, BankMapper bankMapper) {
        this.bankRepository = bankRepository;
        this.bankMapper = bankMapper;
    }

    @Override
    public List<BankDTO> getBanks() {
        List<BankEntity> bankEntities = bankRepository.findAll();
        return bankEntities.stream().map(bankMapper::convert).collect(Collectors.toList());
    }

    @Override
    public BankDTO getBank(String bankCode) {
        BankEntity bankEntity = bankRepository.findById(bankCode)
                .orElseThrow(() -> new RuntimeException(String.format("Bank not found for code - %s", bankCode)));
        return bankMapper.convert(bankEntity);
    }

}
