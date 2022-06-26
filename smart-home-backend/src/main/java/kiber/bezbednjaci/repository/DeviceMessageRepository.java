package kiber.bezbednjaci.repository;

import kiber.bezbednjaci.model.DeviceMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;

@Repository
@RequestScope
public interface DeviceMessageRepository extends MongoRepository<DeviceMessage, String> {
}
