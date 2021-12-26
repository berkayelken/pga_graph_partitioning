package com.berkay.yelken.parallel.ga.rest;

import static org.springframework.http.ResponseEntity.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.berkay.yelken.parallel.ga.model.request.RequestModel;
import com.berkay.yelken.parallel.ga.model.response.ResponseModel;
import com.berkay.yelken.parallel.ga.service.GraphPartitioningService;

@RestController
@RequestMapping("/api/genetic/partitioning")
public class GeneticController {
	@Autowired
	private GraphPartitioningService service;

	@PostMapping
	@CrossOrigin
	public ResponseEntity<ResponseModel> getGraphPartitioning(@RequestBody RequestModel req) {
		return ok(service.doPartitioning(req));
	}
}
