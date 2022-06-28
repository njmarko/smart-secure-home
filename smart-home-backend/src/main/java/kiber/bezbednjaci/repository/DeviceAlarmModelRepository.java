package kiber.bezbednjaci.repository;

import kiber.bezbednjaci.model.DeviceAlarmModel;
import kiber.bezbednjaci.model.DeviceMessage;
import kiber.bezbednjaci.model.MessageType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DeviceAlarmModelRepository extends MongoRepository<DeviceAlarmModel, String> {
    @Query("{$and: [{'realEstateId': ?0}, {'deviceId': ?1}, {'timestamp': {$gt: ?2, $lt: ?3}}]}")
    List<DeviceAlarmModel> search(Integer realEstateId, Integer deviceId, LocalDateTime localDateTime, LocalDateTime dateTime);
}
