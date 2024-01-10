package org.neptrueworks.ordermanagement.web.controlling;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.neptrueworks.ordermanagement.core.servicing.OrderServiceable;
import org.neptrueworks.ordermanagement.data.entitizing.OrderItemEntity;
import org.neptrueworks.ordermanagement.data.entitizing.OrderManifestEntity;
import org.neptrueworks.ordermanagement.web.modelling.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ordering")
public class OrderController {
    private final OrderServiceable service;

    @GetMapping("/manifests/{manifestId}")
    public OrderManifestEntity identifyOrderManifest(@PathVariable Integer manifestId) {
        return this.service.identifyOrderManifest(manifestId);
    }

    @GetMapping("/items/{itemId}")
    public OrderItemEntity identifyOrderItem(@PathVariable Integer itemId) {
        return this.service.identifyOrderItem(itemId);
    }

    @GetMapping("/manifests/retrieve")
    public Iterable<OrderManifestEntity> retrieveOrderManifests(Integer pageIndex, Integer pageSize) {
        return this.service.getPagedOrderManifests(
                pageIndex, pageSize);
    }

    @GetMapping("/manifests/paginate")
    public Integer paginateOrderManifests(Integer pageSize) {
        long totalCount = this.service.countAllOrderManifests();
        return (int) Math.ceil(totalCount * 1.0 / pageSize);
    }

    @GetMapping("/items/list")
    public Iterable<OrderItemEntity> listOrderItems(Integer manifestId) {
        return this.service.findOrderItems(manifestId);
    }

    @PostMapping("/manifest/place")
    public void placeOrder(@RequestBody OrderManifestPlacementViewModel model) {
        OrderManifestEntity entity = new OrderManifestEntity();
        entity.setCreatedBy(model.getPlacedBy());
        this.service.placeOrder(entity, model.getItems());
    }

    @PostMapping("/manifest/cancel")
    public void cancelOrder(@RequestBody OrderManifestCancellationViewModel model,
                            HttpServletRequest request, HttpServletResponse response) {
        this.service.cancelOrder(model.getId());
    }

    @PostMapping("/manifest/resume")
    public void resumeOrder(@RequestBody OrderManifestResumptionViewModel model,
                            HttpServletRequest request, HttpServletResponse response) {
        this.service.resumeOrder(model.getId());
    }

    @PostMapping("/item/remove")
    public void cancelOrder(@RequestBody OrderItemCancellationViewModel model,
                            HttpServletRequest request, HttpServletResponse response) {
        this.service.removeItem(model.getId());
    }

    @PostMapping("/item/restore")
    public void resumeOrder(@RequestBody OrderItemRestorationViewModel model,
                            HttpServletRequest request, HttpServletResponse response) {
        this.service.resumeOrder(model.getId());
    }
}