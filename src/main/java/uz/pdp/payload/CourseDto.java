package uz.pdp.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class CourseDto {

    @NotNull(message = "Name must not be empty")
    private String name;

}
