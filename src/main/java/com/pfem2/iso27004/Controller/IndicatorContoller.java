package com.pfem2.iso27004.Controller;

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

import com.pfem2.iso27004.Entity.Indicator;
import com.pfem2.iso27004.Service.IndicatorService;

@RestController
@RequestMapping("api/v1/indicator")
@CrossOrigin(origins = "*")

public class IndicatorContoller {

    private final IndicatorService indicatorService;

    @Autowired
    public IndicatorContoller(IndicatorService indicatorService) {
        this.indicatorService = indicatorService;
    }

    @GetMapping(path = "/not-evaluated")
    public List<Indicator> getRIndicators() {
        return indicatorService.getRIndicators();
    }

    @GetMapping
    public List<Indicator> getIndicators() {
        return indicatorService.getIndicators();
    }

    @PutMapping
    public Indicator EditIndicator(@RequestBody Indicator indicator) {
        return indicatorService.editIndicator(indicator);
    }

    @GetMapping(path = "{indicatorID}")
    public Indicator getIndicatorByID(@PathVariable("indicatorID") Long id) {
        return indicatorService.getIndicatorByID(id);
    }

    @PostMapping
    public Indicator addIndicators(@RequestBody Indicator indicator) {
        return indicatorService.addIndicator(indicator);
    }

    @DeleteMapping(path = "{indicatorID}")
    public void deleteIndicators(@PathVariable("indicatorID") Long id) {
        indicatorService.deleteIndicator(id);
    }

}
