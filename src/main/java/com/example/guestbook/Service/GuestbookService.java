package com.example.guestbook.Service;

import com.example.guestbook.DTO.GuestbookDTO;
import com.example.guestbook.DTO.PageRequestDTO;
import com.example.guestbook.DTO.PageResultDTO;
import com.example.guestbook.Entity.Guestbook;

public interface GuestbookService {

    Long register (GuestbookDTO dto);

    GuestbookDTO read(Long gno);

    void modify(GuestbookDTO dto);

    void remove(long gno);

//    페이징 처리를 위한 함수
    PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO);

    default Guestbook dtoToEntity(GuestbookDTO dto) {

        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();

        return entity;
    }

    default GuestbookDTO entityToDto(Guestbook entity) {

        GuestbookDTO dto = GuestbookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return dto;
    }
}
