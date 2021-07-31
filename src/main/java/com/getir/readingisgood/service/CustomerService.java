package com.getir.readingisgood.service;

import com.getir.readingisgood.entity.User;
import com.getir.readingisgood.exceptions.ResourceAlreadyExistsException;
import com.getir.readingisgood.model.modelmapper.OrderMapper;
import com.getir.readingisgood.model.modelmapper.UserMapper;
import com.getir.readingisgood.model.request.CustomerRequest;
import com.getir.readingisgood.model.response.CustomerOrderResponse;
import com.getir.readingisgood.model.response.GenericReturnValue;
import com.getir.readingisgood.repository.OrderRepository;
import com.getir.readingisgood.repository.UserRepository;
import com.getir.readingisgood.security.service.UserDetailsDaoService;
import com.getir.readingisgood.utils.ErrorMessages;
import com.getir.readingisgood.utils.ValidateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final UserMapper userMapper;
    private final OrderMapper orderMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsDaoService userDetailsDaoService;
    private final ValidateUtil validateUtil;

    @Transactional
    public GenericReturnValue<Long> createCustomer(CustomerRequest customerRequest) {
        validateUtil.validate(customerRequest);

        if (userRepository.existsByUsernameOrEmail(customerRequest.getUsername(), customerRequest.getEmail()))
            throw new ResourceAlreadyExistsException(ErrorMessages.USERNAME_OR_EMAIL_EXIST);

        customerRequest.setPassword(passwordEncoder.encode(customerRequest.getPassword()));
        User customer = userMapper.toEntity(customerRequest);
        return new GenericReturnValue<>(userRepository.save(customer).getId());
    }

    @Transactional(readOnly = true)
    public GenericReturnValue<Page<CustomerOrderResponse>> getCustomerOrders(Pageable paging) {
        Long currentCustomerId = userDetailsDaoService.getUserId();
        return new GenericReturnValue<>(orderRepository.findAllByCustomerId(currentCustomerId, paging)
                .map(orderMapper::toCustomerOrderResponse));
    }
}
