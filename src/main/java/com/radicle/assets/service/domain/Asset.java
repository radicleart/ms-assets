package com.radicle.assets.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.radicle.assets.api.model.UserInvoiceModel;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@TypeAlias(value = "Asset")
public class Asset {

	public static final String BTC_METHOD = "bitcoin";
	public static final String LND_METHOD = "lightning";
	@Id public String id;
	private long created;
	private long updated;
	@Builder.Default private ClientData clientData = new ClientData();
	private String paymentId;
	private int status;
	private String assetHash;
	private String txid;
	private Integer confirmations;
	private String paymentRequest;
	private String paymentHash;
	private Long invoiceExpiry;
	private String method;
	private boolean settled;
	private String bitcoinAddress;
	private Float bitcoinAmount;

	public Asset(String paymentRequest, String paymentHash) {
		super();
		this.paymentRequest = paymentRequest;
	}

	public Asset() {
		super();
		status = AssetLifecycleEnum.NEW.getStatus();
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
