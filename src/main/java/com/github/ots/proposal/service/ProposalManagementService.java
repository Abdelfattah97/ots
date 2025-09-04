package com.github.ots.proposal.service;

import com.github.ots.common.search.pagination.PageDto;
import com.github.ots.common.search.pagination.PageRequestDto;
import com.github.ots.proposal.dto.basic.BasicPrescriptionDto;
import com.github.ots.proposal.mapper.PrescriptionMapper;
import com.github.ots.proposal.model.Prescription;
import com.github.ots.proposal.repository.PrescriptionRepository;
import com.github.ots.proposal.specification.PrescriptionSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
@AllArgsConstructor
public class ProposalManagementService {

    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionMapper prescriptionMapper;

    public PageDto<BasicPrescriptionDto> searchBasicPrescription(List<Specification<Prescription>> specificationsList, PageRequestDto pageRequestDto) {
        Pageable pageable = pageRequestDto.toPageable();
        Specification<Prescription> specification = null;
        Iterator<Specification<Prescription>> specIterator = specificationsList.iterator();
        if (specIterator.hasNext()) {
            specification = specIterator.next();
            while (specIterator.hasNext()) {
                specification = specification.and(specIterator.next());
            }
        }
        Page<BasicPrescriptionDto> prescriptionPage = prescriptionRepository.findAll(specification, pageable).map(prescriptionMapper::mapToBasicDto);
        return PageDto.fromPage(prescriptionPage);
    }


}
