package com.team.subscriptions.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String name;
    @Email @NotBlank @Column(unique = true) private String email;
    private String company;
    private String phone;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Customer() {}
    public Customer(String name, String email, String company, String phone) {
        this.name=name; this.email=email; this.company=company; this.phone=phone;
    }
    public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getCompany(){return company;} public void setCompany(String v){company=v;}
    public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
    public LocalDateTime getCreatedAt(){return createdAt;}
}
