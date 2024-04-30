package lk.ijse.Model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Data

public class Pet {
    private String petId;
    private int age;
    private String weight;
    private String breed;
    private String petOwId;

}
