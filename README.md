# Spring Cloud Stream + RabbitMQ 微服務架構實戰 ⚡

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2024.0.2-blue.svg)](https://spring.io/projects/spring-cloud)
[![RabbitMQ](https://img.shields.io/badge/RabbitMQ-3.7+-ff69b4.svg)](https://www.rabbitmq.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## 專案介紹

本專案展示如何使用 **Spring Cloud Stream** 結合 **RabbitMQ** 實現微服務間的非同步訊息通訊。透過咖啡店訂單系統的實際案例，演示從訂單建立、支付到咖啡製作的完整流程，展現現代微服務架構中事件驅動設計的實作方式。

### 核心功能
- **訂單管理系統**：支援咖啡訂單的建立、查詢與狀態更新
- **非同步訊息處理**：使用 RabbitMQ 實現服務間解耦通訊
- **事件驅動架構**：基於 Spring Cloud Stream 的函數式編程模型
- **狀態機管理**：訂單狀態的流程控制與追蹤

> 💡 **為什麼選擇此架構？**
> - **解耦合設計**：服務間透過訊息佇列通訊，降低直接依賴
> - **高可用性**：訊息持久化確保系統可靠性
> - **擴展性佳**：可輕鬆新增更多服務節點
> - **易於維護**：清晰的服務邊界與職責分離

### 🎯 專案特色

- **現代化技術棧**：採用 Spring Boot 3.x + Spring Cloud 2024.x
- **函數式編程**：使用 Spring Cloud Stream 4.x 函數式模型
- **完整監控**：整合 Spring Boot Actuator 與 Prometheus
- **容錯機制**：內建 Resilience4j 熔斷器與限流器
- **台灣在地化**：程式碼註解與文件使用繁體中文

## 技術棧

### 核心框架
- **Spring Boot 3.4.5** - 微服務基礎框架
- **Spring Cloud 2024.0.2** - 微服務生態系統
- **Spring Cloud Stream 4.x** - 訊息驅動微服務框架
- **Spring Data JPA** - 資料存取層抽象

### 訊息佇列與資料庫
- **RabbitMQ 3.7+** - 高可用訊息代理器
- **MariaDB** - 關聯式資料庫
- **Hibernate 6** - ORM 框架

### 開發工具與輔助
- **Lombok** - 減少樣板程式碼
- **Resilience4j** - 容錯與限流機制
- **Micrometer** - 應用程式監控指標
- **Maven** - 專案建置與依賴管理

## 專案架構

### 系統架構圖
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Customer      │    │  Waiter Service │    │ Barista Service │
│   (Client)      │    │                 │    │                 │
└─────────┬───────┘    └─────────┬───────┘    └─────────┬───────┘
          │                      │                      │
          │ 1. 建立訂單           │                      │
          ├─────────────────────►│                      │
          │                      │                      │
          │ 2. 支付訂單           │                      │
          ├─────────────────────►│                      │
          │                      │ 3. 發送製作訊息       │
          │                      ├─────────────────────►│
          │                      │                      │
          │                      │ 4. 完成製作通知       │
          │                      │◄─────────────────────┤
          │                      │                      │
          │ 5. 查詢訂單狀態       │                      │
          ├─────────────────────►│                      │
```

### 訊息流程
```
Customer → Waiter Service → RabbitMQ → Barista Service
    ↓           ↓              ↓            ↓
建立訂單 → 處理支付 → 發送製作訊息 → 製作咖啡
    ↓           ↓              ↓            ↓
查詢狀態 ← 更新狀態 ← 接收完成通知 ← 發送完成訊息
```

## 專案結構

```
Chapter 15 Spring Cloud Stream/
├── rabbitmq-waiter-service/          # 服務生服務
│   ├── src/main/java/
│   │   └── tw/fengqing/spring/springbucks/waiter/
│   │       ├── controller/           # REST API 控制器
│   │       │   ├── CoffeeController.java
│   │       │   └── CoffeeOrderController.java
│   │       ├── integration/          # 訊息整合層
│   │       │   ├── Barista.java      # 訊息通道定義
│   │       │   └── OrderListener.java # 訊息監聽器
│   │       ├── model/                # 資料模型
│   │       │   ├── Coffee.java
│   │       │   ├── CoffeeOrder.java
│   │       │   └── OrderState.java
│   │       ├── repository/           # 資料存取層
│   │       ├── service/              # 業務邏輯層
│   │       └── support/              # 支援類別
│   └── src/main/resources/
│       ├── application.properties    # 應用程式配置
│       ├── schema.sql               # 資料庫結構
│       └── data.sql                 # 初始資料
├── rabbitmq-barista-service/         # 咖啡師服務
│   ├── src/main/java/
│   │   └── tw/fengqing/spring/springbucks/barista/
│   │       ├── integration/          # 訊息整合層
│   │       │   ├── Waiter.java       # 訊息通道定義
│   │       │   └── OrderListener.java # 訊息監聽器
│   │       ├── model/                # 資料模型
│   │       └── repository/           # 資料存取層
│   └── src/main/resources/
│       └── application.properties    # 應用程式配置
└── README.md                         # 專案說明文件
```

## 快速開始

### 前置需求
- **Java 21+** - 開發環境
- **Maven 3.6+** - 專案建置工具
- **Docker** - 容器化部署
- **MariaDB** - 資料庫服務
- **RabbitMQ** - 訊息佇列服務

### 環境準備

1. **啟動 MariaDB 資料庫：**
```bash
docker run --name mariadb \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=springbucks \
  -e MYSQL_USER=springbucks \
  -e MYSQL_PASSWORD=springbucks \
  -p 3306:3306 \
  -d mariadb:latest
```

2. **啟動 RabbitMQ 服務：**
```bash
docker run --name rabbitmq \
  -e RABBITMQ_DEFAULT_USER=spring \
  -e RABBITMQ_DEFAULT_PASS=spring \
  -p 5672:5672 \
  -p 15672:15672 \
  -d rabbitmq:3.7-management
```

### 安裝與執行

1. **克隆此倉庫：**
```bash
git clone <repository-url>
cd "Chapter 15 Spring Cloud Stream"
```

2. **編譯 Waiter Service：**
```bash
cd rabbitmq-waiter-service
mvn clean compile
```

3. **編譯 Barista Service：**
```bash
cd ../rabbitmq-barista-service
mvn clean compile
```

4. **啟動服務：**

**終端機 1 - 啟動 Waiter Service：**
```bash
cd rabbitmq-waiter-service
mvn spring-boot:run
```

**終端機 2 - 啟動 Barista Service：**
```bash
cd rabbitmq-barista-service
mvn spring-boot:run
```

### 測試系統功能

1. **建立咖啡訂單：**
```bash
curl -X POST http://localhost:8080/order/ \
  -H "Content-Type: application/json" \
  -d '{
    "customer": "張三",
    "items": ["espresso", "latte"]
  }'
```

2. **支付訂單：**
```bash
curl -X PUT http://localhost:8080/order/1 \
  -H "Content-Type: application/json" \
  -d '{"state": "PAID"}'
```

3. **查詢訂單狀態：**
```bash
curl http://localhost:8080/order/1
```

## 進階說明

### 環境變數配置
```properties
# 資料庫連線設定
DB_HOST=localhost
DB_PORT=3306
DB_NAME=springbucks
DB_USER=springbucks
DB_PASSWORD=springbucks

# RabbitMQ 連線設定
RABBITMQ_HOST=localhost
RABBITMQ_PORT=5672
RABBITMQ_USER=spring
RABBITMQ_PASSWORD=spring
```

### Spring Cloud Stream 配置說明

**Waiter Service 配置：**
```properties
# 函數式編程模型配置
spring.cloud.function.definition=finishedOrders
spring.cloud.stream.bindings.finishedOrders-in-0.destination=finishedOrders
spring.cloud.stream.bindings.finishedOrders-in-0.group=waiter-service

# 輸出綁定配置
spring.cloud.stream.bindings.newOrders-out-0.destination=newOrders
```

**Barista Service 配置：**
```properties
# 函數式編程模型配置
spring.cloud.function.definition=newOrders
spring.cloud.stream.bindings.newOrders-in-0.destination=newOrders
spring.cloud.stream.bindings.newOrders-in-0.group=barista-service

# 輸出綁定配置
spring.cloud.stream.bindings.finishedOrders-out-0.destination=finishedOrders
```

### 重要程式碼說明

**訊息發送 (Waiter Service)：**
```java
/**
 * 更新訂單狀態並發送製作訊息
 * 當訂單狀態變更為 PAID 時，通知 Barista Service 開始製作
 */
public boolean updateState(CoffeeOrder order, OrderState state) {
    if (order == null) {
        log.warn("Order is not in valid status for update.");
        return false;
    }
    
    // 狀態機控制：只允許從 INIT 到 PAID 的轉換
    if (order.getState() != OrderState.INIT || state != OrderState.PAID) {
        log.warn("Order {} status is not valid for change from {} to {}",
                order.getId(), order.getState(), state);
        return false;
    }
    
    order.setState(state);
    orderRepository.save(order);
    
    // 發送製作訊息到 Barista Service
    if (state == OrderState.PAID) {
        Message<Long> message = MessageBuilder.withPayload(order.getId()).build();
        streamBridge.send(Barista.NEW_ORDERS, message);
        log.info("Sent order {} to barista for brewing.", order.getId());
    }
    
    return true;
}
```

**訊息接收 (Barista Service)：**
```java
/**
 * 處理新訂單的函數式 Bean
 * 接收訂單 ID，製作咖啡並發送完成通知
 */
@Bean
public Consumer<Long> newOrders() {
    return id -> {
        CoffeeOrder order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            log.warn("Order id {} is NOT valid.", id);
            return;
        }
        
        log.info("Receive a new Order {}. Waiter: {}. Customer: {}",
                id, order.getWaiter(), order.getCustomer());
        
        // 設置為製作中狀態並記錄咖啡師資訊
        order.setState(OrderState.BREWING);
        order.setBarista(barista);
        orderRepository.save(order);
        
        log.info("Order {} is READY.", id);
        
        // 發送完成訂單訊息回 Waiter Service
        Message<Long> message = MessageBuilder.withPayload(id).build();
        streamBridge.send(Waiter.FINISHED_ORDERS, message);
    };
}
```

## 監控與管理

### RabbitMQ 管理介面
- **URL**: http://localhost:15672
- **帳號**: spring
- **密碼**: spring

### Spring Boot Actuator 端點
- **健康檢查**: http://localhost:8080/actuator/health
- **應用程式資訊**: http://localhost:8080/actuator/info
- **指標監控**: http://localhost:8080/actuator/metrics

## 參考資源

- [Spring Cloud Stream 官方文件](https://spring.io/projects/spring-cloud-stream)
- [RabbitMQ 官方文件](https://www.rabbitmq.com/documentation.html)
- [Spring Boot 3.x 遷移指南](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide)
- [Spring Cloud Stream 函數式編程模型](https://docs.spring.io/spring-cloud-stream/docs/current/reference/html/spring-cloud-stream.html#spring-cloud-stream-overview-using-spring-cloud-stream)

## 注意事項與最佳實踐

### ⚠️ 重要提醒

| 項目 | 說明 | 建議做法 |
|------|------|----------|
| 訊息持久化 | 確保訊息不遺失 | 設定 `delivery-mode=persistent` |
| 消費者群組 | 避免重複消費 | 為每個服務設定唯一的 `group` |
| 資料庫連線 | 避免連線洩漏 | 使用連線池並正確關閉資源 |
| 錯誤處理 | 訊息處理失敗 | 實作重試機制與死信佇列 |

### 🔒 最佳實踐指南

- **訊息設計**：保持訊息結構簡單，避免過度複雜的物件序列化
- **錯誤處理**：實作完整的錯誤處理機制，包括重試與補償邏輯
- **監控告警**：設定關鍵指標的監控與告警機制
- **版本相容性**：注意 Spring Cloud Stream 4.x 函數式模型與舊版本的差異
- **效能調優**：根據實際負載調整訊息佇列與資料庫連線參數

### 常見問題排除

**Q: 訊息發送後沒有被消費？**
A: 檢查 RabbitMQ 管理介面中的 Exchange 和 Queue 綁定關係，確認消費者群組設定正確。

**Q: 資料庫連線失敗？**
A: 確認 MariaDB 服務已啟動，並檢查連線參數是否正確。

**Q: 服務啟動失敗？**
A: 檢查埠號是否被占用，確認所有依賴服務（RabbitMQ、MariaDB）都已正常運行。

## 授權說明

本專案採用 MIT 授權條款，詳見 LICENSE 檔案。

## 關於我們

我們主要專注在敏捷專案管理、物聯網（IoT）應用開發和領域驅動設計（DDD）。喜歡把先進技術和實務經驗結合，打造好用又靈活的軟體解決方案。

## 聯繫我們

- **FB 粉絲頁**：[風清雲談 | Facebook](https://www.facebook.com/profile.php?id=61576838896062)
- **LinkedIn**：[linkedin.com/in/chu-kuo-lung](https://www.linkedin.com/in/chu-kuo-lung)
- **YouTube 頻道**：[雲談風清 - YouTube](https://www.youtube.com/channel/UCXDqLTdCMiCJ1j8xGRfwEig)
- **風清雲談 部落格**：[風清雲談](https://blog.fengqing.tw/)
- **電子郵件**：[fengqing.tw@gmail.com](mailto:fengqing.tw@gmail.com)

---

**📅 最後更新：2025年9月**  
**👨‍💻 維護者：風清雲談團隊**
