package com.example.demo.support;

import com.example.demo.dto.ReadRealEstateResponse;
import com.example.demo.model.RealEstate;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class RealEstateToReadRealEstateResponse extends BaseConverter<RealEstate, ReadRealEstateResponse> {
    @Override
    public ReadRealEstateResponse convert(@NonNull RealEstate source) {
        return getModelMapper().map(source, ReadRealEstateResponse.class);
    }
}
