package com.pfem2.iso27004.Indicator;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfem2.iso27004.OrgApp.App;

@Service
public class IndicatorService {

    private final IndicatorRepository indicatorRepository;

    @Autowired
    public IndicatorService(IndicatorRepository indicatorRepository) {
        this.indicatorRepository = indicatorRepository;
    }

    public List<Indicator> getIndicators() {
        return indicatorRepository.findAll();
    }

    public void addIndicator(Indicator indicator) {
        indicatorRepository.save(indicator);
    }

    public void deleteIndicator(Long id) {
        indicatorRepository.findById(id);
        if (!indicatorRepository.existsById(id)) {
            throw new IllegalStateException("indicator with id: " + id + " does not exists");
        }
        Indicator indicator = indicatorRepository.findById(id).orElseThrow();
        indicatorRepository.delete(indicator);
    }

    public void deleteIndicatorApp(App app) {
        List<Indicator> lIndicators = this.getIndicators();

        for (Indicator indicator : lIndicators) {
            indicator.getApps().remove(app);
        }
        indicatorRepository.saveAll(lIndicators);

    }

    public Indicator getIndicatorByID(Long id) {
        Optional<Indicator> indicator = indicatorRepository.findById(id);

        if (!indicator.isPresent()) {
            throw new IllegalStateException("indicator with id: " + id + " does not exists");
        }
        return indicator.get();
    }

}
