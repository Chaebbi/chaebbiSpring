package com.ae.chaebbiSpring.service;

import com.ae.chaebbiSpring.domain.Bistro;
import com.ae.chaebbiSpring.domain.Bookmark;
import com.ae.chaebbiSpring.domain.User;
import com.ae.chaebbiSpring.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public Long create(Bookmark bookmark) {
        bookmarkRepository.save(bookmark);
        return bookmark.getId();
    }

    public List<Bistro> findBookmark(Long userId) {
        return bookmarkRepository.findBookmark(userId);
    }

    /*
    *
    * @Transactional
	public int delete(long id) {
		Optional<User> oUser = userRepository.findById(id);
		if(oUser.isPresent()) {
			userRepository.delete(oUser.get());
			return 1;
		}
		return 0;
	}*/
    @Transactional
    public Long deleteBookmark(Long userId, Long bistroId) {
        Long findBmId = bookmarkRepository.findBookmarkById(userId, bistroId);
        if(findBmId != null){
            Long deleteId = bookmarkRepository.deleteBoomark(findBmId);
            return deleteId;
        }
        return 0L;
    }

}
