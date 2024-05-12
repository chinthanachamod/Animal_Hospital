package lk.ijse.Model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Data
public class petMediDetail extends CartTm {
    private String Medicine_ID;
    private String P_ID;
    private int QTY;
}
