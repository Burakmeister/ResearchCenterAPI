package com.denert.app.rest.service;

import com.denert.app.rest.adnotations.PublicsLogger;
import com.denert.app.rest.dto.AddressRequest;
import com.denert.app.rest.dto.AddressResponse;
import com.denert.app.rest.exception.AddressNotFoundException;
import com.denert.app.rest.models.Address;
import com.denert.app.rest.repo.AddressRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@PublicsLogger
@Service
@Slf4j
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepo addressRepo;

    public AddressResponse createAddress(AddressRequest addressRequest){
        final Address address = Address.builder()
                .city(addressRequest.getCity())
                .streetName(addressRequest.getStreetName())
                .houseNumber(addressRequest.getHouseNumber())
                .postcode(addressRequest.getPostcode())
                .flatNumber(addressRequest.getFlatNumber())
                .users(addressRequest.getUsers())
                .build();
        AddressResponse addressResponse = mapToAddressResponse(addressRepo.save(address));
        log.info("Address {} is saved", address.getAddressId());

        return addressResponse;
    }

    public List<AddressResponse> getAllAddresses(){
        List<Address> addresses = addressRepo.findAll();

        return addresses.stream().map(address -> mapToAddressResponse(address)).toList();
    }

    public AddressResponse getAddressById(long id){
        return addressRepo.findById(id)
                .map(this::mapToAddressResponse)
                .orElseThrow(() -> new AddressNotFoundException("Address with ID " + id + " not found."));
    }

    public AddressResponse updateAddress(AddressRequest addressRequest, long id) {
        return addressRepo.findById(id)
                .map(existingAddress -> {
                    existingAddress.setPostcode(addressRequest.getPostcode());
                    existingAddress.setCity(addressRequest.getCity());
                    existingAddress.setStreetName(addressRequest.getStreetName());
                    existingAddress.setFlatNumber(addressRequest.getFlatNumber());
                    existingAddress.setHouseNumber(addressRequest.getHouseNumber());

                    Address updatedAddress = addressRepo.save(existingAddress);
                    log.info("Address {} was updated", updatedAddress.getAddressId());
                    return mapToAddressResponse(updatedAddress);

                })  .orElseThrow(() -> new AddressNotFoundException("Address with ID " + id + " not found"));
    }

    public void deleteAddress(long id){
        Address addressToDelete = addressRepo.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address with ID " + id + " not found."));
        addressRepo.delete(addressToDelete);
        log.info("Address {} was deleted", id);
    }

    private AddressResponse mapToAddressResponse(Address address) {
        return AddressResponse.builder()
                .addressId(address.getAddressId())
                .city(address.getCity())
                .streetName(address.getStreetName())
                .houseNumber(address.getHouseNumber())
                .postcode(address.getPostcode())
                .flatNumber(address.getFlatNumber())
                .users(address.getUsers())
                .build();
    }
}
