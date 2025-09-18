package tw.fengqing.spring.springbucks.waiter.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * 訂單監聽器 - 處理完成訂單通知
 * 使用 Spring Cloud Stream 函數式編程模型
 */
@Component
@Slf4j
public class OrderListener {

    @Autowired
    private StreamBridge streamBridge;

    /**
     * 處理完成訂單的函數式 Bean
     * 接收完成訂單 ID 並通知客戶
     * @return 完成訂單處理函數
     */
    @Bean
    public Consumer<Long> finishedOrders() {
        return id -> {
            log.info("We've finished an order [{}].", id);
            
            // 發送通知給客戶
            Message<Long> message = MessageBuilder.withPayload(id)
                    .setHeader("customer", "Li Lei")
                    .build();
            log.info("Notify the customer: Li Lei");
            streamBridge.send("notifyOrders-out-0", message);
        };
    }
}