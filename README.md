# Price Comparator - Java Spring Boot

##  i. Overview

Această aplicație Java Spring Boot compară prețurile produselor din mai multe lanțuri de magazine (Lidl, Profi, Kaufland), oferind:

-  Coș de cumpărături optimizat zilnic  
-  Cele mai bune reduceri actuale  
-  Reduceri adăugate în ultimele 24h  
-  Istoric dinamic de prețuri  
-  Recomandări bazate pe valoarea per unitate (RON/kg, RON/l)  
-  Alerte de preț în timp real via WebSocket

---

##  ii. Structură Proiect

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

---

##  iii. Cum se construiește și rulează aplicația

1. **Clonare repo**
   ```bash
   git clone https://github.com/BoomBar03/Price-Comparator-Backend.git
   cd Price-Comparator-Backend
   ```

2. **Build + Run**
   ```bash
   mvn spring-boot:run
   ```

3. **Acces aplicație**
   - Aplicația rulează la: [http://localhost:8080](http://localhost:8080)

---

##  iv. Presupuneri și simplificări

- Nu se folosește o bază de date — toate datele sunt citite din fișiere `.csv` din `resources/data/`
- Aplicația consideră că prețul cel mai recent este cel din fișierul cu data cea mai mare
- Alertele WebSocket sunt doar pe sesiunea curentă (nu se persistă date)

---

##  v. Cum se folosesc funcționalitățile (Postman / API)

### 1.  Daily Basket Optimization

```http
POST /api/basket/optimize
Content-Type: application/json

[
  { "productName": "lapte zuzu", "quantity": 2 },
  { "productName": "pâine albă", "quantity": 1 }
]
```

 Returnează cele mai ieftine opțiuni și totalul.

---

### 2.  Best Discounts

```http
GET /api/discounts/top
```

 Returnează cele mai mari reduceri curente (inclusiv preț vechi și nou)

---

### 3.  New Discounts

```http
GET /api/discounts/new?date=2025-05-25
```

 Returnează reducerile noi (apărute în ultimele 24h față de data dată)

---

### 4.  Price History

```http
GET /api/price-history?productName=lapte&store=Lidl&brand=Zuzu
```

 Returnează istoricul de prețuri pentru un produs, filtrabil după magazin / brand / categorie

---

### 5.  Recommendations (Value per Unit)

```http
GET /api/recommendations?category=gustări
```

 Returnează produse similare cu cel mai bun raport preț/cantitate

---

### 6.  Price Alerts (WebSocket - PieSocket)

- **Endpoint**: `ws://localhost:8080/alerts`
- **Input**:
  ```json
  {
    "productName": "lapte zuzu",
    "targetPrice": 9.50
  }
  ```
- **Output**:
  ```json
  {
    "alertTriggered": true,
    "productName": "lapte zuzu",
    "storeName": "Profi",
    "price": 9.20,
    "targetPrice": 9.50
  }
  ```

---

##  vi. Testare

- Se folosesc **JUnit 5** + **Mockito**
- Teste implementate pentru:
  - `CsvService`
  - `DailyBasketService`
- Testele folosesc fișiere `.csv` din `src/test/resources/data/`
