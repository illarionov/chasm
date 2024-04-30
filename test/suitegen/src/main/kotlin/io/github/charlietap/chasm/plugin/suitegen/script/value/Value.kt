package io.github.charlietap.chasm.plugin.suitegen.script.value

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface Value {

    @JvmInline
    @Serializable
    @SerialName("i32")
    value class I32(val value: Int) : Value

    @JvmInline
    @Serializable
    @SerialName("i64")
    value class I64(val value: Long) : Value

    @JvmInline
    @Serializable
    @SerialName("f32")
    value class F32(val value: Float) : Value

    @JvmInline
    @Serializable
    @SerialName("f64")
    value class F64(val value: Double) : Value

    @Serializable
    @SerialName("externref")
    data object ExternRef: Value

    @Serializable
    @SerialName("funcref")
    data object FuncRef: Value
}
