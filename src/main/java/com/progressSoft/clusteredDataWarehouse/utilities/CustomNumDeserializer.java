package com.progressSoft.clusteredDataWarehouse.utilities;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.util.NumberUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import java.io.IOException;
import java.math.BigDecimal;

public class CustomNumDeserializer extends JsonDeserializer<Number> {

    @Override
    public Number deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getValueAsString();
        if (isValidNum(value)) {
            return switch (p.getCurrentName()) {
                case "dealId" -> Long.parseLong(value);
                case "dealAmount" -> new BigDecimal(value);
                default -> null;
            };
        } else {
            addValidationError(ctxt, p.currentName(), "Invalid Number: " + value);
            return null;
        }
    }

    private boolean isValidNum(String value) {
        // Implement your custom validation logic here
        try {
            NumberUtils.parseNumber(value, Number.class);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void addValidationError(DeserializationContext ctxt, String fieldName, String errorMessage) {
        Errors errors = (Errors) ctxt.getAttribute(BindingResult.MODEL_KEY_PREFIX + "DealDTO");
        if (errors != null) {
            errors.rejectValue(fieldName, null, errorMessage);
        }
    }

}
