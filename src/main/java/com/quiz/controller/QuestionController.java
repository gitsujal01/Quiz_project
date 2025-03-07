package com.quiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quiz.entity.Question;
import com.quiz.service.QuestionService;


@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionService qs;

    @GetMapping("/allQuestion")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return qs.getAllQuestion();
    }
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> SetQuestionByCategory(@PathVariable String category)
    {
    	return qs.getQuestionsByCategory(category);
    }
    @PostMapping("/add")
    public ResponseEntity<String>gaddQuestion(@RequestBody Question question)
    {
    	return qs.addQuestion(question);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteQues(@PathVariable Integer id) {  
        qs.deleteQue(id);
        return "deleted";
    }
    @PutMapping("/up/{id}")
    public ResponseEntity<Question> updateQue(@PathVariable Integer id,@RequestBody Question q)
    {
           Question qq = qs.updateQue(id, q);
           return ResponseEntity.ok(qq);
    }

}
