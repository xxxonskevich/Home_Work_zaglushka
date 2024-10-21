package org.example.model;

import lombok.*;

import java.math.BigDecimal;

@Data

public class ResponseDTO {
    private String rqUID;
    private String clientId;
    private String account;
    private String currency;
    private BigDecimal balance;
    private BigDecimal maxLimit;
}
