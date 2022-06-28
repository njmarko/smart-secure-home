package kiber.bezbednjaci.repository;

import kiber.bezbednjaci.model.DeviceAlarmModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceAlarmModelRepository extends MongoRepository<DeviceAlarmModel, String> {
}
