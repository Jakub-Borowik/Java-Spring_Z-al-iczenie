package pl.kino.kino.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.kino.kino.dto.HallViewRowResponse;
import pl.kino.kino.service.HallViewService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/screenings")
public class ScreeningController {
    private final HallViewService hallViewService;

    @GetMapping("/{screeningId}/hall-view")
    public List<HallViewRowResponse> getHallView(@PathVariable Long screeningId) {
        return hallViewService.getHallView(screeningId);
    }
}
