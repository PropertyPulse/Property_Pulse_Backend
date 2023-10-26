package com.example.demo.dto.responseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlySummaryDto {
    private Integer month;
    private Double totalAmount;
}