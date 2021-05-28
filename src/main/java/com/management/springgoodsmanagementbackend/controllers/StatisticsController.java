package com.management.springgoodsmanagementbackend.controllers;

import com.management.springgoodsmanagementbackend.dtos.StatisticsDTO;
import com.management.springgoodsmanagementbackend.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
@RequestMapping(path = "/statistics")
public class StatisticsController {

    @Autowired
    private StatisticService statisticService;

    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public StatisticsDTO getStatistics() {
        return statisticService.getStatistics();
    }
}
