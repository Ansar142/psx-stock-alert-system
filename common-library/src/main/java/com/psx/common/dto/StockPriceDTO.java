package com.psx.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockPriceDTO implements Serializable {
    private String symbol;
    private double price;
    private double changePercent;
    private long volume;
    private double high;
    private double low;
    private double open;
    private LocalDateTime timestamp;
    private String source;
}