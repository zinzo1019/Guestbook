package com.example.guestbook.Repository;

import com.example.guestbook.Entity.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long>,
        QuerydslPredicateExecutor<Guestbook> {
}
