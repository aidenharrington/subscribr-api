package com.project.subscribr.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    // Todo: remove

//    @ManyToMany
//    @JoinTable(
//            name = "subscriptions",
//            joinColumns = @JoinColumn(name = "subscriber_id"),
//            inverseJoinColumns = @JoinColumn(name = "subscribed_to_id")
//    )
//    private Set<User> subscriptions;

}
