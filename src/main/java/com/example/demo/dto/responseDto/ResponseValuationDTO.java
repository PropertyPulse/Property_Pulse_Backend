package com.example.demo.dto.responseDto;

import com.example.demo.entity.PropertyType;
import com.example.demo.entity.TaskRequest;
import lombok.Data;
import okhttp3.internal.concurrent.Task;

import java.util.List;


@Data
public class ResponseValuationDTO {


    private Integer propertyId;
    private PropertyType type;
    private String location;
    private List<TaskRequest> taskList;
    private String contact;
    private String status;

    private String fileLink;
    private String district;
    private String address;
    private Double landSize;
    private String haveCrops;

    private String specialFacts;
    private String crops;
}
