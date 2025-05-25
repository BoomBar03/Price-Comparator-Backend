  i. Overview
  Această aplicație Java Spring Boot compară prețurile produselor din mai multe lanțuri de magazine (Lidl, Profi, Kaufland), oferind:
  
  - coș de cumpărături optimizat zilnic
  - cele mai bune reduceri
  - reduceri adăugate în ultimele 24h
  - istoric de prețuri
  - recomandări bazate pe valoarea per unitate
  - alertă de preț în timp real via WebSocket
  
## Structură Proiect

```text
src
├── main
│   ├── java/com.project.PriceComparator
│   │   ├── controller         # REST API endpoints
│   │   ├── service            # Logica aplicației
│   │   ├── dto                # Obiecte DTO (request/response)
│   │   ├── model              # Entități interne
│   │   ├── scheduler          # Task programat pt. alerte
│   │   └── websocket          # Notificări via WebSocket
│   └── resources
│       ├── application.properties
│       └── data/              # Fișiere CSV cu prețuri și reduceri
└── test
    ├── java/com.project.PriceComparator
    └── resources/data/        # Fișiere CSV de test
```

  ii. Cum se construiește și rulează aplicația
    1. Clonare:
      git clone https://github.com/BoomBar03/Price-Comparator-Backend.git
      cd price-comparator
    2. Build + Run (folosind Maven)
    3. Aplicația va porni pe: http://localhost:8080

   iii. **Presupuneri și simplificări**
  - Nu există bază de date — toate datele sunt citite din fișiere .csv aflate în resources/data.
  - Prețul unui produs este cel mai recent apărut într-un fișier CSV cu o anumită dată.
  - Nu se salvează alertele în memorie persistentă — WebSocket-ul e bazat doar pe sesiunea activă.

    iv. **Cum se folosesc funcționalitățile utilizând Postman (exemple API)**
    1. Daily Basket Optimization
             POST /api/basket/optimize
              Content-Type: application/json
              [
                { "productName": "lapte zuzu", "quantity": 2 },
                { "productName": "pâine albă", "quantity": 1 }
              ]
        Răspuns: magazinele în care se află produsele cel mai ieftine + total
       
    3. Best Discounts
           GET /api/discounts/top
        Răspuns: top reduceri actuale, cu preț vechi, nou și procent
       
    4.  New Discounts
           GET /api/discounts/new?date=2025-05-25
        Răspuns: reduceri apărute recent (în ultimele 24h față de data furnizată)

    5.  Price History
           GET /api/price-history?productName=lapte&store=Lidl&brand=Zuzu
        Răspuns: istoric ordonate după prețuri. Filtrabil după store, category, brand

    6. Recommendations (value per unit)
           GET /api/recommendations?category=gustări
         Răspuns: produse similare mai avantajoase (RON/kg, RON/l)

    7. Price Alerts (WebSocket) - utilizând PieHost
         URL: ws://localhost:8080/alerts
       Pentru input-ul { "productName": "lapte zuzu", "targetPrice": 9.50 }, se primește { "alertTriggered": true, "productName": "lapte zuzu", "storeName": "Profi", "price": 9.20, "targetPrice": 9.50 }

    v. Testare
  - Se folosesc JUnit 5
  - Pentru câteva servicii (CsvService, DailyBasketService) există teste în src/test/java.
  - Testele folosesc fișiere CSV dedicate, aflate în src/test/resources/data.
