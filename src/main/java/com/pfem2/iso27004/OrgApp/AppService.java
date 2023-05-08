package com.pfem2.iso27004.OrgApp;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pfem2.iso27004.Indicator.IndicatorService;

@Service
public class AppService {

    private final AppRepository appRepository;
    private final IndicatorService indicatorService;

    @Autowired
    public AppService(AppRepository appRepository, IndicatorService indicatorService) {
        this.appRepository = appRepository;
        this.indicatorService = indicatorService;
    }

    public List<App> getAPPs() {
        return appRepository.findAll();
    }

    public App addApp(App app) {
        return appRepository.save(app);
    }

    public void deleteApp(Long id) {

        Optional<App> app = appRepository.findById(id);
        if (!app.isPresent()) {
            throw new IllegalStateException("App with id: " + id + "does not exists");
        }

        indicatorService.deleteIndicatorApp(app.get());
        appRepository.deleteById(id);
    }

}
