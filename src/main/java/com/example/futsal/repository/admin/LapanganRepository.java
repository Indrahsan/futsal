package com.example.futsal.repository.admin;

import com.example.futsal.model.Lapangan;
import com.example.futsal.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LapanganRepository extends PagingAndSortingRepository<Lapangan, Long> {

    @Query("select c from Lapangan c WHERE c.namaLapangan = ?1 AND c.id_user =?2")
    public Lapangan findLapanganAndCreatedBy(String namaLapangan, User user);

    @Query("select c from Lapangan c WHERE c.id_lapangan = ?1")
    public Lapangan findLapanganByID(Long id_lapangan);
}
