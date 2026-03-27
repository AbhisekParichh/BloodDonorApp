package com.example.demo.repositories;

import com.example.demo.models.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DonorRepository extends JpaRepository<Donor, Long> {
    List<Donor> findByBloodType(String bloodType);
    List<Donor> findByLocation(String location);
}

/*
import com.example.demo.models.Donor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonorRepository extends JpaRepository<Donor, Long> {
}*/