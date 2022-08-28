package com.ae.chaebbiSpring.service;

import com.ae.chaebbiSpring.domain.Bistro;
import com.ae.chaebbiSpring.repository.BistroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BistroService {
    private final BistroRepository bistroRepository;

    public Bistro findOne(Long id) { return bistroRepository.findOne(id); }

    public List<Bistro> getMiddle(String wide) {
        return bistroRepository.getMiddle(wide);
    }

    public List<Bistro> getCategoryList(String wide, String middle) {
        return bistroRepository.getCategoryList(wide, middle);
    }

    public List<Bistro> getCategories(String wide, String middle) {
        return bistroRepository.getCategories(wide, middle);
    }

    public List<Bistro> getBistro() {
        return bistroRepository.getBistro();
    }


    public List<Bistro> getWide() {
        return bistroRepository.getWide();
    }
}
