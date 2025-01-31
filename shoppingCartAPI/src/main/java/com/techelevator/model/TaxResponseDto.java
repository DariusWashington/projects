package com.techelevator.model;
import java.math.BigDecimal;

    public class TaxResponseDto {
        private BigDecimal salesTax;

        public BigDecimal getSalesTax() {
            return salesTax;
        }
        public void setSalesTax(BigDecimal salesTax) {
            this.salesTax = salesTax;
        }

    @Override
    public String toString() {
        return "TaxResponseDto{" +
                "salesTax=" + salesTax +
                '}';
    }
}