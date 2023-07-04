package io.kokilaw.rupiyal.service;

import io.kokilaw.rupiyal.dto.BankDTO;

import java.util.List;

/**
 * Created by kokilaw on 2023-07-04
 */
public interface BankService {

    List<BankDTO> getBanks();
    BankDTO getBank(String bankCode);

}
