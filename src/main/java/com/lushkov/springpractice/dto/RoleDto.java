package com.lushkov.springpractice.dto;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RoleDto {

    @NotEmpty(message = "Name role should not be empty")
    @Size(min = 2, max = 30, message = "Name role should" +
            " be between 2 and 30 characters")
    private String name;

    public RoleDto() {
    }

    public RoleDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoleDto roleDto = (RoleDto) o;

        return name.equals(roleDto.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "RoleDto{" +
                "name='" + name + '\'' +
                '}';
    }
}