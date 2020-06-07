package com.construction.organization.payment.service;

import com.construction.organization.payment.domain.CommandType;
import com.construction.organization.payment.domain.PaymentRequest;
import com.construction.organization.payment.domain.PaymentRequestStatus;
import com.construction.organization.payment.domain.StatusHistory;
import com.construction.organization.payment.repository.PaymentRequestRepository;
import com.construction.organization.payment.repository.StatusHistoryRepository;
import com.construction.persistence.exception.ResourceNotFoundException;
import com.construction.persistence.service.EntityDataMapper;
import com.construction.user.authentication.domain.AppUser;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PaymentRequestService {

    final EntityDataMapper dataMapper;
    final PaymentRequestRepository repository;
    final StatusHistoryRepository historyRepository;

    public PaymentRequest save(PaymentRequest dto) {
        return repository.save(dto);
    }

    public void save(List<PaymentRequest> dtos) {
        repository.saveAll(dtos);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public PaymentRequest getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(PaymentRequest.class, id));
    }

    public List<PaymentRequest> findAll() {
        return repository.findAll();
    }

    public List<PaymentRequest> findPendingFor(final CommandType pendingFor) {
        switch (pendingFor) {
            case SUBMIT:
                return repository.findAllByStatus(PaymentRequestStatus.OPEN);
            case VERIFY:
                return repository.findAllByStatus(PaymentRequestStatus.SUBMITTED);
            case CONFIRM:
                return repository.findAllByStatus(PaymentRequestStatus.VERIFIED);
            case REVIEW:
                return repository.findAllByStatus(PaymentRequestStatus.CONFIRMED);
            case APPROVE:
                return repository.findAllByStatus(PaymentRequestStatus.REVIEWED);
            case CASH_OUT:
                return repository.findAllByStatus(PaymentRequestStatus.APPROVED);
        }
        return List.of();
    }

    public List<PaymentRequest> getAllPending(){
        return repository.findAllPending();
    }

    public PaymentRequest handleCommand(final PaymentRequest request, final CommandType command, final AppUser user, final String comment) {
        addHistory(request, command, user, comment);
        switch (command) {
            case SUBMIT:
                if (!request.getStatus().equals(PaymentRequestStatus.OPEN)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in open status");
                }
                request.setStatus(PaymentRequestStatus.SUBMITTED);
                break;
            case VERIFY:
                if (!request.getStatus().equals(PaymentRequestStatus.SUBMITTED)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in submitted status");
                }
                request.setStatus(PaymentRequestStatus.VERIFIED);
                break;
            case CONFIRM:
                if (!request.getStatus().equals(PaymentRequestStatus.VERIFIED)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in verified status");
                }
                request.setStatus(PaymentRequestStatus.CONFIRMED);
                break;
            case REVIEW:
                if (!request.getStatus().equals(PaymentRequestStatus.CONFIRMED)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in confirmed status");
                }
                request.setStatus(PaymentRequestStatus.REVIEWED);
                break;
            case APPROVE:
                if (!request.getStatus().equals(PaymentRequestStatus.REVIEWED)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in reviewed status");
                }
                request.setStatus(PaymentRequestStatus.APPROVED);
                break;
            case CASH_OUT:
                if (!request.getStatus().equals(PaymentRequestStatus.APPROVED)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "payment request is not in approved status");
                }
                request.setStatus(PaymentRequestStatus.PAID);
                break;
            case REJECT:
                request.setStatus(PaymentRequestStatus.OPEN);
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "command not found");
        }
        return repository.save(request);
    }

    private void addHistory(PaymentRequest request, CommandType commandType, AppUser doneBy, String comment) {
        var history = new StatusHistory()
                .setPaymentRequest(request)
                .setCommandType(commandType)
                .setComment(comment);
        historyRepository.save(history);
    }

    public Page<PaymentRequest> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public PaymentRequest updateById(Long id, PaymentRequest source) {
        var target = getById(id);
        target = dataMapper.mapObject(source, target, PaymentRequest.class);
        return repository.save(target);
    }
}