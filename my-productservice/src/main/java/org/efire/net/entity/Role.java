package org.efire.net.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Table(name = "ROLE", schema = "mydb")
@Entity
public class Role implements GrantedAuthority {
    @Column(name = "ID", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", length = 20)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}