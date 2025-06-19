package com.openclassrooms.mddapi.model;

import javax.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "THEMES")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String title;

    private String description;

    @OneToMany(mappedBy = "theme")
    private Set<UserThemeSubscription> subscriptions;
}
