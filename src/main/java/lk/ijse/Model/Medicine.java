package lk.ijse.Model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class Medicine {
    private String medId;
    private String description;
    private String qty;
    private Double price;


}
