package lk.ijse.Model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Data
public class Veterinarian {
    private String vetId;
    private String name;
    private int yrsOfExperience;


}