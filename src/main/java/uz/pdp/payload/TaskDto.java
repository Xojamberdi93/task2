package uz.pdp.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TaskDto {

    @NotNull
    private String name;

    private String description;

    private Integer sectionId;
}
