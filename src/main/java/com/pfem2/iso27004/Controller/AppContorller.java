package com.pfem2.iso27004.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pfem2.iso27004.Entity.App;
import com.pfem2.iso27004.Service.AppService;

@RestController
@RequestMapping("api/v1/app")
@CrossOrigin(origins = "*")
public class AppContorller {

    private final AppService appService;

    @Autowired
    public AppContorller(AppService appService) {
        this.appService = appService;
    }

    @GetMapping
    public List<App> getApps() {
        return appService.getAPPs();
    }

    @PostMapping
    public App createApps(@RequestBody App app) {
        return appService.addApp(app);
    }

    @DeleteMapping(path = "{AppID}")
    public void getApps(@PathVariable("AppID") Long id) {
        appService.deleteApp(id);
        ;
    }

}
