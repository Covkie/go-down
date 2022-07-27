package net.liquidwarpmc.godown;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GoDown implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("GoDown");
	public static final Identifier GODOWN_IDENTIFIER = new Identifier("godown:identifier");

	public static class Shared {
		public static final TrackedData<Boolean> CRAWLING_REQUEST = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	}

	@Override
	public void onInitialize() {
		ServerPlayNetworking.registerGlobalReceiver(GODOWN_IDENTIFIER, (server, player, handler, buf, responseSender) -> {
			boolean val = buf.readBoolean();
			server.execute(() -> player.getDataTracker().set(Shared.CRAWLING_REQUEST, val));
		});
		LOGGER.info("[Go Down] initialized!");
	}
}
