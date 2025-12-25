package com.alaToo.scrs.dto;

import com.alaToo.scrs.entity.ComplaintType;
import com.alaToo.scrs.entity.DepartmentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplaintRequest {

    @NotNull
    @Size(max = 200)
    private String title;

    @NotNull
    @Size(max = 2000)
    private String description;

    @NotNull
    private ComplaintType type;

    @NotNull
    private DepartmentType departmentType;

    @NotNull
    @Size(max = 100)
    private String targetDepartment;

}
