package com.example.demo.repository;

import com.example.demo.entity.ManPowerCompany;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManPowerCompanyRepository extends JpaRepository<ManPowerCompany, Integer> {

//    ManPowerCompany findManPowerCompanyByUser(User user);
    ManPowerCompany findByUser(User user);

    ManPowerCompany findByUserId(Integer userId);



}
