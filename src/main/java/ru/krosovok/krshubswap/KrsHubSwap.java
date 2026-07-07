package ru.krosovok.krshubswap;

import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonProperties;

/**
 * Главный класс аддона krsHubSwap для Labymod 3
 */
@AddonProperties(
    namespace = "krshubswap",
    name = "krsHubSwap",
    author = "krosov_ok",
    version = "1.0.0",
    description = "Быстрое телепортирование на лайт анархии"
)
public class KrsHubSwap extends LabyAddon {

    @Override
    public void load() {
        // Инициализация аддона
    }

    @Override
    public void unload() {
        // Очистка при выгрузке
    }
}
