package com.example.demo.support;

import com.example.demo.dto.CsrDTO;
import com.example.demo.model.CSR;
import org.springframework.stereotype.Component;

@Component
public class CSRRequestToCSR extends BaseConverter<CsrDTO, CSR> {

    @Override
    public CSR convert(CsrDTO source) {
        return this.getModelMapper().map(source, CSR.class);
    }
}
