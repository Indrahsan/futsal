package com.example.futsal.repository.user;

import com.example.futsal.model.Booked;
import com.example.futsal.model.Lapangan;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookedRepository extends PagingAndSortingRepository<Booked, Long> {
}
