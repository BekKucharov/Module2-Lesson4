package uz.pdp.springlesson4restsecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Outcome {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double amount;
    private Date date;
    private double commissionPercent;

    @ManyToOne
    private Card fromCardId;
    @ManyToOne
    private Card toCardId;
}
