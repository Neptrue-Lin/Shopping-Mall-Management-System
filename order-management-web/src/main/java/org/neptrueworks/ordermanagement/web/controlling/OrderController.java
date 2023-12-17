package org.neptrueworks.ordermanagement.web.controlling;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.neptrueworks.ordermanagement.core.servicing.OrderServiceable;
import org.neptrueworks.ordermanagement.data.entitizing.OrderManifestEntity;
import org.neptrueworks.ordermanagement.web.modelling.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class OrderController {
    private final OrderServiceable service;

    private final ObjectMapper serializer;

    public OrderController(@Qualifier("orderService") @Autowired OrderServiceable service, @Autowired ObjectMapper serializer) {
        this.service = service;
        this.serializer = serializer;
    }

    @GetMapping("/ordering/manifests/{pageIndex}/{pageSize}")
    public void retrieveOrderManifests(@PathVariable("pageIndex") Integer pageIndex,
                                       @PathVariable("pageSize") Integer pageSize,
                                       HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.getWriter().print(this.serializer.writeValueAsString(this.service.getLimitedOrderManifests(pageIndex, pageSize)));
    }

    @GetMapping("/ordering/manifests/{pageSize}")
    public void paginateOrderManifests(@PathVariable("pageSize") Integer pageSize,
                                       HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println((int) Math.ceil(this.service.countAllOrderManifests() * 1.0 / pageSize));
    }

    @GetMapping("/ordering/items/{orderId}")
    public void listOrderManifests(@PathVariable("orderId") Integer orderId,
                                   HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.getWriter().println(this.serializer.writeValueAsString(this.service.getOrderItems(orderId)));
    }

    @PostMapping("/ordering/manifest/place")
    public void placeOrder(@RequestBody OrderManifestPlacementViewModel model,
                           HttpServletRequest request, HttpServletResponse response) {
        OrderManifestEntity entity = new OrderManifestEntity();
        entity.setCreatedBy(model.getPlacedBy());
        this.service.placeOrder(entity, model.getItems());
    }

    @PutMapping("/ordering/manifest/cancel")
    public void cancelOrder(@RequestBody OrderManifestCancellationViewModel model,
                            HttpServletRequest request, HttpServletResponse response) {
        OrderManifestEntity entity = new OrderManifestEntity();
        entity.setId(model.getId());
        entity.setCreatedBy(model.getCancelledBy());
        this.service.cancelOrder(entity);
    }

    @PutMapping("/ordering/manifest/resume")
    public void resumeOrder(@RequestBody OrderManifestResumptionViewModel model,
                            HttpServletRequest request, HttpServletResponse response) {
        OrderManifestEntity entity = new OrderManifestEntity();
        entity.setId(model.getId());
        entity.setCreatedBy(model.getResumedBy());
        this.service.resumeOrder(entity);
    }
}