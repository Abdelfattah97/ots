package com.github.ots.proposal.model;

import com.github.ots.proposal.enums.RefillStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "refill_status_lookup")
@Getter
@Setter
public class RefillStatusLookup {

    @Id
    private Integer id;
    private String status;
    private RefillStatus refillStatusCategory;

}
