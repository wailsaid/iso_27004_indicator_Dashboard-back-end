package com.pfem2.iso27004.Indicator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/indicator")
@CrossOrigin(origins = "http://localhost:4200")

public class IndicatorContoller {

    private final IndicatorService indicatorService;

    @Autowired
    public IndicatorContoller(IndicatorService indicatorService) {
        this.indicatorService = indicatorService;
    }

    @GetMapping
    public List<Indicator> getIndicators() {
        return indicatorService.getIndicators();
    }

    @GetMapping(path = "{indicatorID}")
    public Indicator getIndicatorByID(@PathVariable("indicatorID") Long id) {
        return indicatorService.getIndicatorByID(id);
    }

    @PostMapping
    public void addIndicators(@RequestBody Indicator indicator) {
        indicatorService.addIndicator(indicator);
    }

    @PutMapping
    public List<Indicator> editIndicators() {
        return null;
    }

    @DeleteMapping(path = "{indicatorID}")
    public void deleteIndicators(@PathVariable("indicatorID") Long id) {
        indicatorService.deleteIndicator(id);
    }

}
