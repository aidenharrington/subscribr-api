package com.project.subscribr.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subscriber_id")
    private Long subscriberId;

    @Column(name = "subscribed_to_id")
    private Long subscribedToId;
}
