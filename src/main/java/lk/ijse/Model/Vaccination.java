package lk.ijse.Model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Data
public class Vaccination {
    private String vaccId;
    private String description;
    private String dosage;


}
