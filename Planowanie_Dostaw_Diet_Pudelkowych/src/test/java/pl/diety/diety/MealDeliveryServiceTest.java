package pl.diety.diety;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pl.diety.diety.model.*;
import pl.diety.diety.repository.MealDeliveryRepository;
import pl.diety.diety.repository.MealRouteRepository;
import pl.diety.diety.service.AppUserService;
import pl.diety.diety.service.MealDeliveryService;

@ExtendWith(MockitoExtension.class)
class MealDeliveryServiceTest {
    @Mock
    private MealRouteRepository resourceRepository;

    @Mock
    private MealDeliveryRepository recordRepository;

    @Mock
    private AppUserService appUserService;

    @InjectMocks
    private MealDeliveryService service;

    @Test
    void shouldChooseBestScoredResource() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(3);
        when(resourceRepository.findAll()).thenReturn(List.of(
                resource(1L, DietArea.CENTER, 10, 5, 5, "100.00"),
                resource(2L, DietArea.CENTER, 3, 3, 5, "70.00"),
                resource(3L, DietArea.SUBURBS, 3, 3, 5, "50.00")));
        when(recordRepository.existsOverlapping(anyLong(), eq(start), eq(end))).thenReturn(false);

        MealRoute result = service.findBestResource(DietArea.CENTER,
                3, 3, 10, 1, start, end);

        assertEquals(2L, result.getId());
    }

    @Test
    void shouldSkipOverlappingBetterResource() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(3);
        when(resourceRepository.findAll()).thenReturn(List.of(
                resource(1L, DietArea.CENTER, 3, 3, 5, "70.00"),
                resource(2L, DietArea.CENTER, 5, 4, 5, "90.00")));
        when(recordRepository.existsOverlapping(eq(1L), eq(start), eq(end))).thenReturn(true);
        when(recordRepository.existsOverlapping(eq(2L), eq(start), eq(end))).thenReturn(false);

        MealRoute result = service.findBestResource(DietArea.CENTER,
                3, 3, 10, 1, start, end);

        assertEquals(2L, result.getId());
    }

    @Test
    void shouldRejectWhenTimeWindowIsTooShort() {
        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = start.plusHours(2);
        when(resourceRepository.findAll()).thenReturn(List.of(
                resource(1L, DietArea.CENTER, 3, 3, 5, "70.00")));
        when(recordRepository.existsOverlapping(eq(1L), eq(start), eq(end))).thenReturn(false);

        assertThrows(RuntimeException.class,
                () -> service.findBestResource(DietArea.CENTER,
                        3, 3, 20, 1, start, end));
    }

    @Test
    void shouldCalculateStartedRequiredHoursAndPrice() {
        MealRoute resource = resource(1L, DietArea.CENTER,
                3, 3, 5, "100.00");

        assertEquals(2, service.calculateRequiredHours(resource, 6));
        assertEquals(new BigDecimal("220.00"), service.calculatePrice(resource, 6, 3));
    }

    private MealRoute resource(Long id, DietArea category, int capacity,
            int quality, int unitsPerHour, String price) {
        MealRoute resource = new MealRoute();
        resource.setId(id);
        resource.setName("Test");
        resource.setCategory(category);
        resource.setCapacity(capacity);
        resource.setQuality(quality);
        resource.setUnitsPerHour(unitsPerHour);
        resource.setPricePerHour(new BigDecimal(price));
        resource.setAvailable(true);
        return resource;
    }
}
