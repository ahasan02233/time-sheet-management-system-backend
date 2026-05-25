package com.ahasan.time_sheet_mngmnt_sys.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(nullable = false)
    private String role;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
