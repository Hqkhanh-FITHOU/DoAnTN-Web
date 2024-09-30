package com.graduate.hou.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.security.Timestamp;

@Entity
@Table(name = "response")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reponseId;

    @OneToOne
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @Lob
    private String reponseContent;

    @CreationTimestamp
    private Timestamp createdAt;

}
