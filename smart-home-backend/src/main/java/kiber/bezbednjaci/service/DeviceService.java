package kiber.bezbednjaci.service;

import kiber.bezbednjaci.dto.report.DeviceDTO;
import kiber.bezbednjaci.dto.report.DeviceReport;
import kiber.bezbednjaci.dto.report.SearchDeviceReport;
import kiber.bezbednjaci.model.DeviceAlarmModel;
import kiber.bezbednjaci.repository.DeviceAlarmModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceAlarmModelRepository deviceAlarmModelRepository;
    private final RestTemplate restTemplate;
    public List<DeviceReport> read(SearchDeviceReport deviceReport, Pageable pageable){
        DeviceDTO[] devices = restTemplate.getForObject("https://localhost:8082/api/devices", DeviceDTO[].class);
        assert devices != null;
        return Arrays.stream(devices).map(new Function<DeviceDTO, DeviceReport>() {
            @Override
            public DeviceReport apply(DeviceDTO deviceDTO) {
                DeviceReport dr = new DeviceReport();
                dr.setDeviceId(deviceDTO.getName());
                dr.setEstateId(deviceDTO.getRealEstateName());
                List<DeviceAlarmModel> alarms = deviceAlarmModelRepository.search(deviceDTO.getEstateId(), deviceDTO.getDeviceId(), deviceReport.fromTimestamp(), deviceReport.toTimestamp());
                dr.setAlarmNumber(alarms.size());
                return dr;
            }
        }).collect(Collectors.toList());
    }
}
