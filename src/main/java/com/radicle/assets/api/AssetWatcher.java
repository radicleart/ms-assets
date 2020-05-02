package com.radicle.assets.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.radicle.assets.service.AssetService;
import com.radicle.assets.service.domain.Asset;
import com.radicle.assets.service.domain.AssetLifecycleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableAsync
@EnableScheduling
public class AssetWatcher {

	@Autowired private SimpMessagingTemplate simpMessagingTemplate;
	@Autowired private AssetService assetService;

	@Scheduled(fixedDelay=60000)
	public void watchForConfirmedLoop() throws JsonProcessingException {
		List<Asset> assets = assetService.findAssetsByStatus(AssetLifecycleEnum.BUYER_TX_CONFIRMING.getStatus());
	    // tell current user
		simpMessagingTemplate.convertAndSend("/queue/mynews-assets", assets);
	    // tell everyone subscribed to 'news'
	    simpMessagingTemplate.convertAndSend("/topic/news", assets);
	}
}
