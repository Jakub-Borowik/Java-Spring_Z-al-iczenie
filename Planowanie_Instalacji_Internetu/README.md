# Planowanie_Instalacji_Internetu

Projekt zaliczeniowy Spring Boot + Vaadin.

- UI klienta: `/internet-installations`
- Panel roli wyzszej: `/technician/internet-installations`
- Swagger: `/swagger-ui.html`
- REST POST: `/api/internet-installations`
- Baza PostgreSQL: `planowanie_instalacji_internetu`
- Loginy: `client/client`, `technician/technician`

## Opis algorytmu

Algorytm przydziela instalatora do technologii, liczby punktow, certyfikatu i okna czasowego. Najpierw waliduje dane: kategorie, wymaganie pojemnosci, minimalna jakosc, liczbe jednostek pracy, priorytet oraz zakres dat.

Potem odrzuca zasoby, ktore sa niedostepne, maja zla kategorie, zbyt mala pojemnosc, zbyt niska jakosc, zerowa wydajnosc albo koliduja czasowo z istniejacymi wpisami.

Nastepnie liczy wymagany czas:

`ceil(taskUnits / unitsPerHour)`

Na koncu liczy wynik punktowy:

`cena za wymagane godziny + kara za nadmiar pojemnosci + kara za nadmiar jakosci + kara/bonus za priorytet`

Wygrywa zasob z najmniejszym wynikiem punktowym. Dzieki temu aplikacja nie wybiera pierwszego wolnego rekordu, tylko najbardziej dopasowany i sensowny kosztowo.
