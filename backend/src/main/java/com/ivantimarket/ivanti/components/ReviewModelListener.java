package com.ivantimarket.ivanti.components;


import com.ivantimarket.ivanti.model.Review;
import com.ivantimarket.ivanti.model.User;
import com.ivantimarket.ivanti.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class ReviewModelListener extends AbstractMongoEventListener<Review> {
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    public ReviewModelListener(SequenceGeneratorService sequenceGeneratorService) {
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Review> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGeneratorService.generateSequence(Review.SEQUENCE_NAME));
        }
    }
}
