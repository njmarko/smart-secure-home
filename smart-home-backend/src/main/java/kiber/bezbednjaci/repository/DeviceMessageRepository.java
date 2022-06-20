package kiber.bezbednjaci.repository;

import kiber.bezbednjaci.model.DeviceMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceMessageRepository extends JpaRepository<DeviceMessage, Integer> {
}
