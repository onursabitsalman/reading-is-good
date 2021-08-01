package com.getir.readingisgood.service;

import com.getir.readingisgood.entity.Order;
import com.getir.readingisgood.entity.User;
import com.getir.readingisgood.entity.enums.RoleType;
import com.getir.readingisgood.exceptions.ResourceAlreadyExistsException;
import com.getir.readingisgood.model.modelmapper.OrderMapper;
import com.getir.readingisgood.model.modelmapper.UserMapper;
import com.getir.readingisgood.model.request.CustomerRequest;
import com.getir.readingisgood.model.response.CustomerOrderResponse;
import com.getir.readingisgood.model.response.GenericReturnValue;
import com.getir.readingisgood.repository.OrderRepository;
import com.getir.readingisgood.repository.UserRepository;
import com.getir.readingisgood.security.service.UserDetailsDaoService;
import com.getir.readingisgood.utils.ValidateUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserDetailsDaoService userDetailsDaoService;

    @Mock
    private ValidateUtil validateUtil;

    @Test
    public void createCustomer_successfully() {
        CustomerRequest customerRequest = CustomerRequest.builder()
                .email("onur.sabit.salman@getir.com")
                .name("Onur Sabit")
                .surname("Salman")
                .password("test")
                .username("onursabitsalman")
                .roleType(RoleType.CUSTOMER)
                .build();

        User customer = User.builder()
                .email("onur.sabit.salman@getir.com")
                .name("Onur Sabit")
                .surname("Salman")
                .password("test")
                .username("onursabitsalman")
                .roleType(RoleType.CUSTOMER)
                .build();

        when(userRepository.existsByUsernameOrEmail(anyString(), anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(customer);

        GenericReturnValue<Long> result = customerService.createCustomer(customerRequest);
        assertEquals(new GenericReturnValue<>(customer.getId()).getValue(), result.getValue());
    }

    @Test
    public void createCustomer_withExistCustomer_expectedResourceAlreadyExistsException() {
        CustomerRequest customerRequest = CustomerRequest.builder()
                .email("onur.sabit.salman@getir.com")
                .name("Onur Sabit")
                .surname("Salman")
                .password("test")
                .username("onursabitsalman")
                .roleType(RoleType.CUSTOMER)
                .build();

        when(userRepository.existsByUsernameOrEmail(anyString(), anyString())).thenReturn(true);

        assertThrows(ResourceAlreadyExistsException.class, () -> customerService.createCustomer(customerRequest));
    }

    @Test
    public void getCustomerOrders_successfully() {
        Pageable pageable = Pageable.unpaged();
        Page<Order> orders = Page.empty(pageable);
        Page<CustomerOrderResponse> customerOrders = Page.empty(pageable);


        when(userDetailsDaoService.getUserId()).thenReturn(1L);
        when(orderRepository.findAllByCustomerId(anyLong(), eq(pageable))).thenReturn(orders);

        GenericReturnValue<Page<CustomerOrderResponse>> result = customerService.getCustomerOrders(pageable);
        assertEquals(new GenericReturnValue<>(customerOrders).getValue(), result.getValue());
    }

}