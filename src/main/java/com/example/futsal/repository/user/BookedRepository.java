package com.example.futsal.repository.user;

import com.example.futsal.model.Booked;
import com.example.futsal.model.Lapangan;
import com.example.futsal.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface BookedRepository extends PagingAndSortingRepository<Booked, Long> {
    @Query("select c from Booked c WHERE c.lapangan = ?1 AND c.time_booked =?2")
    public Booked findLapanganAndBookedTime(Lapangan lapangan, Date time_booked);
}
