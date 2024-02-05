package com.hocvui.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100)
    private String displayName;

    @Column(length = 100,nullable = false,unique = true)
    private String email;

    @Column(length = 100,nullable = false)
    private String password;

    private Boolean enabled;

    public User(String gmail,String password) {
        this.email = gmail;
        this.password = password;
    }

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created_at;

    @UpdateTimestamp
    @Column
    private LocalDateTime updated_at;

    public User(String name, String email, String password) {
        this.email = email;
        this.displayName = name;
        this.password = password;
    }

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<UserRole> roles = new ArrayList<>();
}
