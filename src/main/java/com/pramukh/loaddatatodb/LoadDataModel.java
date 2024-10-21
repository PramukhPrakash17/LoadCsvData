package com.pramukh.loaddatatodb;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
public class LoadDataModel {

    @Id
    private int id;
    private String name;
    private String fullname;
    private LocalDate dob;
    private String positions;
    private int rating;
    private int potential;
    private long value_euros;
    private String preffered_foot;
    private String nationality;
    private int natianal_jersey_number;

}
