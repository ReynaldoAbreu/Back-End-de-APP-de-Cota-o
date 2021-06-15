package com.bootcamp.bootcamp.service;

import com.bootcamp.bootcamp.exception.BusinessException;
import com.bootcamp.bootcamp.exception.NotFoundException;
import com.bootcamp.bootcamp.mapper.StockMapper;
import com.bootcamp.bootcamp.model.Stock;
import com.bootcamp.bootcamp.model.dto.StockDto;
import com.bootcamp.bootcamp.repository.StockRepository;
import com.bootcamp.bootcamp.utill.MessageUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class StockService {

    @Autowired
    private StockRepository repository;

    @Autowired
    private StockMapper mapper;

    @Transactional
    public StockDto save(StockDto dto) {
        Optional<Stock> optionalStock = repository.findByNameAndDate(dto.getName(), dto.getDate());
        if(optionalStock.isPresent()){
            throw new BusinessException(MessageUtill.STOCK_ALREADY_EXISTS);
        }

        Stock stock = mapper.toEntity(dto);
        repository.save(stock);
        return mapper.toDto(stock);
    }
    @Transactional
    public StockDto update(StockDto dto) {
        Optional<Stock> optionalStock = repository.findByStockUpdate(dto.getName(), dto.getDate(), dto.getId());
        if(optionalStock.isPresent()) {
            throw new BusinessException(MessageUtill.STOCK_ALREADY_EXISTS);
        }

        Stock stock = mapper.toEntity(dto);
        repository.save(stock);
        return mapper.toDto(stock);
    }

    @Transactional
    public StockDto delete(Long id) {
        StockDto dto = this.finById(id);
        repository.deleteById(id);
        return dto;
    }
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<StockDto> findAll() {
        return mapper.toDto(repository.findAll());
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public StockDto finById(Long id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow(NotFoundException::new);
    }


    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<StockDto> finByToday() {
        return repository.findByToday(LocalDate.now()).map(mapper::toDto).orElseThrow(NotFoundException::new);

    }
}
