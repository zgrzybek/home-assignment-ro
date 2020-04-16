package pl.org.grzybek.har.common.audit;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Base class representing audited object,
 * it can be used to implement a generic behavior like Soft Deletion
 */
@Getter
@Setter
public abstract class Audited implements SoftDeletable {

    @Id
    private UUID id;

    @CreatedDate
    private LocalDateTime creationTime;

    private boolean deleted;

    /**
     * Needed by the {@link CreatedDate} when id is assigned manually, see DATAMONGO-946
     */
    @Version
    private long version;
}
