package za.co.discovery.assignment.bo;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

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
