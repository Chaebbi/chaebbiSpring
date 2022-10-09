package com.ae.chaebbiSpring.repository;

import com.ae.chaebbiSpring.domain.Bistro;
import com.ae.chaebbiSpring.domain.MainCategory;
import com.ae.chaebbiSpring.domain.MiddleCategory;
import com.ae.chaebbiSpring.dto.response.BistroDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BistroRepository {
    private final EntityManager em;

    public Bistro findOne(Long id) { return em.find(Bistro.class, id);  }

    public List<Bistro> getMiddle(String wide) {
        return em.createQuery("select b from Bistro b where b.wide = :wide group by b.middle", Bistro.class)
                .setParameter("wide", wide)
                .getResultList();
    }

    public List<BistroDto> getCategoryList(String wide, String middle) {
        List<Bistro> bistros = em.createQuery("select b from Bistro b where b.wide = :wide and b.middle = :middle", Bistro.class)
                .setParameter("wide", wide)
                .setParameter("middle", middle)
                .getResultList();

        List<BistroDto> bistroDtos = new ArrayList<>();
        for(Bistro b : bistros) {
            MainCategory mainCategory = em.createQuery("select mc from BistroCategory bc join Bistro b on b.id = bc.bistro_id join " +
                    "MainCategory mc on mc.id = bc.main_id join MiddleCategory mdc on mdc.id = bc.middle_id where b.id = :id", MainCategory.class)
                    .setParameter("id", b.getId())
                    .getSingleResult();
            MiddleCategory middleCategory = em.createQuery("select mdc from BistroCategory bc join Bistro b on b.id = bc.bistro_id join " +
                            "MainCategory mc on mc.id = bc.main_id join MiddleCategory mdc on mdc.id = bc.middle_id where b.id = :id", MiddleCategory.class)
                    .setParameter("id", b.getId())
                    .getSingleResult();
            bistroDtos.add(new BistroDto(b.getId(), mainCategory.getType() + ", " + middleCategory.getType(), b.getName(), b.getWide(), b.getMiddle(), b.getRAddr(), b.getLAddr(), b.getTel(), b.getMenu(), b.getLa(), b.getLo(), b.getUrl()));
        }
        return bistroDtos;
    }

    public List<String> getCategories(String wide, String middle) {
        List<Bistro> bistros = em.createQuery("select b from Bistro b join BistroCategory bc on b.id = bc.bistro_id " +
                        "join MainCategory mc on mc.id = bc.main_id " +
                        "join MiddleCategory mdc on mdc.id = bc.middle_id " +
                        "where b.wide = :wide and b.middle = :middle group by bc.main_id, bc.middle_id", Bistro.class)
                .setParameter("wide", wide)
                .setParameter("middle", middle)
                .getResultList();

        List<String> categories = new ArrayList<>();
        for(Bistro b : bistros) {
            MainCategory mainCategory = em.createQuery("select mc from BistroCategory bc join Bistro b on b.id = bc.bistro_id join " +
                            "MainCategory mc on mc.id = bc.main_id join MiddleCategory mdc on mdc.id = bc.middle_id where b.id = :id", MainCategory.class)
                    .setParameter("id", b.getId())
                    .getSingleResult();
            MiddleCategory middleCategory = em.createQuery("select mdc from BistroCategory bc join Bistro b on b.id = bc.bistro_id join " +
                            "MainCategory mc on mc.id = bc.main_id join MiddleCategory mdc on mdc.id = bc.middle_id where b.id = :id", MiddleCategory.class)
                    .setParameter("id", b.getId())
                    .getSingleResult();
            categories.add(mainCategory.getType() + ", " + middleCategory.getType());
        }

        return categories;
    }

    public List<BistroDto> getBistro() {
        List<Bistro> bistros = em.createQuery("select b from Bistro b", Bistro.class)
                .getResultList();

        List<BistroDto> bistroDtos = new ArrayList<>();
        for(Bistro b : bistros) {
            MainCategory mainCategory = em.createQuery("select mc from BistroCategory bc join Bistro b on b.id = bc.bistro_id join " +
                            "MainCategory mc on mc.id = bc.main_id join MiddleCategory mdc on mdc.id = bc.middle_id where b.id = :id", MainCategory.class)
                    .setParameter("id", b.getId())
                    .getSingleResult();
            MiddleCategory middleCategory = em.createQuery("select mdc from BistroCategory bc join Bistro b on b.id = bc.bistro_id join " +
                            "MainCategory mc on mc.id = bc.main_id join MiddleCategory mdc on mdc.id = bc.middle_id where b.id = :id", MiddleCategory.class)
                    .setParameter("id", b.getId())
                    .getSingleResult();
            bistroDtos.add(new BistroDto(b.getId(), mainCategory.getType() + ", " + middleCategory.getType(), b.getName(), b.getWide(), b.getMiddle(), b.getRAddr(), b.getLAddr(), b.getTel(), b.getMenu(), b.getLa(), b.getLo(), b.getUrl()));
        }
        return bistroDtos;
    }

    public List<Bistro> getWide() {
        return em.createQuery("select b from Bistro b group by b.wide")
                .getResultList();
    }

    public String getUrl(int bistroId) {
        return em.createQuery("select b from Bistro b where b.id = :id", Bistro.class)
                .setParameter("id", Long.valueOf(String.valueOf(bistroId)))
                .getSingleResult()
                .getUrl();
    }
    public List<Bistro> getBistroMain(String siteWide, String siteMiddle, String categoryMain) {
        return em.createQuery("select b from Bistro b join BistroCategory bc on b.id = bc.bistro_id join MainCategory mc on mc.id = bc.main_id " +
                        "where mc.type = :categoryMain and b.wide = :siteWide and b.middle = :siteMiddle", Bistro.class)
                .setParameter("categoryMain", categoryMain)
                .setParameter("siteWide", siteWide)
                .setParameter("siteMiddle", siteMiddle)
                .getResultList();

    }

    public List<Bistro> getBistroMiddle(String siteWide, String siteMiddle, String categoryMain, String categoryMiddle) {
        return em.createQuery("select b from Bistro b join BistroCategory bc on b.id = bc.bistro_id join MainCategory mc on mc.id = bc.main_id " +
                "join MiddleCategory mdc on mdc.id = bc.middle_id where mc.type = :categoryMain and mdc.type = :categoryMiddle and " +
                        "b.wide = :siteWide and b.middle = :siteMiddle")
                .setParameter("categoryMain", categoryMain)
                .setParameter("categoryMiddle", categoryMiddle)
                .setParameter("siteWide", siteWide)
                .setParameter("siteMiddle", siteMiddle)
                .getResultList();
    }
}
