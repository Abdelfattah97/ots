package com.github.ots.proposal.repository;

import com.github.ots.proposal.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> , JpaSpecificationExecutor<Prescription> {

}
