package com.github.ots.proposal.enums;

public enum RefillStatus {
    DELIVERY_REJECTED // customer rejected the delivery order
    ,PATIENT_DECLINED // Patient don't need the order
    ,PICKED_UP // Picked up Successfully
    ,READY_FOR_PICK_UP // Picked up Successfully
    ,DELIVERED // Delivered Successfully
    ,FOR_PICK_UP // Customer will pick up
    ,FOR_DELIVERY // Needs Delivery
    ,PACKED
    ,OUT_FOR_DELIVERY
    ,NEW
}
