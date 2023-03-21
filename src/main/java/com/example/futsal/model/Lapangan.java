package com.example.futsal.model;

import com.example.futsal.model.AbstractDate;
import com.example.futsal.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "lapangan")
@Where(clause = "deleted_date is null")
public class Lapangan extends AbstractDate implements Serializable {

    @Id
    @Column(name = "id_lapangan")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_lapangan;

    @Column(name="nama_lapangan", columnDefinition="TEXT")
    private String namaLapangan;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User id_user;

}
