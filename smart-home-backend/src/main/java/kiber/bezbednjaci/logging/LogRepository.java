package kiber.bezbednjaci.logging;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface LogRepository extends MongoRepository<LogModel, String> {

    @Query(value = "{$and: [{'logType': ?0}, {'content': {$regex: ?1}}, {'timestamp': {$gt: ?2, $lt: ?3}}]}")
    Page<LogModel> search(LogType type, String regex, LocalDateTime from, LocalDateTime to, Pageable pageable);
}
