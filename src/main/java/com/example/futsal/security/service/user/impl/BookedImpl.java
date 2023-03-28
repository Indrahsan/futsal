package com.example.futsal.security.service.user.impl;

import com.example.futsal.model.Booked;
import com.example.futsal.model.Lapangan;
import com.example.futsal.model.User;
import com.example.futsal.repository.admin.LapanganRepository;
import com.example.futsal.repository.user.BookedRepository;
import com.example.futsal.security.service.UserDetailsServiceImpl;
import com.example.futsal.security.service.user.BookedService;
import com.example.futsal.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

@Service
public class BookedImpl implements BookedService {

    @Autowired
    LapanganRepository lapanganRepository;
    @Autowired
    Global global;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    BookedRepository bookedRepository;

    @Override
    public ResponseEntity<Map> BookedLap(Long idLap, Principal principal, Booked obj) {
        User user = global.getUserIdToken(principal, userDetailsService);
        Lapangan lapangan = lapanganRepository.findLapanganByID(idLap);
        Booked check = bookedRepository.findLapanganAndBookedTime(lapangan, obj.getTime_booked());

        if (user == null) {
            return global.Response(null, 20, "User not found");
        }
        if (lapangan == null){
            return global.Response(null, 20, "Field not found");
        }
        if (check != null) {
            return global.Response(null, 20, "Field Not Available!");
        }


        Booked booked = new Booked();

        booked.setLapangan(lapangan);
        booked.setUser(user);
        booked.setTime_booked(obj.getTime_booked());

        bookedRepository.save(booked);
        return global.Response(booked, 0, "success booked!");
    }
}
