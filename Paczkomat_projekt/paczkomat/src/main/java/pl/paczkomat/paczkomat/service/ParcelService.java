package pl.paczkomat.paczkomat.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.paczkomat.paczkomat.repository.ParcelRepository;

@RequiredArgsConstructor
@Service
public class ParcelService {
    private final ParcelRepository parcelRepository;
}
