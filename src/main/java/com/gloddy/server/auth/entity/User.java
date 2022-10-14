package com.gloddy.server.auth.entity;

import com.gloddy.server.auth.entity.kind.Authority;
import com.gloddy.server.auth.entity.kind.Gender;
import com.gloddy.server.auth.entity.kind.Personality;
import com.gloddy.server.core.converter.EnumArrayConverter;
import com.gloddy.server.core.entity.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "user")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(name = "password", columnDefinition = "longtext")
    private String password;

    private String name;

    @Column(name = "school")
    private String school;

    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Convert(converter = EnumArrayConverter.class)
    @Column(name = "personality")
    private List<Personality> personalities = new ArrayList<>();

    private int score;

    @Builder
    public User(String email, String password, String name, String school, LocalDate birth, Gender gender, List<Personality> personalities) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.school = school;
        this.birth = birth;
        this.gender = gender;
        this.personalities = personalities;
        authorityDefault();
    }

    private void authorityDefault() {
        this.authority = Authority.USER;
    }

    // TODO: ENUM으로 score 값 관리
    private void updateScore() {

    }


}
