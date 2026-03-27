package com.example.demo.services;


import com.example.demo.models.Donor;
import com.example.demo.repositories.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonorService {

    @Autowired
    private DonorRepository donorRepository;

    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    public Donor addDonor(Donor donor) {
        return donorRepository.save(donor);
    }

    public void deleteDonor(Long id) {
        donorRepository.deleteById(id);
    }

    public List<Donor> findByBloodType(String bloodType) {
        return donorRepository.findByBloodType(bloodType);
    }

    public List<Donor> findByLocation(String location) {
        return donorRepository.findByLocation(location);
    }
}


/*
import com.example.demo.models.Donor;
import com.example.demo.repositories.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonorService {

    @Autowired
    private DonorRepository donorRepository;

    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    public Donor addDonor(Donor donor) {
        return donorRepository.save(donor);
    }

    public void deleteDonor(Long id) {
        donorRepository.deleteById(id);
    }
} */