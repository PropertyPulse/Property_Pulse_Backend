package com.example.demo.controller;

import com.example.demo.dto.requestDto.RequestFeedbackDTO;
import com.example.demo.dto.responseDto.*;
import com.example.demo.entity.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ComplaintRepository;
import com.example.demo.repository.PropertyOwnerRepository;
import com.example.demo.repository.PropertyRepository;
import com.example.demo.repository.TaskSupervisorRepository;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tm")

@PreAuthorize("hasRole('TOPMANAGER')")
public class TopManagerController {

    private final FeedbackService feedbackService;
//    private  final GeocodeController    geocodeController;


    private final TopManagerService topManagerService;
    private final ValuationReportService valuationReportService;


    private final  PropertyService propertyService;

    private final ComplaintRepository complaintRepository;
    private final PropertyRepository propertyRepository;
    private   final TaskSupervisorRepository taskSupervisorRepository;

    private final PropertyOwnerRepository propertyOwnerRepository;

    @Autowired
    public TopManagerController(FeedbackService feedbackService, TopManagerServiceImpl topManagerService, ValuationReportServiceImpl valuationReportService, PropertyService propertyService, ComplaintRepository complaintRepository, PropertyRepository propertyRepository, TaskSupervisorRepository taskSupervisorRepository, PropertyOwnerRepository propertyOwnerRepository) {
        this.feedbackService = feedbackService;
//        this.geocodeController = geocodeController;


        this.topManagerService = topManagerService;
        this.valuationReportService = valuationReportService;
        this.propertyService = propertyService;
        this.complaintRepository = complaintRepository;

        this.propertyRepository = propertyRepository;
        this.taskSupervisorRepository = taskSupervisorRepository;
        this.propertyOwnerRepository = propertyOwnerRepository;
    }

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('topmanager:read')")
    public ResponseEntity<String> get() {
        // Replace the following string with an actual object you want to return as JSON
        // You can create a class representing the response data and return an instance of that class.
        String responseData = "GET :: CUSTOMER";
        return ResponseEntity.ok(responseData);
    }


    @PostMapping("/give-a-feedback")
    @PreAuthorize("hasAuthority('topmanager:create')")
    public ResponseEntity<String> giveAFeedback(@RequestBody RequestFeedbackDTO requestFeedbackDTO) {

        try {
            feedbackService.addFeedback(requestFeedbackDTO);
            return ResponseEntity.ok("Feedback registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error registering feedback: " + e.getMessage());
        }
    }

    @GetMapping("/select-a-task-supervisor")
    @PreAuthorize("hasAuthority('topmanager:read')")
    public ResponseEntity<List<ResponseTaskSupervisorDTO>> SelectedTaskSupervisor(@RequestParam String address) throws IOException {


        if (address != null) {
            List<TaskSupervisor> taskSupervisor = topManagerService.SelectedSupervisors(address);
            List<ResponseTaskSupervisorDTO> responseTaskSupervisorDTOList = new ArrayList<>();
            for(TaskSupervisor taskSupervisor1 : taskSupervisor)
            {
                ResponseTaskSupervisorDTO responseTaskSupervisorDTO = new ResponseTaskSupervisorDTO();
                 responseTaskSupervisorDTO.setId(taskSupervisor1.getId());
                    responseTaskSupervisorDTO.setAddress(taskSupervisor1.getAddress());
                    responseTaskSupervisorDTO.setNearestTown(taskSupervisor1.getNearestTown());
                    responseTaskSupervisorDTO.setDistrict(taskSupervisor1.getDistrict());
                    responseTaskSupervisorDTO.setContactNo(taskSupervisor1.getContactNo());
                    responseTaskSupervisorDTO.setNic(taskSupervisor1.getNic());
                    responseTaskSupervisorDTO.setDob(taskSupervisor1.getDob());
                    responseTaskSupervisorDTO.setGender(taskSupervisor1.getGender());
                    responseTaskSupervisorDTOList.add(responseTaskSupervisorDTO);

                System.out.println(taskSupervisor1.getAddress());
            }
            System.out.println("No issues at all");
            return ResponseEntity.ok(responseTaskSupervisorDTOList);
        }

        return ResponseEntity.ok(null);


    }

    @GetMapping("/valuation-reports")
    @PreAuthorize("hasAuthority('topmanager:read')")
    public ResponseEntity<List<ResponseValuationDTO>> getValuationReports(
            @RequestParam(name = "status") String status
    ) throws ResourceNotFoundException {
        List<ResponseValuationDTO> reports;
        System.out.println(status);
        if (status != null) {
            reports = valuationReportService.getValuationReports(status.toLowerCase());
        } else {
            // Handle invalid status parameter or return an error response
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(reports);

    }


    @GetMapping("/requestValuationandAcceptProperty")
    @PreAuthorize("hasAuthority('topmanager:read')")
    public ResponseEntity<String> requestValuationandAcceptProperty(@RequestParam(name = "propertyId") Integer propertyId) throws ResourceNotFoundException {

        if (propertyId != null) {

            topManagerService.requestValuationandAcceptProperty(propertyId);
            return ResponseEntity.ok("Valuation Requested and Property Accepted");
        } else {
            // Handle invalid status parameter or return an error response
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/newmanagmentrequest")
    @PreAuthorize("hasAuthority('topmanager:read')")
    public ResponseEntity<List<ResponseNewManagementRequestDto>> newmanagementrequest() {
        try {
            List<Property> unacceptedProperties = propertyRepository.findByAcceptedStatus(false);
            System.out.println(unacceptedProperties.size());
            List<ResponseNewManagementRequestDto> responseDTOs = new ArrayList<>();

            for (Property property : unacceptedProperties) {
                ResponseNewManagementRequestDto responseDTO = new ResponseNewManagementRequestDto();
                responseDTO.setId(property.getId());
                responseDTO.setAddress(property.getAddress());
                responseDTO.setPropertyOwnerContactNo(property.getPropertyOwner().getTelephone());
                responseDTO.setRegisteredStatus(property.getRegisteredStatus());
                responseDTO.setVisitStatus(property.getVisitStatus());
                responseDTO.setPriceListStatus(property.getPriceListStatus());
                responseDTO.setAcceptedStatus(true);
                responseDTO.setRegisteredStatus("Registered");

                responseDTO.setAcceptedDate(property.getAccepted_date());
                responseDTO.setLegalContractStatus(property.getLegalContractStatus());

                responseDTO.setLocation(property.getLocation());
                responseDTO.setSpecialFacts(property.getSpecialFacts());
                responseDTO.setWantInsurance(property.getWantInsurance());
                responseDTO.setDuration(property.getDuration());
                responseDTO.setDistrict(property.getDistrict());


                if (property.getType() == PropertyType.LAND) {
                    // Set variables specific to LAND
                    responseDTO.setHaveCrops(property.getHaveCrops());
                    responseDTO.setCrops(property.getCrops());
                    responseDTO.setLandSize(property.getLandSize());
                    responseDTO.setType(PropertyType.LAND);
                    // Set other LAND-specific variables
                } else if (property.getType() == PropertyType.HOME) {
                    // Set variables specific to HOME
                    responseDTO.setBedrooms(property.getBedrooms());
                    responseDTO.setBathrooms(property.getBathrooms());
                    responseDTO.setLivingRooms(property.getLivingRooms());

                    responseDTO.setSpecialRooms(property.getSpecialRooms());
                    responseDTO.setStories(property.getStories());


                    responseDTO.setType(PropertyType.HOME);
                    // Set other HOME-specific variables
                }

                responseDTOs.add(responseDTO);
            }

            return ResponseEntity.ok(responseDTOs);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }

    }


    @GetMapping
    @PreAuthorize("hasAuthority('topmanager:read')")
    public ResponseEntity<List<Property>> getAllProperties() {
        return ResponseEntity.ok(propertyRepository.findAll());
    }

    //    @GetMapping("/viewPendingProperties")
//    public List<Property> viewPendingProperties() {
//
//           if(propertyRepository.findByAccepted_Status(false).isEmpty())
//           {
//               return null;
//           }
//           else
//           {
//               return propertyRepository.findByAccepted_Status(false);
//           }
//    }


    @PostMapping("/Assign-a-task-supervisor")
    @PreAuthorize("hasAuthority('topmanager:create')")
    public ResponseEntity<String> AssignTaskSupervisor(@RequestParam(name = "propertyId") Long propertyId, @RequestParam(name = "taskSupervisorName") Long taskSupervisorId) throws ResourceNotFoundException {

        if (propertyId != null && taskSupervisorId != null) {

            topManagerService.AssignTaskSupervisor(propertyId, taskSupervisorId);
            return ResponseEntity.ok("Task Supervisor Assigned");
        } else {
            // Handle invalid status parameter or return an error response
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('topmanager:read')")
    public ResponseEntity<List<ResponseComplaintDTO>> getAllComplaints() {
        try {
            List<NewComplaint> complaints = complaintRepository.findAllWithComplainantByIssolvedFalse();
            List<ResponseComplaintDTO> complaintDTOList = new ArrayList<>();
            if(complaints == null)
            {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            for (NewComplaint complaint : complaints) {

                ResponseComplaintDTO complaintDTO = new ResponseComplaintDTO();
                complaintDTO.setComplaint_id(complaint.getComplaint_id());
                complaintDTO.setDescription(complaint.getDescription());
                complaintDTO.setIssolved(complaint.isIssolved());
                complaintDTO.setComplainant_id(complaint.getComplainant().getId());
                complaintDTO.setTitle(complaint.getTitle());
                complaintDTO.setReason(complaint.getReason());
                complaintDTO.setTelephone(propertyOwnerRepository.findById(complaint.getComplainant().getId()).get().getTelephone());

                complaintDTOList.add(complaintDTO);


            }
            System.out.println("Number of complaints: " + complaints.size());
            return ResponseEntity.ok(complaintDTOList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }




    @PostMapping("/update-task-supervisor")
    @PreAuthorize("hasAuthority('topmanager:create')")
    public ResponseEntity<ResponseTaskSupervisorDTO> updateTaskSupervisor(@RequestBody Integer PropertyId) throws IOException {
        Property property = propertyRepository.findById(PropertyId).get();
        String address = propertyRepository.findById(PropertyId).get().getAddress();

        // Check if the property and new task supervisor exist
        if (property == null) {
            return ResponseEntity.internalServerError().body(null);
        }

        List<ResponseTaskSupervisorDTO> responseTaskSupervisorDTOList = this.SelectedTaskSupervisor(address).getBody();
        if(responseTaskSupervisorDTOList == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        ResponseTaskSupervisorDTO responseTaskSupervisorDTO = responseTaskSupervisorDTOList.remove(0);
        TaskSupervisor taskSupervisor = taskSupervisorRepository.getReferenceById(responseTaskSupervisorDTO.getId());
        property.setTaskSupervisor(taskSupervisor);
        propertyRepository.save(property);
        taskSupervisor.getProperties().add(property);
        taskSupervisorRepository.save(taskSupervisor);
        propertyRepository.save(property);



        return ResponseEntity.ok(responseTaskSupervisorDTO);
    }





    @GetMapping("/accepted")
    @PreAuthorize("hasAuthority('topmanager:read')")

    public ResponseEntity<List<UpdateTaskSupervisorResponseDTO>> getAllAcceptedProperties() {
        List<Property> propertyList =  propertyRepository.findByAcceptedStatusTrueAndTaskSupervisorIsNotNull();
        System.out.println(propertyList.size());
        List<UpdateTaskSupervisorResponseDTO> updateTaskSupervisorResponseDTOList = new ArrayList<>();
        if(propertyList.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        for(Property property:propertyList )
        {
            UpdateTaskSupervisorResponseDTO updateTaskSupervisorResponseDTO = new UpdateTaskSupervisorResponseDTO();
            updateTaskSupervisorResponseDTO.setPropertyId(property.getId());
            updateTaskSupervisorResponseDTO.setLocation(property.getLocation());
//            updateTaskSupervisorResponseDTO.setName(property.getTaskSupervisor().getUser().getFirstname() + " " + property.getTaskSupervisor().getUser().getLastname());
//            updateTaskSupervisorResponseDTO.setOngoingTask(property.getTaskSupervisor().getProperties().size());
            updateTaskSupervisorResponseDTOList.add(updateTaskSupervisorResponseDTO);
        }

        return ResponseEntity.ok(updateTaskSupervisorResponseDTOList);
    }
    @PostMapping("/reject-property")
    @PreAuthorize("hasAuthority('topmanager:create')")
    public ResponseEntity<String> rejectProperty(Integer propertyId) {
        Optional<Property> propertyOptional = propertyRepository.findById(propertyId);

        if (propertyOptional.isPresent()) {
            Property property = propertyOptional.get();
            property.setRegisteredStatus("Rejected");
            property.setDeleted(true); // Set the isDeleted flag to true
             propertyRepository.save(property);
            return ResponseEntity.ok("Property rejected successfully");
        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}




