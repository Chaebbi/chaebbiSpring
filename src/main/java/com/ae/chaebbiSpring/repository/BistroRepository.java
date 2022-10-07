package com.ae.chaebbiSpring.repository;

import com.ae.chaebbiSpring.domain.Bistro;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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

    public List<Bistro> getCategoryList(String wide, String middle) {
        return em.createQuery("select b from Bistro b where b.wide = :wide and b.middle = :middle", Bistro.class)
                .setParameter("wide", wide)
                .setParameter("middle", middle)
                .getResultList();
    }

    public List<Bistro> getCategories(String wide, String middle) {
        return em.createQuery("select b from Bistro b where b.wide = :wide and b.middle = :middle group by b.category", Bistro.class)
                .setParameter("wide", wide)
                .setParameter("middle", middle)
                .getResultList();
    }

    public List<Bistro> getBistro() {
        return em.createQuery("select b from Bistro b", Bistro.class)
                .getResultList();
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
    public List<Bistro> getBistroMain(String main) {
        return em.createQuery("select b from Bistro b join BistroCategory bc on b.id = bc.bistro_id join MainCategory mc on mc.id = bc.main_id where mc.type = :main", Bistro.class)
                .setParameter("main", main)
                .getResultList();

    }

    public List<Bistro> getBistroMiddle(String main, String middle) {
        return em.createQuery("select b from Bistro b join BistroCategory bc on b.id = bc.bistro_id join MainCategory mc on mc.id = bc.main_id " +
                "join MiddleCategory mdc on mdc.id = bc.middle_id where mc.type = :main and mdc.type = :middle")
                .setParameter("main", main)
                .setParameter("middle", middle)
                .getResultList();
    }
}
