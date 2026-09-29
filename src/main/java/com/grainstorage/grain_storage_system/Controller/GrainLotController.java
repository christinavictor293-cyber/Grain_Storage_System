package com.grainstorage.grain_storage_system.Controller;

import com.grainstorage.grain_storage_system.Entity.GrainLot;
import com.grainstorage.grain_storage_system.Service.GrainLotService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grain-lots")
public class GrainLotController {

    private final GrainLotService grainLotService;

    public GrainLotController(GrainLotService grainLotService) {
        this.grainLotService = grainLotService;
    }

    @PostMapping
    public GrainLot addGrainLot(@RequestBody GrainLot grainLot) {
        return grainLotService.addGrainLot(grainLot);
    }

    @GetMapping
    public List<GrainLot> getAllGrainLots() {
        return grainLotService.getAllGrainLots();
    }

    @GetMapping("/warehouse/{warehouseId}")
    public List<GrainLot> getGrainLotsByWarehouse(@PathVariable Long warehouseId) {
        return grainLotService.getGrainLotsByWarehouse(warehouseId);
    }
}
