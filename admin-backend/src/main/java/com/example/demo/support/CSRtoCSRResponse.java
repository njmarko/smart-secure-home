package com.example.demo.support;

import com.example.demo.dto.CsrDTO;
import com.example.demo.model.CSR;
import org.springframework.stereotype.Component;

@Component
public class CSRtoCSRResponse extends BaseConverter<CSR, CsrDTO> {

    @Override
    public CsrDTO convert(CSR source) {
        return this.getModelMapper().map(source, CsrDTO.class);
    }
}
