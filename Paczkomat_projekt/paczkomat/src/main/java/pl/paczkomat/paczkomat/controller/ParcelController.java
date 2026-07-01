package pl.paczkomat.paczkomat.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pl.paczkomat.paczkomat.dto.CreateParcelRequest;
import pl.paczkomat.paczkomat.model.Parcel;
import pl.paczkomat.paczkomat.service.ParcelService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/parcels")
public class ParcelController {
    private final ParcelService parcelService;

    @PostMapping
    public String createParcel(@RequestBody CreateParcelRequest request) {
        Parcel parcel = parcelService.createParcel(request.getSize());
        
        return "Parcel created with an id: " + parcel.getId();
    }
    
}
