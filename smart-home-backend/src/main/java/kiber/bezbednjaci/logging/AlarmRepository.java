package kiber.bezbednjaci.logging;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends MongoRepository<AlarmModel, String> {

    @Query("{'acknowledged': false}")
    Page<AlarmModel> readNonAcknowledged(Pageable pageable);
}
