package com.example.demo.service;

import com.example.demo.dto.requestDto.RequestAddTaskEquipmentPaymentDto;
import com.example.demo.dto.requestDto.RequestUpdatePaymentDto;
import com.example.demo.dto.responseDto.ResponseTaskEquipPaymentsDto;
import com.example.demo.entity.Property;
import com.example.demo.entity.TaskEquipmentPayment;
import com.example.demo.entity.TaskSupervisor;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.repository.TaskEquipmentPaymentRepository;
import com.example.demo.repository.TaskSupervisorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TaskEquipmentPaymentServiceImpl implements TaskEquipmentPaymentService {
    private final TransactionHistoryService transactionHistoryService;
    private final TaskEquipmentPaymentRepository taskEquipmentPaymentRepository;
    private final PropertyRepository propertyRepository;
    private final TaskSupervisorRepository taskSupervisorRepository;

    public TaskEquipmentPaymentServiceImpl(TransactionHistoryService transactionHistoryService, TaskEquipmentPaymentRepository taskEquipmentPaymentRepository, PropertyRepository propertyRepository, TaskSupervisorRepository taskSupervisorRepository) {
        this.transactionHistoryService = transactionHistoryService;
        this.taskEquipmentPaymentRepository = taskEquipmentPaymentRepository;
        this.propertyRepository = propertyRepository;
        this.taskSupervisorRepository = taskSupervisorRepository;
    }

    @Override
    public List<ResponseTaskEquipPaymentsDto> getAllTaskEquipmentpayments() {

        List<ResponseTaskEquipPaymentsDto> responseTaskEquipPaymentsDtoList = new ArrayList<>();

        List<TaskEquipmentPayment> unpaidPayments = taskEquipmentPaymentRepository.findByIsPaidFalse();

        for (TaskEquipmentPayment taskEquipmentPayment : unpaidPayments) {
            ResponseTaskEquipPaymentsDto responseTaskEquipPaymentsDto = new ResponseTaskEquipPaymentsDto();
            responseTaskEquipPaymentsDto.setPaymentId(taskEquipmentPayment.getId());
            responseTaskEquipPaymentsDto.setDate(taskEquipmentPayment.getDate());
            responseTaskEquipPaymentsDto.setAmount(taskEquipmentPayment.getAmount());
            responseTaskEquipPaymentsDto.setDescription(taskEquipmentPayment.getDescription());
            responseTaskEquipPaymentsDto.setPropertyId(taskEquipmentPayment.getProperty().getId());
            responseTaskEquipPaymentsDto.setTaskSupervisorId(taskEquipmentPayment.getTaskSupervisor().getId());
            responseTaskEquipPaymentsDto.setTaskSupervisorName(taskEquipmentPayment.getTaskSupervisor().getUser().getFirstname());
            responseTaskEquipPaymentsDtoList.add(responseTaskEquipPaymentsDto);
        }

        return responseTaskEquipPaymentsDtoList;

    }

    @Override
    public Boolean updatePaymentStatus(RequestUpdatePaymentDto payment) {
        System.out.println(
                "Payment ID: " + payment.getId() +
                        "\nPayment method: " + payment.getPaymentMethod() +
                        "\nDescription: " + payment.getDescription()
        );
        try{
            TaskEquipmentPayment taskEquipmentPayment = taskEquipmentPaymentRepository.findById(payment.getId()).orElse(null);
            if(taskEquipmentPayment != null){
                taskEquipmentPayment.setPaid(true);
                transactionHistoryService.insertExpense(taskEquipmentPayment.getAmount(), payment.getDescription(),payment.getPaymentMethod());
                taskEquipmentPaymentRepository.save(taskEquipmentPayment);
                return true;
            }
            else{
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace(); // Replace this with proper error handling
            return false;
        }
    }

    @Override
    public Boolean addTaskEquipmentPayment(RequestAddTaskEquipmentPaymentDto req) {
        TaskEquipmentPayment payment = new TaskEquipmentPayment();
//        System.out.println(req.getProperty_id());
        var property = propertyRepository.findById(req.getProperty_id());
        System.out.println(property.get().getId());

        var taskSupervisor = taskSupervisorRepository.findById(req.getTaskSupervisor_id());

        payment.setProperty(property.get());
        payment.setTaskSupervisor(taskSupervisor.get());

        payment.setDescription(req.getDescription());
//        LocalDate today = LocalDate.now();
        payment.setDate(req.getDate());
        payment.setPaid(false);
        payment.setAmount(req.getAmount());

        taskEquipmentPaymentRepository.save(payment);

        return true;
    }


}
