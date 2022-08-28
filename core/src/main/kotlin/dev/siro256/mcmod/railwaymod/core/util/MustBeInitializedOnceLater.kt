package dev.siro256.mcmod.railwaymod.core.util

import java.util.Optional

/**
 * Data holder class that can be initialized only once, like a combination of lateinit and val.
 */
class MustBeInitializedOnceLater<T: Any> {
    private var holder = Optional.empty<T>()

    /**
     * Set data to the holder.
     * If the data holder has already been initialized, this throws [IllegalStateException].
     *
     * @param value The value to set
     * @throws IllegalStateException This meaning the data holder has already been initialized.
     */
    @Throws(IllegalStateException::class)
    fun set(value: T) {
        if (!this.holder.isEmpty) throw IllegalStateException("The value has already been initialized.")
        this.holder = Optional.of(value)
    }

    /**
     * Get data from data holder.
     * If the data holder isn't initialized, this throws [IllegalStateException]
     *
     * @throws IllegalStateException This meaning the data holder is not initialized.
     */
    @Throws(IllegalStateException::class)
    fun get(): T = holder.orElseThrow { IllegalStateException("The value is not initialized.") }
}
