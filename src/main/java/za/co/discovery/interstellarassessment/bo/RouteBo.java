package za.co.discovery.interstellarassessment.bo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "route")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RouteBo extends AbstractBo{

    @Enumerated(value = EnumType.STRING)
    @Column
    Planet origin;
    @Enumerated(value = EnumType.STRING)
    @Column
    Planet destination;
    @Column
    Double distance;
}
