package pl.kino.kino.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.kino.kino.model.Screening;
import pl.kino.kino.repository.ScreeningRepository;

@Service
@RequiredArgsConstructor
public class ScreeningService {
    private final ScreeningRepository screeningRepository;

    public List<Screening> getAllScreenings() {
        return screeningRepository.findAll();
    }

    public Screening getScreeningById(Long screeningId) {
        return screeningRepository.findById(screeningId)
                .orElseThrow(() -> new RuntimeException("Screening not found."));
    }
}
