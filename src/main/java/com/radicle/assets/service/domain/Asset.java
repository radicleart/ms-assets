package com.radicle.assets.service.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.radicle.assets.api.model.UserInvoiceModel;

public class Asset implements Serializable {
	private static final long serialVersionUID = 6349634068001422092L;
	public static final String BTC_METHOD = "bitcoin";
	public static final String LND_METHOD = "lightning";
	@Id public String id;
	private long created;
	private long updated;
	private ClientData clientData = new ClientData();
	private String paymentId;
	private int status;
	private String txid;
	private Integer confirmations = 0;
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
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBitcoinAddress() {
		return bitcoinAddress;
	}

	public void setBitcoinAddress(String bitcoinAddress) {
		this.bitcoinAddress = bitcoinAddress;
	}

	public String getPaymentRequest() {
		return paymentRequest;
	}

	public void setPaymentRequest(String paymentRequest) {
		this.paymentRequest = paymentRequest;
	}

	public void setSettled(boolean settled) {
		this.settled = settled;
	}

	public boolean isSettled() {
		return settled;
	}

	public String getMethod() {
		return method;
	}

	public boolean isBitcoinMethod() {
		return this.method != null && this.method.equals(BTC_METHOD);
	}

	public boolean isLightningMethod() {
		return this.method != null && this.method.equals(LND_METHOD);
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getTxid() {
		return txid;
	}

	public void setTxid(String txid) {
		this.txid = txid;
	}

	public Integer getConfirmations() {
		return confirmations;
	}

	public void setConfirmations(Integer confirmations) {
		this.confirmations = confirmations;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public long getUpdated() {
		return updated;
	}

	public void setUpdated(long updated) {
		this.updated = updated;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public Float getBitcoinAmount() {
		return bitcoinAmount;
	}

	public void setBitcoinAmount(Float bitcoinAmount) {
		this.bitcoinAmount = bitcoinAmount;
	}

	public String getPaymentHash() {
		return paymentHash;
	}

	public void setPaymentHash(String paymentHash) {
		this.paymentHash = paymentHash;
	}

	public ClientData getClientData() {
		return clientData;
	}

	public void setClientData(ClientData clientData) {
		this.clientData = clientData;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", created=" + created + ", updated=" + updated + ", clientData=" + clientData + ", paymentId=" + paymentId + ", status=" + status + ", txid=" + txid
				+ ", confirmations=" + confirmations + ", paymentRequest=" + paymentRequest + ", paymentHash=" + paymentHash + ", method=" + method + ", settled=" + settled
				+ ", bitcoinAddress=" + bitcoinAddress + ", bitcoinAmount=" + bitcoinAmount + "]";
	}

	public Long getInvoiceExpiry() {
		return invoiceExpiry;
	}

	public void setInvoiceExpiry(Long invoiceExpiry) {
		Long expires = System.currentTimeMillis() + (invoiceExpiry * 1000L);
		this.invoiceExpiry = expires;
	}
}
