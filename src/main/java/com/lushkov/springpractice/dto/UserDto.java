package com.lushkov.springpractice.dto;

import com.lushkov.springpractice.models.Role;
import com.lushkov.springpractice.models.User;
import com.lushkov.springpractice.services.RoleService;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.sql.Date;

public class UserDto {

    @Autowired
    private RoleService roleService;

    @NotNull
    private long roleId;

    @NotEmpty(message = "First name should not be empty")
    @Size(min = 2, max = 30, message = "First name " +
            "should be between 2 and 30 characters")
    private String firstName;

    @NotEmpty(message = "Last name should not be empty")
    @Size(min = 2, max = 30, message = "Last name " +
            "should be between 2 and 30 characters")
    private String lastName;

    @NotEmpty(message = "Login should not be empty")
    @Size(min = 2, max = 30, message = "Login " +
            "should be between 2 and 30 characters")
    private String login;

    @Past(message = "Dob should be in the past")
    @NotNull(message = "Dob should not be null")
    private Date dob;

    @NotEmpty(message = "Password should not be empty")
    @Size(min = 4, max = 30, message = "Password " +
            "should be between 2 and 30 characters")
    private String password;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email should not be empty")
    private String email;

    public User toUser(Role role) {
        return new User(role ,firstName, lastName, login, dob, password, email);
    }

    public UserDto() {
    }

    public UserDto(long roleId, String firstName, String lastName, String login, Date dob, String password, String email) {
        this.roleId = roleId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.dob = dob;
        this.password = password;
        this.email = email;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        if (roleId != userDto.roleId) return false;
        if (!firstName.equals(userDto.firstName)) return false;
        if (!lastName.equals(userDto.lastName)) return false;
        if (!login.equals(userDto.login)) return false;
        if (!password.equals(userDto.password)) return false;
        return email.equals(userDto.email);
    }

    @Override
    public int hashCode() {
        int result = (int) (roleId ^ (roleId >>> 32));
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "roleId=" + roleId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                '}';
    }
}