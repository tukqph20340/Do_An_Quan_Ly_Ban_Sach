package WebProject.WebProject.controller;

import WebProject.WebProject.repository.ThongKeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ThongKeController {

    @Autowired
    private ThongKeRepository thongKeRepository;



    @GetMapping("/api/revenue-by-date")
    public List<Map<String, Object>> getRevenueByDate() {
        return thongKeRepository.getTotalRevenueByDate();
    }

    @GetMapping("/api/revenue-by-payment")
    public ResponseEntity<List<Map<String, Object>>> getRevenueByDatePay() {
        try {
            List<Map<String, Object>> result = thongKeRepository.getTotalRevenueByDatePayMent();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/api/revenue-by-payment-thang")
    public ResponseEntity<List<Map<String, Object>>> getRevenueByDatePayThang() {
        try {
            List<Map<String, Object>> result = thongKeRepository.getTotalRevenueByDatePayMentThang();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/api/revenue-by-payment-nam")
    public ResponseEntity<List<Map<String, Object>>> getRevenueByDatePayNam() {
        try {
            List<Map<String, Object>> result = thongKeRepository.getTotalRevenueByDatePayMentNam();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @GetMapping("/count-by-status-and-date")
//    public ResponseEntity<List<Map<String, Object>>> getCountByStatusAndDate() {
//        List<Map<String, Object>> result = thongKeRepository.getCountByStatusAndDate();
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//    @GetMapping("/api/chart")
//    @ResponseBody
//    public ResponseEntity<List<Object[]>> getChartData() {
//        try {
//            List<Map<String, Object>> listThongKeSoLuongAoDTOS = thongKeProductRepository.thongKeProduct();
//
//            List<Object[]> resultList = listThongKeSoLuongAoDTOS.stream()
//                    .map(map -> new Object[]{map.get("ten"), map.get("sLTon"), map.get("slBan")})
//                    .collect(Collectors.toList());
//
//            return ResponseEntity.ok(resultList);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

//    @GetMapping("/api/countByActiveAndDate")
//    public ResponseEntity<List<Map<String, Object>>> getCountByStatusAndDate() {
//        try {
//            List<Map<String, Object>> result = thongKeRepository.getCountByStatusAndDate();
//            return ResponseEntity.ok(result);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
}