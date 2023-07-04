package io.kokilaw.rupiyal.mapper;

import io.kokilaw.rupiyal.dto.BankDTO;
import io.kokilaw.rupiyal.repository.model.BankEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by kokilaw on 2023-07-04
 */
@Mapper(componentModel = "spring")
public interface BankMapper {

    BankMapper INSTANCE = Mappers.getMapper(BankMapper.class);

    BankDTO convert(BankEntity bankEntity);

}
