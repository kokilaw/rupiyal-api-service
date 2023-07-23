package io.kokilaw.rupiyal.controller.internal;

import io.kokilaw.rupiyal.dto.BankDTO;
import io.kokilaw.rupiyal.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by kokilaw on 2023-07-04
 */
@RestController
@RequestMapping("internal/banks")
public class BankController {

    private final BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping
    public ResponseEntity<List<BankDTO>> getBanks() {
        List<BankDTO> bankDTOS = bankService.getBanks();
        return new ResponseEntity<>(bankDTOS, HttpStatus.OK);
    }

    @GetMapping("/{bankCode}")
    public ResponseEntity<BankDTO> getBank(@PathVariable String bankCode) {
        BankDTO bankDTO = bankService.getBank(bankCode);
        return new ResponseEntity<>(bankDTO, HttpStatus.OK);
    }

}
