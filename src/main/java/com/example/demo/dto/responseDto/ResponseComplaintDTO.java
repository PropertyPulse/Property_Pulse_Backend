package com.example.demo.dto.responseDto;




import com.example.demo.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseComplaintDTO {
          private Long complaint_id;
          private String description;
            private String  reason;
            private String  title;
            private boolean issolved;
            private String telephone;
            private  Integer Complainant_id;


}
