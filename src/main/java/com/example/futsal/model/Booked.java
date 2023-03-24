package com.example.futsal.model;

import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "booked")
@Where(clause = "deleted_date is null")
public class Booked extends AbstractDate implements Serializable {

    @Id
    @Column(name = "id_booked")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_booked;

    @ManyToOne
    @JoinColumn(name = "lapangan")
    private Lapangan lapangan;

    @ManyToOne
    @JoinColumn(name = "booked_by")
    private User user;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "time_booked")
    private Date time_booked;
}
