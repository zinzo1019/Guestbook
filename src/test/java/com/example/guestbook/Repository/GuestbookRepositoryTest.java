package com.example.guestbook.Repository;

import com.example.guestbook.DTO.GuestbookDTO;
import com.example.guestbook.DTO.PageRequestDTO;
import com.example.guestbook.DTO.PageResultDTO;
import com.example.guestbook.Entity.Guestbook;
import com.example.guestbook.Entity.QGuestbook;
import com.example.guestbook.Service.GuestbookService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTest {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void insertDummies() {

        IntStream.rangeClosed(1, 300).forEach(i -> {

            Guestbook guestbook = Guestbook.builder()
                    .writer("Writer..." + i)
                    .content("Content..." + i)
                    .title("Title..." + i)
                    .build();

            System.out.println(guestbookRepository.save(guestbook));
        });
    }

    @Test
    public void updateTest() {

        Optional<Guestbook> result = guestbookRepository.findById(700L);

        if (result.isPresent()) {
            Guestbook guestbook = result.get();

            guestbook.changeTitle("new Title....");
            guestbook.changeContent("new Content....");
            guestbookRepository.save(guestbook);
        }
    }

    @Test
    public void testQuery1() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression expression = qGuestbook.title.contains(keyword);

        builder.and(expression);

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });

    }

    @Test
    public void testQuery2() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;

        BooleanBuilder builder = new BooleanBuilder();

        String keyword = "1";

        BooleanExpression expression1 = qGuestbook.title.contains(keyword);
        BooleanExpression expression2 = qGuestbook.content.contains(keyword);

        BooleanExpression allExpression = expression1.or(expression2);

        builder.and(allExpression);

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }

    @Autowired
    private GuestbookService service;

    @Test
    public void testList() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        System.out.println(resultDTO.isPrev());
        System.out.println(resultDTO.isNext());
        System.out.println(resultDTO.getTotalPage());

    }
}
