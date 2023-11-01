
package  com.example.demo.entity;
import com.example.demo.entity.Property;
import jakarta.persistence.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@Table(name = "valuation_reports")
public class ValuationReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reportId;

    @Column(nullable = true)
    private String fileName;

    @Column(nullable = true)
    private String pdfPath;

    @Column(nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'pending'")
    private String status;

    @Column
    private LocalDate requestedDate;

    @Column(nullable = true)
    private LocalDate submittedDate;

    @OneToOne
    @JoinColumn(name = "property_id")
    private Property property;


}


//@Entity
//@Table(name = "Valuation_Report")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class ValuationReport {
//
//    @Id
//    @Column(name = "id")
//    private Integer id;
//
//
//    @OneToOne
//    @MapsId
//    @JoinColumn(name = "id")
//    private Property property;
//
//    private String name;
//    private String type;
//    private String filePath;
//}

