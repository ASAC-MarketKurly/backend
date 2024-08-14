package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private static final String DATA_FILE_PATH = "data.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private Map<String, Object> readData() throws IOException {
        File jsonFile = new File(DATA_FILE_PATH);
        return objectMapper.readValue(jsonFile, Map.class);
    }

    
    @GetMapping("/main/api/closing-sale")
    public Map<String, Object> getClosingSaleItems() throws IOException {
        Map<String, Object> data = readData();
        List<Map<String, Object>> items = (List<Map<String, Object>>) data.get("ItemsList");

        List<Map<String, Object>> closingSaleItems = items.stream()
                .filter(item -> (int) item.get("discountRate") >= 30)
                .sorted((item1, item2) -> Integer.compare((int) item2.get("discountRate"),
                        (int) item1.get("discountRate")))
                .collect(Collectors.toList());

        return Map.of("closingSaleItems", closingSaleItems, "totalCount", closingSaleItems.size());
    }

    
    @GetMapping("/main/api/main-slider")
    public Map<String, Object> getMainBannerSlider() throws IOException {
        Map<String, Object> data = readData();
        List<Map<String, Object>> mainBannerSlider = (List<Map<String, Object>>) data.get("MainBannerSlider");

        return Map.of("MainBannerSlider", mainBannerSlider);
    }

    
    @GetMapping("/main/api/weekend-special")
    public Map<String, Object> getWeekendSpecialItems() throws IOException {
        Map<String, Object> data = readData();
        List<Map<String, Object>> items = (List<Map<String, Object>>) data.get("ItemsList");

        List<Map<String, Object>> weekendSpecialItems = items.stream()
                .filter(item -> (boolean) item.get("isWeekendSpecial"))
                .collect(Collectors.toList());

        return Map.of("weekendSpecialItems", weekendSpecialItems, "totalCount", weekendSpecialItems.size());
    }

    
    @GetMapping("/main/api/lowest-price")
public Map<String, Object> getLowestPriceItems() throws IOException {
    Map<String, Object> data = readData();
    List<Map<String, Object>> items = (List<Map<String, Object>>) data.get("ItemsList");

    // 전체 항목을 콘솔에 출력
    items.forEach(item -> System.out.println(item));

    List<Map<String, Object>> lowestPriceItems = items.stream()
            .filter(item -> (boolean) item.get("isLowestPrice"))
            .collect(Collectors.toList());


    return Map.of("lowestPriceItems", lowestPriceItems, "totalCount", lowestPriceItems.size());
}

    
    @GetMapping("/main/api/approved-new-products")
    public Map<String, Object> getApprovedNewProducts() throws IOException {
        Map<String, Object> data = readData();
        List<Map<String, Object>> items = (List<Map<String, Object>>) data.get("ItemsList");

        List<Map<String, Object>> approvedNewProducts = items.stream()
                .filter(item -> (boolean) item.get("isBestSeller") && (boolean) item.get("isNew"))
                .collect(Collectors.toList());

        return Map.of("approvedNewProducts", approvedNewProducts, "totalCount", approvedNewProducts.size());
    }
}
