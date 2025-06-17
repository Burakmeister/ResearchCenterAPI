package com.denert.app.rest.aspects;

import com.denert.app.rest.adnotations.ValidateAddress;
import com.denert.app.rest.dto.AddressRequest;
import com.denert.app.rest.models.Address;
import com.denert.app.rest.repo.AddressRepo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ValidationAspect {

    @Around("@annotation(validateAddress)")
    public Object validateAddress(ProceedingJoinPoint pjp, ValidateAddress validateAddress) throws Throwable {
        Object[] args = pjp.getArgs();

        for (Object arg : args) {
            if (arg instanceof AddressRequest addressRequest) {
                validateAddressFields(addressRequest);
            }
        }

        return pjp.proceed();
    }

    private void validateAddressFields(AddressRequest addressRequest) {
        StringBuilder errorBuilder = new StringBuilder();

        if (addressRequest.getCity() == null || addressRequest.getCity().isBlank()) {
            errorBuilder.append("City is missing or blank. ");
        }
        if (addressRequest.getStreetName() == null || addressRequest.getStreetName().isBlank()) {
            errorBuilder.append("Street name is missing or blank. ");
        }
        if (addressRequest.getHouseNumber() == null || addressRequest.getHouseNumber().isBlank()) {
            errorBuilder.append("House number is missing or blank. ");
        }
        if (addressRequest.getPostcode() <= 0) {
            errorBuilder.append("Postcode must be greater than 0. ");
        }
        if (addressRequest.getFlatNumber() < 0) {
            errorBuilder.append("Flat number cannot be negative. ");
        }

        if (!errorBuilder.isEmpty()) {
            throw new IllegalArgumentException("Invalid Address: " + errorBuilder.toString().trim());
        }
    }
}
