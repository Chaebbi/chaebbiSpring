package com.ae.chaebbiSpring.repository;

import com.ae.chaebbiSpring.domain.Suggestion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SuggestionRepository {
    private final EntityManager em;

    public List<Suggestion> getSuggestions (Long problemId){
        return em.createQuery("select s from Suggestion s where s.problemId = :problemId", Suggestion.class)
                .setParameter("problemId", problemId)
                .getResultList();
    }
}
