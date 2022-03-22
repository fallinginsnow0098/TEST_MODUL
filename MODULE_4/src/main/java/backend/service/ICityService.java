package backend.service;

import backend.model.City;

import java.util.Optional;

public interface ICityService {
    Iterable<City> findAll();
    Optional<City> findById(Long id);
    void save(City city);
    void delete(Long id);
}
