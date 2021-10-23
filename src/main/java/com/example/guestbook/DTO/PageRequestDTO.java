package com.example.guestbook.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {

    private int page;
    private int size;

    // 검색 조건 ex) 제목, 내용, 작성자
    private String type;
    // 검색어
    private String keyword;

    public PageRequestDTO() {
        this.page = 1;
        this.size = 10; // 한 페이지에 보이게 할 데이터 수
    }

    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page - 1, size, sort);
    }
}
