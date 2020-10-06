package com.minesweeper.api.config;

import com.minesweeper.api.model.CellState;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CellStateConverter implements Converter<String, CellState> {

    @Override
    public CellState convert(String value) {
        return CellState.valueOf(value.toUpperCase());
    }
}