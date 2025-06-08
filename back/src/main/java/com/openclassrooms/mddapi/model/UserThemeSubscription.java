package com.openclassrooms.mddapi.model;

import javax.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "USER_THEME_SUBSCRIPTIONS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserThemeSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "theme_id")
    private Theme theme;

    @Column(name = "subscribed_at")
    private LocalDateTime subscribedAt;
}
