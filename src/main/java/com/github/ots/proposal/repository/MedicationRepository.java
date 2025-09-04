package com.github.ots.proposal.repository;

import com.github.ots.proposal.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {

    Set<Medication> findAllByMedicationNameIn(Collection<String> medicationNames);

}
