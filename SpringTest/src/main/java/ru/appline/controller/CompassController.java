package ru.appline.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CompassController {

    private Map<String, String> sides = new HashMap<>();

    // POST-запрос диапазонов градусов
    @PostMapping("/setSides")
    public ResponseEntity<String> setSides(@RequestBody Map<String, String> sides) {
        this.sides = sides;
        return ResponseEntity.ok("Диапазоны градусов успешно установлены.");
    }

    // GET-запрос для определения стороны света по градусу
    @GetMapping("/getSide")
    public ResponseEntity<Map<String, String>> getSide(@RequestBody Map<String, Integer> request) {
        int degree = request.get("degree");
        String side = "";
        for (Map.Entry<String, String> entry : sides.entrySet()) {
            String[] range = entry.getValue().split("-");
            int start = Integer.parseInt(range[0]);
            int end = Integer.parseInt(range[1]);
            if ((degree >= start && degree <= 359) || (degree >= 0 && degree <= end)) {
                side = entry.getKey();
                break;
            }
        }
        Map<String, String> response = new HashMap<>();
        response.put("Side", side);
        return ResponseEntity.ok(response);
    }
}
