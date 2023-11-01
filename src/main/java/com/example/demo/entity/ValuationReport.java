package  com.example.demo.entity;
import com.example.demo.entity.Property;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "valuation_reports")
public class ValuationReport

{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
     private Integer reportId;


     private String fileName;


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
//
//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer id;
//
//
//

private String name;
private String type;
private String filePath;


        }