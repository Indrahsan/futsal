package com.example.futsal.security.service.admin.impl;

import com.example.futsal.model.Lapangan;
import com.example.futsal.model.User;
import com.example.futsal.repository.admin.LapanganRepository;
import com.example.futsal.security.service.UserDetailsServiceImpl;
import com.example.futsal.security.service.admin.LapanganService;
import com.example.futsal.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.Map;

@Service
public class LapanganImpl implements LapanganService {

    @Autowired
    Global global;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    LapanganRepository lapanganRepository;

    @Override
    public ResponseEntity<Map> CreateLap(Lapangan lapangan, Principal principal) {
        User user =  global.getUserIdToken(principal, userDetailsService);
        Lapangan findLapanganAndCreatedBy = lapanganRepository.findLapanganAndCreatedBy(lapangan.getNamaLapangan(), user);
        try {
            if (findLapanganAndCreatedBy != null) {
                return global.Response(null, 20, "the field name is already taken on your account!");
            }
            Lapangan obj = new Lapangan();
            obj.setNamaLapangan(lapangan.getNamaLapangan());
            obj.setId_user(user);

            Lapangan lap = lapanganRepository.save(obj);
            return global.Response(lap, 0, "succesful add field!");
        } catch (Exception e) {
            return global.Response(null, 500, ""+e);
        }

    }

    @Override
    public ResponseEntity<Map> DelLap(Long idLap, Principal principal) {
        Lapangan lapangan = lapanganRepository.findLapanganByID(idLap);
        User user =  global.getUserIdToken(principal, userDetailsService);
        try {
            if (lapangan == null) {
                return global.Response(null, 21, "the field not found!");
            }

            if (lapangan.getId_user() != user){
                return global.Response(null, 21, "you don't have access to delete field!");
            }

            lapangan.setDeleted_date(new Date());

            Lapangan obj = lapanganRepository.save(lapangan);
            return global.Response(obj, 0, "Delete Success!");
        } catch (Exception e) {
            return global.Response(null, 500, ""+e);
        }

    }

    @Override
    public ResponseEntity<Map> EditLap(Long idLap, Principal principal, Lapangan obj) {
        Lapangan lapangan = lapanganRepository.findLapanganByID(idLap);
        User user =  global.getUserIdToken(principal, userDetailsService);

        try {
            if (lapangan == null) {
                return global.Response(null, 21, "the field not found!");
            }
            if (lapangan.getId_user() != user){
                return global.Response(null, 21, "you don't have access to edit this field!");
            }

            lapangan.setNamaLapangan(obj.getNamaLapangan());

            Lapangan update = lapanganRepository.save(lapangan);
            return global.Response(update, 0, "success update!");
        } catch (Exception e) {
            return global.Response(null, 500, ""+e);
        }
    }
}
