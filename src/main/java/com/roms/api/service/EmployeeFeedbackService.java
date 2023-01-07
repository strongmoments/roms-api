package com.roms.api.service;

import com.roms.api.model.EmployeeFeedback;
import com.roms.api.repository.EmployeeFeedbackRepository;
import com.roms.api.requestInput.OnboardingFeedBackInput;
import com.roms.api.utils.LoggedInUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class EmployeeFeedbackService {

    @Autowired
    private EmployeeFeedbackRepository employeeFeedbackRepository;

    @Autowired
    private LoggedInUserDetails loggedIn;


    public EmployeeFeedback save(EmployeeFeedback employeeFeedback){
        employeeFeedback.setOrganisation(loggedIn.getOrg());
        employeeFeedback.setCreateBy(loggedIn.getUser());
        employeeFeedback.setCreateDate(Instant.now());

        return employeeFeedbackRepository.save(employeeFeedback);

    }

    public EmployeeFeedback findByEmployeeAndFeedbackType(int feedbackType){
        Optional<EmployeeFeedback> employeeFeedbackResul = employeeFeedbackRepository.findByEmployeAndOrganisationAndFeedbackType(loggedIn.getUser().getEmployeId(), loggedIn.getOrg(),feedbackType);
        if(employeeFeedbackResul.isPresent()){
            return employeeFeedbackResul.get();
        }
      return null;

    }


    public EmployeeFeedback saveOnboarding(OnboardingFeedBackInput request){
        EmployeeFeedback feedback = findByEmployeeAndFeedbackType(1);
        EmployeeFeedback employeeFeedbackModel = EmployeeFeedback.
                builder()
                .feedbackType(1)
                .outOf(request.getOutOf())
                .rating(request.getRating())
                .comments(request.getComments())
                .employe(loggedIn.getUser().getEmployeId())
        .build();
        if(feedback != null){
            employeeFeedbackModel.setId(feedback.getId());
        }

        return  save(employeeFeedbackModel);


    }

}
