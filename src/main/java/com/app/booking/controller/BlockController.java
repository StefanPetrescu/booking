package com.app.booking.controller;

import com.app.booking.model.Block;
import com.app.booking.service.BlockService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.app.booking.util.log.LoggingUtil.I10004;
import static com.app.booking.util.log.LoggingUtil.I10005;
import static com.app.booking.util.log.LoggingUtil.I10006;

@RestController
@RequestMapping("/block")
public class BlockController {
    private static final Logger LOGGER = LogManager.getLogger(BlockController.class);
    private final BlockService blockService;

    public BlockController(BlockService blockService) {
        this.blockService = blockService;
    }

    @PostMapping()
    ResponseEntity<Block> create(@Valid @RequestBody Block block) {
        this.blockService.create(block);
        LOGGER.info(I10004.getMessage(), block.toString());
        return new ResponseEntity<>(block, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Block> delete(@PathVariable Long id) {
        this.blockService.delete(id);
        LOGGER.info(I10005.getMessage(), id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Block> update(@Valid @RequestBody Block block, @PathVariable Long id) {
        Block blockUpdated = this.blockService.update(block, id);
        LOGGER.info(I10006.getMessage(), block.toString());
        return new ResponseEntity<>(blockUpdated, HttpStatus.OK);
    }
}
