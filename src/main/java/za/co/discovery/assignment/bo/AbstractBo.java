package za.co.discovery.assignment.bo;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

/**
 * This class is only necessary IFF there are multiple entities
 * It is used here for design purposes only
 */
@MappedSuperclass
public abstract class AbstractBo {

    @GeneratedValue
    @Id
    private Long id;
}
