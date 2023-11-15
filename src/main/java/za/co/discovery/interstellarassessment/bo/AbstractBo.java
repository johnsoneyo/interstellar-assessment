package za.co.discovery.interstellarassessment.bo;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractBo {

    @GeneratedValue
    @Id
    Long id;


}
