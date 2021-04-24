package uz.pdp.springlesson4restsecurity.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutcomeDto {
    private Integer fromCardId;
    private Integer toCardId;
    private double amount;
    private double commissionPercent;
}
