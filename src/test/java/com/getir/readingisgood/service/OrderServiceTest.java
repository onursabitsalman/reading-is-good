package com.getir.readingisgood.service;

import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.entity.Order;
import com.getir.readingisgood.entity.User;
import com.getir.readingisgood.entity.enums.OrderStatusType;
import com.getir.readingisgood.entity.enums.RoleType;
import com.getir.readingisgood.exceptions.CustomException;
import com.getir.readingisgood.exceptions.ResourceNotFoundException;
import com.getir.readingisgood.model.modelmapper.OrderMapper;
import com.getir.readingisgood.model.request.OrderRequest;
import com.getir.readingisgood.model.response.BookResponse;
import com.getir.readingisgood.model.response.CustomerResponse;
import com.getir.readingisgood.model.response.GenericReturnValue;
import com.getir.readingisgood.model.response.OrderResponse;
import com.getir.readingisgood.repository.BookRepository;
import com.getir.readingisgood.repository.OrderRepository;
import com.getir.readingisgood.repository.UserRepository;
import com.getir.readingisgood.security.service.UserDetailsDaoService;
import com.getir.readingisgood.utils.ResponseMessages;
import com.getir.readingisgood.utils.ValidateUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDetailsDaoService userDetailsDaoService;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private ValidateUtil validateUtil;

    @Test
    public void createOrder_successfully() {
        OrderRequest orderRequest = OrderRequest.builder()
                .bookId(1L)
                .customerId(1L)
                .quantity(10L)
                .status(OrderStatusType.PURCHASED)
                .build();

        User customer = User.builder()
                .email("onur.sabit.salman@getir.com")
                .name("Onur Sabit")
                .surname("Salman")
                .password("test")
                .username("onursabitsalman")
                .roleType(RoleType.CUSTOMER)
                .build();

        Book book = Book.builder()
                .id(1L)
                .author("Onur Sabit Salman")
                .name("Effective Java")
                .page(500L)
                .price(19.99)
                .stock(100L)
                .build();

        Order order = Order.builder()
                .quantity(10L)
                .status(OrderStatusType.PURCHASED)
                .build();

        when(orderMapper.toEntity(any())).thenReturn(order);
        when(userDetailsDaoService.getUserId()).thenReturn(1L);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(orderRepository.save(any())).thenReturn(order);

        GenericReturnValue<Long> result = orderService.createOrder(orderRequest);
        assertEquals(new GenericReturnValue<>(customer.getId()).getValue(), result.getValue());

    }

    @Test
    public void createOrder_withNotEnoughStock_expectedCustomException() {
        OrderRequest orderRequest = OrderRequest.builder()
                .bookId(1L)
                .customerId(1L)
                .quantity(10L)
                .status(OrderStatusType.PURCHASED)
                .build();

        User customer = User.builder()
                .email("onur.sabit.salman@getir.com")
                .name("Onur Sabit")
                .surname("Salman")
                .password("test")
                .username("onursabitsalman")
                .roleType(RoleType.CUSTOMER)
                .build();

        Book book = Book.builder()
                .id(1L)
                .author("Onur Sabit Salman")
                .name("Effective Java")
                .page(500L)
                .price(19.99)
                .stock(1L)
                .build();

        Order order = Order.builder()
                .quantity(10L)
                .status(OrderStatusType.PURCHASED)
                .build();

        when(orderMapper.toEntity(any())).thenReturn(order);
        when(userDetailsDaoService.getUserId()).thenReturn(1L);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));

        assertThrows(CustomException.class, () -> orderService.createOrder(orderRequest));

    }

    @Test
    public void createOrder_withCouldNotFindBook_expectedResourceAlreadyExistsException() {
        OrderRequest orderRequest = OrderRequest.builder()
                .bookId(1L)
                .customerId(1L)
                .quantity(10L)
                .status(OrderStatusType.PURCHASED)
                .build();

        User customer = User.builder()
                .email("onur.sabit.salman@getir.com")
                .name("Onur Sabit")
                .surname("Salman")
                .password("test")
                .username("onursabitsalman")
                .roleType(RoleType.CUSTOMER)
                .build();

        Order order = Order.builder()
                .quantity(10L)
                .status(OrderStatusType.PURCHASED)
                .build();

        when(orderMapper.toEntity(any())).thenReturn(order);
        when(userDetailsDaoService.getUserId()).thenReturn(1L);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.createOrder(orderRequest));

    }

    @Test
    public void getOrders_successfully() {
        Pageable pageable = Pageable.unpaged();
        Page<Order> orders = Page.empty(pageable);
        Page<OrderResponse> orderResponses = Page.empty(pageable);

        when(orderRepository.findAll(eq(pageable))).thenReturn(orders);

        GenericReturnValue<Page<OrderResponse>> result = orderService.getOrders(pageable);
        assertEquals(new GenericReturnValue<>(orderResponses).getValue(), result.getValue());
    }

    @Test
    public void getOrdersWithDateInterval_successfully() {
        Date startDate = new Date();
        Date endDate = new Date();
        Pageable pageable = Pageable.unpaged();
        Page<Order> orders = Page.empty(pageable);
        Page<OrderResponse> orderResponses = Page.empty(pageable);

        when(orderRepository.findAllByCreatedDateIsBetween(any(), any(), eq(pageable))).thenReturn(orders);

        GenericReturnValue<Page<OrderResponse>> result = orderService.getOrdersWithDateInterval(startDate, endDate, pageable);
        assertEquals(new GenericReturnValue<>(orderResponses).getValue(), result.getValue());
    }

    @Test
    public void cancelOrder_successfully() {

        CustomerResponse customerResponse = CustomerResponse.builder()
                .id(1L)
                .email("onur.sabit.salman@getir.com")
                .name("Onur Sabit")
                .surname("Salman")
                .password("test")
                .username("onursabitsalman")
                .build();

        BookResponse bookResponse = BookResponse.builder()
                .id(1L)
                .author("Onur Sabit Salman")
                .name("Effective Java")
                .page(500L)
                .price(19.99)
                .stock(100L)
                .build();

        OrderResponse orderResponse = OrderResponse.builder()
                .book(bookResponse)
                .customer(customerResponse)
                .quantity(10L)
                .status(OrderStatusType.PURCHASED)
                .build();


        Order order = Order.builder()
                .id(1L)
                .quantity(10L)
                .status(OrderStatusType.PURCHASED)
                .build();

        Book book = Book.builder()
                .id(1L)
                .author("Onur Sabit Salman")
                .name("Effective Java")
                .page(500L)
                .price(19.99)
                .stock(100L)
                .build();

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
        when(orderMapper.toOrderResponse(any())).thenReturn(orderResponse);
        when(userDetailsDaoService.getUserId()).thenReturn(1L);
        when(orderRepository.findByIdAndCustomerId(anyLong(), anyLong())).thenReturn(Optional.of(order));
        when(bookRepository.increaseBookStock(anyLong(), anyLong())).thenReturn(1);
        when(orderRepository.updateOrderStatus(anyLong(), eq(OrderStatusType.CANCELLED))).thenReturn(1);

        GenericReturnValue<String> result = orderService.cancelOrder(1L);
        assertEquals(new GenericReturnValue<>(ResponseMessages.CANCELLED_ORDER).getValue(), result.getValue());
    }

    @Test
    public void cancelOrder_withCouldNotFindBook_expectedResourceNotFoundException() {

        CustomerResponse customerResponse = CustomerResponse.builder()
                .id(1L)
                .email("onur.sabit.salman@getir.com")
                .name("Onur Sabit")
                .surname("Salman")
                .password("test")
                .username("onursabitsalman")
                .build();

        BookResponse bookResponse = BookResponse.builder()
                .id(1L)
                .author("Onur Sabit Salman")
                .name("Effective Java")
                .page(500L)
                .price(19.99)
                .stock(100L)
                .build();

        OrderResponse orderResponse = OrderResponse.builder()
                .book(bookResponse)
                .customer(customerResponse)
                .quantity(10L)
                .status(OrderStatusType.PURCHASED)
                .build();

        Order order = Order.builder()
                .id(1L)
                .quantity(10L)
                .status(OrderStatusType.PURCHASED)
                .build();


        when(orderMapper.toOrderResponse(any())).thenReturn(orderResponse);
        when(userDetailsDaoService.getUserId()).thenReturn(1L);
        when(orderRepository.findByIdAndCustomerId(anyLong(), anyLong())).thenReturn(Optional.of(order));

        assertThrows(ResourceNotFoundException.class, () -> orderService.cancelOrder(1L));

    }

    @Test
    public void cancelOrder_withCouldNotFindBook_expectedResourceNotFoundException2() {

        when(userDetailsDaoService.getUserId()).thenReturn(1L);
        assertThrows(ResourceNotFoundException.class, () -> orderService.cancelOrder(1L));

    }

}