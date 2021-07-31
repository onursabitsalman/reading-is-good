package com.getir.readingisgood.model.modelmapper;

import com.getir.readingisgood.entity.User;
import com.getir.readingisgood.model.request.CustomerRequest;
import com.getir.readingisgood.model.response.CustomerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(CustomerRequest customerRequest);
    CustomerResponse toCustomerResponse(User user);
}
