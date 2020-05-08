package com.radicle.assets.service.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.radicle.assets.api.model.UserInvoiceModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@TypeAlias(value = "Payment")
public class Payment {

	public static final String BTC_METHOD = "bitcoin";
	public static final String LND_METHOD = "lightning";
	@Id public String id;
	private long created;
	private long updated;
	@Builder.Default private ClientData clientData = new ClientData();
	private String paymentId;
	private int status;
	private String txid;
	private Integer confirmations;
	private String paymentRequest;
	private String paymentHash;
	private Long invoiceExpiry;
	private String method;
	private boolean settled;
	private String bitcoinAddress;
	private Float bitcoinAmount;

	public Payment(String paymentRequest, String paymentHash) {
		super();
		this.paymentRequest = paymentRequest;
	}

	public Payment() {
		super();
		status = PaymentLifecycleEnum.NEW.getStatus();
	}

	@JsonIgnore
	public UserInvoiceModel getUserInvoiceModel() {
		UserInvoiceModel uim = new UserInvoiceModel();
		Float scaledAmount = Float.valueOf(bitcoinAmount * 100000000.0f);
		uim.setAmount((scaledAmount).longValue());
		uim.setMemo(this.getPaymentId());
		return uim;
	}
	
	@JsonIgnore
	public boolean isExpired() {
		if (this.invoiceExpiry == null || this.invoiceExpiry < System.currentTimeMillis()) {
			return true;
		}
		return false;
	}
	
	public boolean addAssets(List<String> assetHashes) {
		if (this.clientData == null) {
			this.clientData = new ClientData();
		}
		return this.clientData.addAssets(assetHashes);
	}

	
	public boolean isBitcoinMethod() {
		return this.method != null && this.method.equals(BTC_METHOD);
	}

	public boolean isLightningMethod() {
		return this.method != null && this.method.equals(LND_METHOD);
	}

	public void setInvoiceExpiry(Long invoiceExpiry) {
		Long expires = System.currentTimeMillis() + (invoiceExpiry * 1000L);
		this.invoiceExpiry = expires;
	}
}
