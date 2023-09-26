package com.example.demo.controller;


import com.example.demo.dto.requestDto.*;
import com.example.demo.dto.responseDto.*;



import com.example.demo.service.ManpowerCompanyPaymentsService;
import com.example.demo.service.RecivedPaymentService;
import com.example.demo.service.TaskEquipmentPaymentService;
import com.example.demo.service.TransactionHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@PreAuthorize("hasRole('FINANCIALMANAGER')")
public class PaymentController {

    private final RecivedPaymentService recivedPaymentService;

    private final ManpowerCompanyPaymentsService manpowerCompanyPaymentsService;
    private final TaskEquipmentPaymentService taskEquipmentPaymentService;
    private final TransactionHistoryService transactionHistoryService;




    public PaymentController(RecivedPaymentService recivedPaymentService, ManpowerCompanyPaymentsService manpowerCompanyPaymentsService, TaskEquipmentPaymentService taskEquipmentPaymentService, TransactionHistoryService transactionHistoryService) {
        this.recivedPaymentService = recivedPaymentService;
        this.manpowerCompanyPaymentsService = manpowerCompanyPaymentsService;
        this.taskEquipmentPaymentService = taskEquipmentPaymentService;
        this.transactionHistoryService = transactionHistoryService;
    }

    @GetMapping("/all-received-monthly-payments")
    public ResponseEntity<List<ReceivedPaymentDto>> getAllMonthlyPayments() {
        List<ReceivedPaymentDto> payments = recivedPaymentService.getAllMonthlyPayments();
        return ResponseEntity.ok(payments);
    }
    @GetMapping("/all-received-task-payments")
    public ResponseEntity<List<ReceivedPaymentDto>> getAllTaskPayments() {
        List<ReceivedPaymentDto> payments = recivedPaymentService.getAllTaskPayments();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/unpaid-per-month")
    public ResponseEntity<List<MonthlyPaymentDto>> getTotalUnpaidPaymentsPerMonth() {

        List<MonthlyPaymentDto> unpaidPaymentsPerMonth = manpowerCompanyPaymentsService.getTotalUnpaidPaymentsPerMonth();

        return ResponseEntity.ok(unpaidPaymentsPerMonth);
    }


    @PostMapping("/updatePaymentStatus")
    public ResponseEntity<String> updatePaymentStatus(@RequestBody MonthlyPaymentDto monthlyPaymentDto) {
        try {
            // Call the updatePaymentStatus method from your service
            Boolean isUpdated = manpowerCompanyPaymentsService.updatePaymentStatus(monthlyPaymentDto);

            if (isUpdated) {
                return ResponseEntity.ok("Payment status updated successfully.");
            } else {
                return ResponseEntity.badRequest().body("Failed to update payment status.");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Replace this with proper error handling
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error occurred.");
        }
    }
    @GetMapping("/unpaid-task-equipment-payments")
    public ResponseEntity<List<ResponseTaskEquipPaymentsDto>> getAllTaskEquipmentpayments() {

        List<ResponseTaskEquipPaymentsDto> unpaidPaymentsPerMonth = taskEquipmentPaymentService.getAllTaskEquipmentpayments();

        return ResponseEntity.ok(unpaidPaymentsPerMonth);
    }

    @PostMapping("/updatePaymentStatus_task")
    public ResponseEntity<String> updatePaymentStatus(@RequestBody RequestUpdatePaymentDto payment) {


        Boolean updated = taskEquipmentPaymentService.updatePaymentStatus(payment);
        if (updated != null && updated) {
            return ResponseEntity.ok("Payment status updated successfully");
        } else {
            return ResponseEntity.notFound().build(); // or return ResponseEntity.badRequest() if you prefer
        }
    }


    @PostMapping("/insert-income")
    public ResponseEntity<String> insertIncome(@RequestBody RequestInsertincome requestInsertincome) {
        try {
            boolean success = transactionHistoryService.insertIncome(requestInsertincome.getAmount(), requestInsertincome.getDescription());
            if (success) {
                return new ResponseEntity<>("Income transaction added successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to add income transaction", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    add monthly payments

    @PostMapping("/monthly")
    public ResponseEntity<String> addMonthlyPayment(@RequestBody PaymentRequestDto request) {
        if (recivedPaymentService.addMonthlyPayment(request.getPropertyId(), request.getAmount(), request.getDescription())) {
            return ResponseEntity.ok("Monthly payment added successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to add monthly payment");
        }
    }


//    add task payments
    @PostMapping("/task")
    public ResponseEntity<String> addTaskPayment(@RequestBody PaymentRequestDto request) {
        if (recivedPaymentService.addTaskPayment(request.getPropertyId(), request.getAmount(), request.getDescription())) {
            return ResponseEntity.ok("Task payment added successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to add task payment");
        }
    }

    @GetMapping("/getAllTransactionHistory")
    public List<TransactionHistoryDto> getAllTransactionHistory() {
        return transactionHistoryService.getAllTransactionHistory();
    }

    @GetMapping("/getAllCurrentYearIncomesbymonthly")
    public ResponseEntity<List<MonthlySummaryDto>> getAllCurrentYearIncomesbymonthly() {
        return ResponseEntity.ok(transactionHistoryService.getMonthlyIncomeSummary());
    }


    @GetMapping("/getAllCurrentYearExpensesbymonthly")
    public ResponseEntity<List<MonthlySummaryDto>> getAllCurrentYearExpensesbymonthly() {
        return ResponseEntity.ok(transactionHistoryService.getMonthlyExpenseSummary());
    }


    @GetMapping("/getFMDashboardData")
    public ResponseEntity<List<FinancialManagerDashboardDataDto>> getFMDashboardData() {
        return ResponseEntity.ok(transactionHistoryService.getFMDashboardData());
    }

    @GetMapping("/getTotalIncome")
    public ResponseEntity<Double> getTotalIncome() {
        return ResponseEntity.ok(transactionHistoryService.getTotalIncome());
    }

    @GetMapping("/getTotalOutcome")
    public ResponseEntity<Double> getTotalOutcome() {
        return ResponseEntity.ok(transactionHistoryService.getTotalOutcome());
    }


//    add manpowrcompany payment

    @PostMapping("/add_taskequipment_payment")
    public ResponseEntity<String> addTaskEquipmentPayment(@RequestBody RequestAddTaskEquipmentPaymentDto req) {
        try {
            boolean result = taskEquipmentPaymentService.addTaskEquipmentPayment(req);

            if (result) {
                return ResponseEntity.ok("Task equipment payment added successfully");
            } else {
                return ResponseEntity.badRequest().body("Failed to add task equipment payment");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @PostMapping("/add_manpowercompany_payments")
    public ResponseEntity<String> addManpowerCompanyPayment(@RequestBody RequestMonthlyPayementManpowerCompanyDto req) {
        try {
            boolean result = manpowerCompanyPaymentsService.addManpowerCompanyPayment(req);

            if (result) {
                return ResponseEntity.ok("Payment added successfully");
            } else {
                return ResponseEntity.badRequest().body("Failed to add payment");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }


}
