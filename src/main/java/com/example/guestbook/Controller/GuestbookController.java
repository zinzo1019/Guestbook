package com.example.guestbook.Controller;

import com.example.guestbook.DTO.GuestbookDTO;
import com.example.guestbook.DTO.PageRequestDTO;
import com.example.guestbook.Service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService service;

    @GetMapping("/")
    public String index() {
        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        log.info("list....." + pageRequestDTO);

        // pageResultDTO 타입을 result로 넘긴다.
        model.addAttribute("result", service.getList(pageRequestDTO));

    }

    @GetMapping("/register")
    public void register() {

    }

    @PostMapping("/register") // button type="submit" 하면 무조건 @PostMapping과 연결된다.
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes) {

        Long gno = service.register(dto);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {

        log.info("modify getmapping started...");

        GuestbookDTO dto = service.read(gno);

        model.addAttribute("dto", dto);

    }

    @PostMapping("/modify")
    public String modify(GuestbookDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes) {

        log.info("modify postmapping started...");

        service.modify(dto);

        log.info("modify dto gno: " + dto.getGno());

        log.info("modify type: " + requestDTO.getType());
        log.info("modify keyword: " + requestDTO.getKeyword());

//        addFlashAttribute가 되지 않도록 조심하기
//        /guestbook/read 페이지에 접근하기 위해선 gno의 값과 page 정보를 알아야 하므로 이 데이터를 넘겨준다.
        redirectAttributes.addAttribute("gno", dto.getGno());
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("type", requestDTO.getType());
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword());

        return "redirect:/guestbook/read";
    }

    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes) {

        log.info("remove method started...");

        service.remove(gno);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";

    }
}
