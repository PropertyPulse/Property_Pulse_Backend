package com.example.demo.service;

import com.example.demo.entity.TaskSupervisor;


import java.io.IOException;
import java.util.List;

public interface TopManagerService {

    List<TaskSupervisor> SelectedSupervisors(String address) throws IOException;

}
