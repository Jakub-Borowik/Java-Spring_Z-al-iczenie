package pl.kino.kino.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.kino.kino.repository.HallRepository;

@Service
@RequiredArgsConstructor
public class HallService {
    private final HallRepository hallRepository;

    
}
