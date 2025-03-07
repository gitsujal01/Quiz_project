package com.quiz.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.quiz.entity.Question;



@Repository
public interface QuestionDao extends JpaRepository<Question, Long> {

    @Query(value = "SELECT * FROM question q WHERE q.category = :category ORDER BY RANDOM() LIMIT :numQ", nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(@RequestParam("category") String category, @RequestParam("numQ") int numQ);

	List<Question> findByCategory(String category);

	void deleteById(Integer id);

	Optional<Question> findById(Integer id);
}


