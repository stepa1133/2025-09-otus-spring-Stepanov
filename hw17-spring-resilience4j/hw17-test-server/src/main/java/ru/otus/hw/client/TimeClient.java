package ru.otus.hw.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "timeApi", url = "${time.api.url}")
public interface TimeClient {

    @GetMapping("/now")
    TimeResponse getTime(@RequestParam("tz") String timezone, @RequestParam("format") String format);
}