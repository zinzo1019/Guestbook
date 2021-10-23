package com.example.guestbook.Service;

import com.example.guestbook.DTO.GuestbookDTO;
import com.example.guestbook.DTO.PageRequestDTO;
import com.example.guestbook.DTO.PageResultDTO;
import com.example.guestbook.Entity.Guestbook;
import com.example.guestbook.Entity.QGuestbook;
import com.example.guestbook.Repository.GuestbookRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor // 이걸 어노테이션 해야 final에서 오류 안 남.
public class GuestbookServiceImpl implements GuestbookService {

    private final GuestbookRepository repository;

    @Override
    public Long register(GuestbookDTO dto) {

        Guestbook entity = dtoToEntity(dto);

        log.info(entity.toString());

        repository.save(entity);

        return entity.getGno();

    }

    @Override
    public GuestbookDTO read(Long gno) {

        Optional<Guestbook> result = repository.findById(gno);

        return result.isPresent() ? entityToDto(result.get()) : null;

    }

    @Override
    public void modify(GuestbookDTO dto) {

        log.info("modify dto: " + dto.toString());

        Optional<Guestbook> result = repository.findById(dto.getGno());

        if (result.isPresent()) {

            Guestbook entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            log.info("updated entity: " + entity.toString());

            repository.save(entity);

        }

    }

    @Override
    public void remove(long gno) {

        log.info("remove gno: " + gno);
        repository.deleteById(gno);

    }

//    검색 함수
    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {

        String type = requestDTO.getType();
        String keyword = requestDTO.getKeyword();

        QGuestbook qGuestbook = QGuestbook.guestbook;

        // 첫 번째 컨테이너 생성
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // gno > 0인 데이터 (조건을 생성하기 위해선 qGuestbook 사용)
        BooleanExpression expression = qGuestbook.gno.gt(0L);

        // 첫 번째 조건 생성
        booleanBuilder.and(expression);

        if (type == null || type.trim().length() == 0)
            return booleanBuilder;

        // 두 번째 컨테이너 생성
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if (type.contains("t")) {
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }
        if (type.contains("c")) {
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        }
        if (type.contains("w")) {
            conditionBuilder.or(qGuestbook.writer.contains(keyword));
        }

        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;

    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {

        // getPageable() 함수에 인자로 정렬 방식을 넘겨준다.
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDTO);

        Page<Guestbook> result = repository.findAll(booleanBuilder, pageable);

        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

}
