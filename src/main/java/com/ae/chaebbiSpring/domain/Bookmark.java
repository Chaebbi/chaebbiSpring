package com.ae.chaebbiSpring.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "bookmark")
@Getter @Setter
@NoArgsConstructor
public class Bookmark {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "bm_user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "bistro_id")
    private Bistro bistro;

    public static Bookmark createBookmark(User user, Bistro bistro){
        Bookmark bookmark = new Bookmark();
        bookmark.setUser(user);
        bookmark.setBistro(bistro);
        return bookmark;
    }


}
