package com.quiz.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.entity.Quiz;


@Repository
public interface QuizDao extends JpaRepository<Quiz, Integer>{

}
