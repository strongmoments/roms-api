package com.roms.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name ="employee_feedback")
public class EmployeeFeedback extends CommonFields implements Serializable {

    @Column(name = "feedback_type")
    private int feedbackType;

    @Column(name = "rating" )
    private String rating;
    @Column(name = "outof" )
    private String outOf;
    @Column(name = "comments" )
    private String comments;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_idx",referencedColumnName = "id")
    private Employe employe;
}
