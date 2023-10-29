package com.example.demo.service;


import com.example.demo.dto.requestDto.RequestFeedbackDTO;
import com.example.demo.repository.ComplaintRepository;
import com.example.demo.repository.FeedbackRepository;
import com.example.demo.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Builder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service

public class FeedbackImpl implements FeedbackService{


    @PersistenceContext
    private EntityManager entityManager;

       private FeedbackRepository feedbackRepository;
       private ComplaintRepository complaintRepository;
       private UserRepository userRepository;


       @Autowired
       public FeedbackImpl(FeedbackRepository feedbackRepository, ComplaintRepository complaintRepository, UserRepository userRepository)
       {   this.feedbackRepository = feedbackRepository;
           this.complaintRepository = complaintRepository;
           this.userRepository = userRepository;



         }
        public void addFeedback(RequestFeedbackDTO requestFeedbackDTO) {



                if(requestFeedbackDTO.getComplaintId()!=null){

                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                    String username = userDetails.getUsername();

                    String feedback = requestFeedbackDTO.getFeedbackContent();
                    Long complaintId = requestFeedbackDTO.getComplaintId();

                    int  userId = userRepository.findByEmail(username).get().getId();



                }
                else{

                }

        }




}
