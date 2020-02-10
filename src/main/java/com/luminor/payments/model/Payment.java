package com.luminor.payments.model;

import com.luminor.payments.validator.EnumValidator;
import com.luminor.payments.validator.PaymentValidator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="payment")
@PaymentValidator
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;

    @NotEmpty(message = "Payment is mandatory")
    @EnumValidator(
            enumClazz = PaymentType.class,
            message = "The payment type should be TYPE1 or TYPE2 or TYPE3"
    )
    private String paymentType;

    @NotEmpty(message = "Currency is mandatory")
    @EnumValidator(
            enumClazz = Currency.class,
            message = "The Currency should be EUR or USD"
    )
    private String currency;

    @Min(value = 0, message = "The amount must be positive")
    private BigDecimal amount;

    @NotBlank(message = "Debtor Iban is mandatory")
    private String debtorIban;

    @NotBlank(message = "Creditor Iban is mandatory")
    private String creditorIban;

    private String bic;

    private LocalDateTime paymentDate;

    private boolean isPaymentCancelled;

    private String details;

    private BigDecimal cancellationFee;

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDebtorIban() {
        return debtorIban;
    }

    public void setDebtorIban(String debtorIban) {
        this.debtorIban = debtorIban;
    }

    public String getCreditorIban() {
        return creditorIban;
    }

    public void setCreditorIban(String creditorIban) {
        this.creditorIban = creditorIban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public boolean isPaymentCancelled() {
        return isPaymentCancelled;
    }

    public void setPaymentCancelled(boolean paymentCancelled) {
        isPaymentCancelled = paymentCancelled;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public BigDecimal getCancellationFee() {
        return cancellationFee;
    }

    public void setCancellationFee(BigDecimal cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

}
