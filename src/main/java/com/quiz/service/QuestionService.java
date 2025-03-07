package com.quiz.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.quiz.dao.QuestionDao;
import com.quiz.entity.Question;
@Service
public class QuestionService {
    @Autowired
    private QuestionDao qd;

    public ResponseEntity<List<Question>> getAllQuestion() {
         try {
    	return new ResponseEntity<>(qd.findAll(),HttpStatus.OK);
         }
         catch(Exception e)
         {
        	 e.printStackTrace();
         }
     	return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);

         }

	public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
		// TODO Auto-generated method stub
		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
		}

	public ResponseEntity<String> addQuestion(Question question) {
		// TODO Auto-generated method stub
	        qd.save(question);
	        return new ResponseEntity<>("success",HttpStatus.CREATED);
	}
	public ResponseEntity<Void> deleteQue(Integer id)
	{       
		     qd.deleteById(id);
		     return ResponseEntity.noContent().build();
	       
	}
	public Question updateQue(Integer id, Question q) {
	    Question existingQuestion = qd.findById(id)
	        .orElseThrow(() -> new RuntimeException("Question not found with ID: " + id));

	    existingQuestion.setQuestiontitle(q.getQuestiontitle());
	    existingQuestion.setAnswer(q.getAnswer());
	    existingQuestion.setCategory(q.getCategory());
	    existingQuestion.setDifficultylevel(q.getDifficultylevel());
	    existingQuestion.setOption1(q.getOption1());
	    existingQuestion.setOption2(q.getOption2());
	    existingQuestion.setOption3(q.getOption3());
	    existingQuestion.setOption4(q.getOption4());

	    return qd.save(existingQuestion); // Returns the updated question
	}

}
