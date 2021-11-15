package com.example.flyway_poc.entity.e;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_mapping",schema = "Target")
public class UserMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int serial_no;

    int old_id;

    int new_id;
}
