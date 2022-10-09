package com.ae.chaebbiSpring.service;

import com.ae.chaebbiSpring.domain.Bistro;
import com.ae.chaebbiSpring.dto.response.BistroDto;
import com.ae.chaebbiSpring.dto.response.CategoryListDto;
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

    public List<BistroDto> getCategoryList(String wide, String middle) {
        return bistroRepository.getCategoryList(wide, middle);
    }

    public List<String> getCategories(String wide, String middle) {
        return bistroRepository.getCategories(wide, middle);
    }

    public List<BistroDto> getBistro() {
        return bistroRepository.getBistro();
    }


    public List<Bistro> getWide() {
        return bistroRepository.getWide();
    }

    public String getUrl(int bistroId) {
        return bistroRepository.getUrl(bistroId);
    }

    public List<Bistro> getBistroMain(String siteWide, String siteMiddle, String categoryMain) {
        return bistroRepository.getBistroMain(siteWide, siteMiddle, categoryMain);
    }

    public List<Bistro> getBistroMiddle(String siteWide, String siteMiddle, String categoryMain, String categoryMiddle) {
        return bistroRepository.getBistroMiddle(siteWide, siteMiddle, categoryMain, categoryMiddle);
    }
}
