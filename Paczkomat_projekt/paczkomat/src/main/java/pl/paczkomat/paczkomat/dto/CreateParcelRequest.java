package pl.paczkomat.paczkomat.dto;

import lombok.Data;
import pl.paczkomat.paczkomat.model.Size;

@Data
public class CreateParcelRequest {
    private Size size;
}
