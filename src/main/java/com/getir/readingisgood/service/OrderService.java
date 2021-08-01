package com.getir.readingisgood.service;

import com.getir.readingisgood.entity.Book;
import com.getir.readingisgood.entity.Order;
import com.getir.readingisgood.entity.enums.OrderStatusType;
import com.getir.readingisgood.exceptions.CustomException;
import com.getir.readingisgood.exceptions.ResourceNotFoundException;
import com.getir.readingisgood.model.modelmapper.OrderMapper;
import com.getir.readingisgood.model.request.OrderRequest;
import com.getir.readingisgood.model.response.GenericReturnValue;
import com.getir.readingisgood.model.response.OrderResponse;
import com.getir.readingisgood.repository.BookRepository;
import com.getir.readingisgood.repository.OrderRepository;
import com.getir.readingisgood.repository.UserRepository;
import com.getir.readingisgood.security.service.UserDetailsDaoService;
import com.getir.readingisgood.utils.ErrorMessages;
import com.getir.readingisgood.utils.ResponseMessages;
import com.getir.readingisgood.utils.ValidateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final UserDetailsDaoService userDetailsDaoService;
    private final OrderMapper orderMapper;
    private final ValidateUtil validateUtil;

    @Transactional
    public GenericReturnValue<Long> createOrder(OrderRequest orderRequest) {

        validateUtil.validate(orderRequest);

        Long currentCustomerId = userDetailsDaoService.getUserId();

        Order order = orderMapper.toEntity(orderRequest);
        order.setCustomer(userRepository.findById(currentCustomerId).get());

        bookRepository.findById(orderRequest.getBookId())
                .ifPresentOrElse(
                        (book) -> {
                            if (!(book.getStock() >= orderRequest.getQuantity())) {
                                throw new CustomException(ErrorMessages.BOOK_STOCK_ERROR);
                            }
                            bookRepository.decreaseBookStock(book.getId(), orderRequest.getQuantity());
                            order.setBook(bookRepository.findById(book.getId()).get());
                            log.info("Updated book id: {}", book.getId());
                        },
                        () -> {
                            throw new ResourceNotFoundException(ErrorMessages.BOOK_NOT_FOUND);
                        }
                );
        log.info("Order created by: {} and purchased book id: {}", currentCustomerId, orderRequest.getBookId());
        return new GenericReturnValue<>(orderRepository.save(order).getId());
    }

    @Transactional(readOnly = true)
    public GenericReturnValue<Page<OrderResponse>> getOrders(Pageable paging) {
        return new GenericReturnValue<>(orderRepository.findAll(paging)
                .map(orderMapper::toOrderResponse));
    }

    @Transactional(readOnly = true)
    public GenericReturnValue<Page<OrderResponse>> getOrdersWithDateInterval(Date startDate, Date endDate, Pageable paging) {
        LocalDateTime startLocalDate = LocalDateTime.ofInstant(startDate.toInstant(), ZoneId.systemDefault());
        LocalDateTime endLocalDate = LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault());
        return new GenericReturnValue<>(orderRepository.findAllByCreatedDateIsBetween(startLocalDate, endLocalDate, paging)
                .map(orderMapper::toOrderResponse));
    }

    @Transactional
    public GenericReturnValue<String> cancelOrder(Long orderId) {
        Long currentCustomerId = userDetailsDaoService.getUserId();
        Optional<Order> customerOrder = orderRepository.findByIdAndCustomerId(orderId, currentCustomerId);

        customerOrder.ifPresentOrElse(
                (order) -> {
                    if (order.getStatus().equals(OrderStatusType.CANCELLED)) {
                        throw new CustomException(ErrorMessages.ORDER_ALREADY_CANCELLED);
                    }
                    OrderResponse orderResponse = orderMapper.toOrderResponse(order);
                    Optional<Book> orderBook = bookRepository.findById(orderResponse.getBook().getId());
                    orderBook.ifPresentOrElse(
                            (book) -> {
                                bookRepository.increaseBookStock(book.getId(), orderResponse.getQuantity());
                                log.info("Updated book id: {}", book.getId());
                            },
                            () -> {
                                throw new ResourceNotFoundException(ErrorMessages.BOOK_NOT_FOUND);
                            }
                    );
                    orderRepository.updateOrderStatus(order.getId(), OrderStatusType.CANCELLED);
                    log.info("Updated order id: {}, new status: {}", order.getId(), OrderStatusType.CANCELLED);
                },
                () -> {
                    throw new ResourceNotFoundException(ErrorMessages.ORDER_NOT_FOUND);
                }
        );

        log.info("Order cancelled by: {} and order id: {}", currentCustomerId, orderId);
        return new GenericReturnValue<>(ResponseMessages.CANCELLED_ORDER);
    }
}
