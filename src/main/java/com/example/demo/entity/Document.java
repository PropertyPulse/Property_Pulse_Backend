
package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "files")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long fileId;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private FileType type;

    @Column(name = "file_url")
    private String fileUrl;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file_description")
    private String fileDescription;

    public void setFileType(String contentType) {
    }


    // Constructors, getters, and setters

    // You can also add additional methods as needed
}
