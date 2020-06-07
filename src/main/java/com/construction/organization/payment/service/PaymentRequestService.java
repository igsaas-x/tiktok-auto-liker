package com.construction.organization.payment.service;

import com.construction.organization.payment.domain.PaymentCommand;
import com.construction.organization.payment.domain.PaymentRequest;
import com.construction.organization.payment.domain.RequestStatus;
import com.construction.organization.payment.repository.PaymentRequestRepository;
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
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PaymentRequestService {

    final EntityDataMapper dataMapper;
    final PaymentRequestRepository repository;

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

    public List<PaymentRequest> findPendingFor(final PaymentCommand pendingFor) {
        switch (pendingFor) {
            case SUBMIT:
                return repository.findPending(false, false, false, false, false);
            case VERIFY:
                return repository.findPending(true, false, false, false, false);
            case CONFIRM:
                return repository.findPending(true, true, false, false, false);
            case REVIEW:
                return repository.findPending(true, true, true, false, false);
            case APPROVE:
                return repository.findPending(true, true, true, true, false);
            case CASH_OUT:
                return repository.findPending(true, true, true, true, true);
        }
        return List.of();
    }

    public PaymentRequest handleCommand(final PaymentRequest paymentRequest, final PaymentCommand command, final AppUser user) {
        var done = new RequestStatus().setDone(true).setDoneAt(LocalDateTime.now()).setDoneBy(user);
        switch (command) {
            case SUBMIT:
                paymentRequest.setSubmitted(done);
                break;
            case VERIFY:
                paymentRequest.setVerified(done);
                break;
            case CONFIRM:
                paymentRequest.setConfirmed(done);
                break;
            case REVIEW:
                paymentRequest.setReviewed(done);
                break;
            case APPROVE:
                paymentRequest.setApproved(done);
                break;
            case CASH_OUT:
                paymentRequest.setPaid(done);
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "command not found");
        }
        return repository.save(paymentRequest);
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