package com.quiz.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quiz.dao.QuestionDao;
import com.quiz.dao.QuizDao;
import com.quiz.entity.Question;
import com.quiz.entity.QuestionWrapper;
import com.quiz.entity.Quiz;
import com.quiz.entity.Response;

@Service
public class QuizService {
	 @Autowired
     QuizDao quizdao;
     
	 @Autowired
	 QuestionDao questionDao;
	 public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
		    List<Question> questions = questionDao.findByCategory(category);
		    System.out.println("Fetched Questions: " + questions);  // ✅ Debugging ke liye

		    if (questions.isEmpty()) {
		        return ResponseEntity.badRequest().body("No questions found for category: " + category);
		    }

		    Quiz quiz = new Quiz();
		    quiz.setTitle(title);
		    quiz.setQuestions(questions);  // ✅ Ensure ye line execute ho

		    Quiz savedQuiz = quizdao.save(quiz);
		    System.out.println("Saved Quiz: " + savedQuiz);  // ✅ Debugging ke liye

		    return ResponseEntity.ok("Quiz Created Successfully!");
		}
	 public ResponseEntity<List<QuestionWrapper>> getQuizquestions(Integer id) {
		    Optional<Quiz> quiz = quizdao.findById(id);
		    
		    if (quiz.isEmpty()) {
		        System.out.println("Quiz not found for id: " + id);
		        return ResponseEntity.notFound().build();
		    }

		    System.out.println("Quiz found: " + quiz.get()); // ✅ Check quiz details

		    List<Question> questionList = quiz.get().getQuestions();
		    
		    if (questionList == null || questionList.isEmpty()) {
		        System.out.println("No questions found for quiz id: " + id);
		        return ResponseEntity.noContent().build();  // ✅ No questions case
		    }

		    List<QuestionWrapper> questions = questionList.stream()
		        .map(q -> new QuestionWrapper(q.getId(), q.getQuestiontitle(),
		                                      q.getOption1(), q.getOption2(),
		                                      q.getOption3(), q.getOption4()))
		        .toList();

		    return ResponseEntity.ok(questions);
		}
	 public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
		    // Fetch the quiz
		    Optional<Quiz> quizOptional = quizdao.findById(id);
		    if (quizOptional.isEmpty()) {
		        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 if quiz not found
		    }

		    Quiz quiz = quizOptional.get();
		    List<Question> questions = quiz.getQuestions();
		    int right = 0;

		    // Process each user response
		    for (Response response : responses) {
		        // Find the correct question by matching ID
		        Question question = questions.stream()
		            .filter(q -> q.getId().equals(response.getId()))
		            .findFirst()
		            .orElse(null);

		        if (question == null) {
		            System.out.println("❌ Question not found for Response ID: " + response.getId());
		            continue; // Skip this response if the question is missing
		        }

		        System.out.println("User Response: [" + response.getResponse() + "]");
		        System.out.println("Correct Answer: [" + question.getAnswer() + "]");

		        // Check answer (ignores case & trims spaces)
		        if (response.getResponse().trim().equalsIgnoreCase(question.getAnswer().trim())) {
		            right++;
		            System.out.println("✅ Matched!");
		        } else {
		            System.out.println("❌ Not Matched!");
		        }
		    }

		    return new ResponseEntity<>(right, HttpStatus.OK);
		}

}
