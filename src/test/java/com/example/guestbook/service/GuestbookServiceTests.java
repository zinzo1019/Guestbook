package com.example.guestbook.service;

import com.example.guestbook.DTO.GuestbookDTO;
import com.example.guestbook.DTO.PageRequestDTO;
import com.example.guestbook.DTO.PageResultDTO;
import com.example.guestbook.Entity.Guestbook;
import com.example.guestbook.Repository.GuestbookRepository;
import com.example.guestbook.Service.GuestbookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.print.attribute.IntegerSyntax;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookServiceTests {

    @Autowired
    private GuestbookService service;

    @Autowired
    private GuestbookRepository repository;

    @Test
    public void testRegister() {

        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("new title...")
                .content("new content...")
                .writer("new writer...")
                .build();

        System.out.println(service.register(guestbookDTO));

    }

    @Test
    public void insertDummies() {

        IntStream.rangeClosed(1, 100).forEach( i -> {

            Guestbook guestbook = Guestbook.builder()
                    .title("real new Title..." + i)
                    .content("real new Content..." + i)
                    .writer("real new Writer..." + i)
                    .build();

            System.out.println(repository.save(guestbook));

        });
    }

    @Test
    public void testList() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        System.out.println("prev: " + resultDTO.isPrev());
        System.out.println("total: " + resultDTO.getTotalPage());

    }

}
