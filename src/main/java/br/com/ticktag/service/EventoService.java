package br.com.ticktag.service;

import br.com.ticktag.domain.EventoVO;
import br.com.ticktag.dto.EventoFilterDTO;
import br.com.ticktag.util.ApiResponse;

import br.com.ticktag.util.ExampleMatcherUtil;
import br.com.ticktag.util.ImagemUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface EventoService {
    ApiResponse<List<EventoVO>> findAll();

    ApiResponse<EventoVO> findById(Long id);

    ApiResponse<EventoVO> save(EventoVO vo);

    ApiResponse<EventoVO> update(EventoVO vo);

    ApiResponse<String> deleteById(Long id);

    ApiResponse<List<EventoVO>> findByParams(EventoFilterDTO filter);
}
