package com.example.flyway_poc.entity.d;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "target_foreign",schema = "Target")
public class TargetUOD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int user_id_uod;

    private String key_name;

    private String value;

    private String created_date;

}
