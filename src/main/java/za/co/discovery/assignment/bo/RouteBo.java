package za.co.discovery.assignment.bo;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "route")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RouteBo extends AbstractBo {

    @Enumerated(value = EnumType.STRING)
    @Column
    private Planet origin;
    @Enumerated(value = EnumType.STRING)
    @Column
    private Planet destination;
    @Column
    private Double distance;
}
