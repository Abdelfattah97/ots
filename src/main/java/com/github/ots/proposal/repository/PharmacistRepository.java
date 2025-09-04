package com.github.ots.proposal.repository;

import com.github.ots.proposal.model.Pharmacist;
import com.github.ots.proposal.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {
}
