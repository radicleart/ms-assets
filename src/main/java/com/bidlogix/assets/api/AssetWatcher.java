package com.bidlogix.assets.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.bidlogix.assets.service.AssetService;
import com.bidlogix.assets.service.domain.Asset;
import com.bidlogix.assets.service.domain.AssetLifecycleEnum;
import com.fasterxml.jackson.core.JsonProcessingException;

@Configuration
@EnableAsync
@EnableScheduling
public class AssetWatcher {

	@Autowired private SimpMessagingTemplate simpMessagingTemplate;
	@Autowired private AssetService assetService;

	@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8081", "https://asset-flow.com", "https://test.asset-flow.com" }, maxAge = 6000)
	@Scheduled(fixedDelay=60000)
	public void watchForConfirmedLoop() throws JsonProcessingException {
		List<Asset> assets = assetService.findAssetsByStatus(AssetLifecycleEnum.BUYER_TX_CONFIRMING.getStatus());
	    // tell current user
		simpMessagingTemplate.convertAndSend("/queue/mynews-assets", assets);
	    // tell everyone subscribed to 'news'
	    simpMessagingTemplate.convertAndSend("/topic/news", assets);
	}
}
