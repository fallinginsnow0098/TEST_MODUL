package backend.controller;

import backend.model.City;
import backend.service.ICityService;
import backend.service.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/home")
public class CityController {
    @Autowired
    private ICountryService countryService;
    @Autowired
    private ICityService cityService;
    @GetMapping
    public ModelAndView homePage(){
        ModelAndView modelAndView = new ModelAndView("home");
        Iterable<City> cities = cityService.findAll();
        modelAndView.addObject("countries", countryService.findAll());
        modelAndView.addObject("cities", cities);
        return modelAndView;
    }
    @GetMapping("/create-new")
    public ModelAndView showFormCreate(){
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("city", new City());
        modelAndView.addObject("countries", countryService.findAll());
        return modelAndView;
    }
    @PostMapping("/create")
    public ModelAndView save(@ModelAttribute City city){
        ModelAndView modelAndView = new ModelAndView("redirect:/home");
        cityService.save(city);
        return modelAndView;
    }
    @GetMapping("/edit/{id}")
    public ModelAndView showFormEdit(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("create");
        Optional<City> city = cityService.findById(id);
        if (city.isPresent()){
            modelAndView.addObject("city", city);
            modelAndView.addObject("countries", countryService.findAll());
            return modelAndView;
        }
        return modelAndView;
    }
    @PostMapping("/edit/{id}")
    public ModelAndView update(@PathVariable Long id, @ModelAttribute City city){
        ModelAndView modelAndView = new ModelAndView("redirect:/home");
        city.setId(id);
        cityService.save(city);
        modelAndView.addObject("message", "Edit success");
        return modelAndView;
    }
    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("redirect:/home");
        cityService.delete(id);
        modelAndView.addObject("message", "Delete success");
        return modelAndView;
    }
    @GetMapping("/{id}")
    public ModelAndView detail(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("detail");
        Optional<City> city = cityService.findById(id);
        if (city.isPresent()){
            modelAndView.addObject("city", city.get());
            return modelAndView;
        } else {
            modelAndView.addObject("message", "City not found");
            return modelAndView;
        }
    }
}
