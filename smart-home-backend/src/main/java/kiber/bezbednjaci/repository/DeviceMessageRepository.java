package kiber.bezbednjaci.repository;

import kiber.bezbednjaci.model.DeviceMessage;
import kiber.bezbednjaci.model.MessageType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;

import java.time.LocalDateTime;

@Repository
public interface DeviceMessageRepository extends MongoRepository<DeviceMessage, String> {
    @Query("{$and: [{'realEstateId': ?0}, {'messageType': ?1}, {'content': {$regex: ?2}}, {'timestamp': {$gt: ?3, $lt: ?4}}]}")
    Page<DeviceMessage> search(Integer realEstateId, MessageType messageType, String regex, LocalDateTime localDateTime, LocalDateTime dateTime, Pageable pageable);
}
