package backend.service;

import backend.model.Country;

import java.util.Optional;

public interface ICountryService {
    Iterable<Country> findAll();
    Optional<Country> findById(Long id);
    void save(Country country);

}
