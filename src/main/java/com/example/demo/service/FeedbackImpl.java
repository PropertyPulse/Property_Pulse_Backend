package com.example.demo.service;


import com.example.demo.dto.requestDto.RequestFeedbackDTO;
import com.example.demo.entity.Complaint;
import com.example.demo.entity.Feedback;
import com.example.demo.entity.NewComplaint;
import com.example.demo.repository.ComplaintRepository;
import com.example.demo.repository.FeedbackRepository;
import com.example.demo.repository.TopManagerRepository;
import com.example.demo.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

       private TopManagerRepository topManagerRepository;


       @Autowired
       public FeedbackImpl(FeedbackRepository feedbackRepository, ComplaintRepository complaintRepository, UserRepository userRepository, TopManagerRepository topManagerRepository)
       {   this.feedbackRepository = feedbackRepository;
           this.complaintRepository = complaintRepository;
           this.userRepository = userRepository;


           this.topManagerRepository = topManagerRepository;
       }
        public void addFeedback(RequestFeedbackDTO requestFeedbackDTO) {



                if(requestFeedbackDTO.getComplaintId()!=null){

                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                    String username = userDetails.getUsername();

                    String feedback = requestFeedbackDTO.getFeedbackContent();
                    Long complaintId = requestFeedbackDTO.getComplaintId();

                    int  userId = userRepository.findByEmail(username).get().getId();
                    Feedback feedback1 = new Feedback();
                    feedback1.setFeedbackContent(feedback);
                    feedback1.setComplaint(complaintRepository.getReferenceById(Math.toIntExact(complaintId)));
                    feedback1.setTopManager(topManagerRepository.getReferenceById(userId));
                    feedback1.setCreatedAt(new java.util.Date());
                    feedback1.setStatus("Pending");
                     NewComplaint complaint = complaintRepository.getReferenceById(Math.toIntExact(complaintId));
                    complaint.setIssolved(true);
                    complaintRepository.save(complaint);
                    feedbackRepository.save(feedback1);







                }
                else{


                    System.out.println("Complaint id is null");
                }

        }




}
