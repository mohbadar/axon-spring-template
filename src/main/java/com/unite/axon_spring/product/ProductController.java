package com.unite.axon_spring.product;

import com.unite.axon_spring.product.command.AddProductCommand;
import com.unite.axon_spring.product.command.DeleteProductCommand;
import com.unite.axon_spring.product.command.UpdateProductCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {


    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @Autowired
    public ProductController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping("/add")
    public HttpStatus addProduct(@RequestBody ProductSummary productSummary){
        productSummary.setId(UUID.randomUUID().toString());

        commandGateway.send(new AddProductCommand(productSummary.getId(), productSummary.getPrice(), productSummary.getStock(), productSummary.getDescription()));
        return HttpStatus.ACCEPTED;
    }

    @PutMapping("/update")
    public HttpStatus updateProduct(@RequestBody ProductSummary productSummary){
        commandGateway.send(new UpdateProductCommand(productSummary.getId(), productSummary.getPrice(), productSummary.getStock(), productSummary.getDescription()));
        return HttpStatus.ACCEPTED;
    }


    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteProduct(@PathVariable String id){
        commandGateway.send(new DeleteProductCommand(id));
        return HttpStatus.ACCEPTED;
    }
}
