package kiber.bezbednjaci.controller;

import kiber.bezbednjaci.dto.message.SearchMessagesRequest;
import kiber.bezbednjaci.dto.report.DeviceReport;
import kiber.bezbednjaci.dto.report.SearchDeviceReport;
import kiber.bezbednjaci.model.DeviceMessage;
import kiber.bezbednjaci.service.DeviceMessageService;
import kiber.bezbednjaci.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/device-report")
public class DeviceReportController {
    private final DeviceService deviceService;

    @Autowired
    public DeviceReportController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public List<DeviceReport> read(@Valid SearchDeviceReport report, Pageable pageable) {
        return deviceService.read(report, pageable);
    }
}
