package com.example.demo.service;

import com.example.demo.dto.requestDto.RequestEmployeeDetailsDto;
import com.example.demo.dto.requestDto.RequestUpdateEmployeeDetailsDto;
import com.example.demo.dto.requestDto.RequestUserdetails;
import com.example.demo.dto.responseDto.ResponseEmployeeDetailsDto;
import com.example.demo.dto.responseDto.ResponseUpdateEmployeeDetailsDto;
import com.example.demo.entity.Employee;
import com.example.demo.entity.ManPowerCompany;
import com.example.demo.exception.UserException;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.ManPowerCompanyRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ManPowerCompanyServiceImpl implements ManPowerCompanyService {

    private final EmployeeRepository employeeRepository;
    private final ManPowerCompanyRepository manPowerCompanyRepository;

    private final UserRepository userRepository;

    public ManPowerCompanyServiceImpl(EmployeeRepository employeeRepository, ManPowerCompanyRepository manPowerCompanyRepository, UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.manPowerCompanyRepository = manPowerCompanyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEmployeeDetailsDto addEmployee(RequestEmployeeDetailsDto req) throws UserException {
        ResponseEmployeeDetailsDto responseEmployeeDetailsDto = new ResponseEmployeeDetailsDto();

        var user = userRepository.findByEmail(req.getEmail());

        if (user.isEmpty()) {
            throw new UserException("User not found");
        }


        ManPowerCompany manPowerCompany = manPowerCompanyRepository.findByUserId(user.get().getId());

        System.out.println(manPowerCompany.getId());
        Employee employee = new Employee();

        employee.setAddress(req.getAddress());
        employee.setNic(req.getNic());
        employee.setContactno(req.getContactno());
        employee.setName(req.getName());
        employee.setSkills(req.getSkills());
        employee.setDistrict(req.getDistrict());
        employee.setManPowerCompany(manPowerCompany);

//
//        private String name;
//        private String address;
//        private String nic;
//        private String contactno;
//        private String district;
//        private List<String> skills;


        Employee savedEmployee=employeeRepository.save(employee);
        responseEmployeeDetailsDto.setAddress(savedEmployee.getAddress());
        responseEmployeeDetailsDto.setNic(savedEmployee.getNic());
        responseEmployeeDetailsDto.setContactno(savedEmployee.getContactno());
        responseEmployeeDetailsDto.setName(savedEmployee.getName());
        responseEmployeeDetailsDto.setSkills(savedEmployee.getSkills());
        responseEmployeeDetailsDto.setDistrict(savedEmployee.getDistrict());
        responseEmployeeDetailsDto.setId(savedEmployee.getId());


        return  responseEmployeeDetailsDto;
    }

    @Override
    public ResponseEntity<List<ResponseEmployeeDetailsDto>> getallEmployees(RequestUserdetails req) throws UserException {

        var user = userRepository.findByEmail(req.getEmail());
        if (user.isEmpty()) {
            throw new UserException("User not found");
        }

        User user1 = user.get();


        //        Integer companyId = req.getCompanyId(); // Assuming you have the company ID


        // Retrieve the ManPowerCompany entity using company ID
        ManPowerCompany manPowerCompany = manPowerCompanyRepository.findByUserId(user1.getId());
        // Get the list of employees from the retrieved ManPowerCompany entity
        List<Employee> employees = manPowerCompany.getEmployees();

        // Convert Employee entities to ResponseEmployeeDetailsDto objects
        List<ResponseEmployeeDetailsDto> employeeDetailsDtos = employees.stream()
                .map(this::convertToEmployeeDetailsDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(employeeDetailsDtos);
    }

    private ResponseEmployeeDetailsDto convertToEmployeeDetailsDto(Employee employee) {
        ResponseEmployeeDetailsDto employeeDetailsDto = new ResponseEmployeeDetailsDto();
        employeeDetailsDto.setId(employee.getId());
        employeeDetailsDto.setName(employee.getName());
        employeeDetailsDto.setAddress(employee.getAddress());
        employeeDetailsDto.setNic(employee.getNic());
        employeeDetailsDto.setContactno(employee.getContactno());
        employeeDetailsDto.setDistrict(employee.getDistrict());
        employeeDetailsDto.setSkills(employee.getSkills());

        return employeeDetailsDto;
    }

    @Override
    public ResponseUpdateEmployeeDetailsDto updateEmployee(Integer employeeId, RequestUpdateEmployeeDetailsDto req) throws UserException {
        // Check if the employee with the provided ID exists
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new UserException("Employee not found"));

        // Update the existing employee's details
        existingEmployee.setName(req.getName());
        existingEmployee.setDistrict(req.getDistrict());
        existingEmployee.setContactno(req.getContactno());
        existingEmployee.setNic(req.getNic());
        existingEmployee.setAddress(req.getAddress());
        existingEmployee.setSkills(req.getSkills());

        System.out.println(req.getSkills());

        // ... Update other properties

        // Save the updated employee
        Employee updatedEmployee = employeeRepository.save(existingEmployee);

        // Convert and return the updated employee as a DTO
        return convertToResponseUpdateEmployeeDetailsDto(updatedEmployee);
    }

    private ResponseUpdateEmployeeDetailsDto convertToResponseUpdateEmployeeDetailsDto(Employee employee) {
        ResponseUpdateEmployeeDetailsDto employeeDetailsDto = new ResponseUpdateEmployeeDetailsDto();
        employeeDetailsDto.setId(employee.getId());
        employeeDetailsDto.setName(employee.getName());
        employeeDetailsDto.setAddress(employee.getAddress());
        employeeDetailsDto.setNic(employee.getNic());
        employeeDetailsDto.setContactno(employee.getContactno());
        employeeDetailsDto.setDistrict(employee.getDistrict());
        employeeDetailsDto.setSkills(employee.getSkills());

        return employeeDetailsDto;
    }


    @Override
    public String deleteEmployee(Integer employeeId) throws UserException {
        // Check if the employee with the provided ID exists
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new UserException("Employee not found"));

        // Delete the employee
        employeeRepository.delete(existingEmployee);

        return "Employee deleted successfully";
    }


}
