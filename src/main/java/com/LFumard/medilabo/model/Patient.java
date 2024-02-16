package com.LFumard.medilabo.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "FirstName is mandatory")
    @Size(max = 50, message = "max size is limited to 50")
    @Column(name = "firstname")
    private String firstName;

    @NotBlank(message = "LastName is mandatory")
    @Size(max = 50, message = "max size is limited to 50")
    @Column(name = "lastname")
    private String lastName;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    @NotBlank(message = "Sex is mandatory (M or F)")
    @Column(name = "sex")
    @Pattern(regexp = "[MF]", message = "Gender must be 'M' or 'F'")
    private String sex;

    @Size(max = 255, message = "max size is limited to 255")
    private String address;

    @Size(max = 15, message = "max size is limited to 15")
    private String phonenumber;
}