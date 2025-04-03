package teddyx.neapolitans.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import teddyx.neapolitans.Neapolitans;
import teddyx.neapolitans.network.BlockPosPayload;

public class ModScreenHandlers {
    public static final ScreenHandlerType<NeapolitanPressScreenHandler> NEAPOLITAN_PRESS_SCREEN_HANDLER =
            register("neapolitan_press", NeapolitanPressScreenHandler::new, BlockPosPayload.PACKET_CODEC);

    public static <T extends ScreenHandler, D extends CustomPayload> ExtendedScreenHandlerType<T, D> register(String name,
                                                                                                              ExtendedScreenHandlerType.ExtendedFactory<T, D> factory,
                                                                                                              PacketCodec<? super RegistryByteBuf, D> codec) {
        return Registry.register(Registries.SCREEN_HANDLER, Neapolitans.id(name), new ExtendedScreenHandlerType<>(factory, codec));
    }
}
