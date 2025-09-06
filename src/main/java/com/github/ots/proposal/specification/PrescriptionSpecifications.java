package com.github.ots.proposal.specification;

import com.github.ots.common.search.specification.AbstractSpecification;
import com.github.ots.proposal.model.Medication;
import com.github.ots.proposal.model.Prescription;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class PrescriptionSpecifications extends AbstractSpecification {

    public static Specification<Prescription> hasPatientId(String patientId) {
        return ((root, query, cb) ->
                patientId == null ? null :
                        cb.like(cb.lower(root.get("patient").get("patientId")), String.format("%s%s", patientId.toLowerCase(), wild)));

    }

    public static Specification<Prescription> hasPatientName(String patientName) {
        return ((root, query, cb) ->
                patientName == null ? null :
                        cb.like(cb.lower(root.get("patient").get("fullName")), String.format("%s%s%s", wild, patientName.toLowerCase(), wild)));
    }

    public static Specification<Prescription> hasMobileNumber(String mobileNumber) {
        return ((root, query, cb) ->
                mobileNumber == null ? null :
                        cb.like(cb.lower(root.get("patient").get("mobileNumber")), String.format("%s%s", mobileNumber.toLowerCase(), wild)));
    }

    public static Specification<Prescription> hasPrescriptionId(Long prescriptionId) {
        return ((root, query, cb) ->
                prescriptionId == null ? null :
                        cb.like(cb.toString(root.get("prescriptionId")), String.format("%s%s", prescriptionId, wild)));
    }

    public static Specification<Prescription> hasAppointmentId(String appointmentId) {
        return ((root, query, cb) ->
        {
            if (appointmentId == null) return null;
            Join<Prescription, String> join = root.join("appointments");
            return cb.like(join, String.format("%s%s", appointmentId, wild));
        });
    }

    public static Specification<Prescription> hasMedicationName(String medicationName) {
        return ((root, query, cb) ->
        {
            if (medicationName == null) return null;
            Join<Prescription, Medication> join = root.join("medications");
            return cb.like(cb.lower(join.get("medicationName")), String.format("%s%s", medicationName.toLowerCase(), wild));
        });
    }

    public static Specification<Prescription> hasPatientInfo(String patientInfo) {
        return ((root, query, cb) -> {
           return patientInfo == null ? null :
                    cb.or(
                            cb.like(cb.lower(root.get("patient").get("fullName")), String.format("%s%s%s", wild, patientInfo.toLowerCase(), wild))
                            , cb.like(cb.lower(root.get("patient").get("patientId")), String.format("%s%s", patientInfo.toLowerCase(), wild)));
        });
    }

}