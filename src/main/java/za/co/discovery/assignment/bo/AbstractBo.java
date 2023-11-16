package za.co.discovery.assignment.bo;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.SequenceGenerator;

/**
 * This class is only necessary IFF there are multiple entities
 * It is used here for design purposes only
 */
@MappedSuperclass
public abstract class AbstractBo {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROUTE_SEQ")
    @SequenceGenerator(name = "ROUTE_SEQ", sequenceName = "ROUTE_SEQ", allocationSize = 1)
    @Id
    private Long id;
}
