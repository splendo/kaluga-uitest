package com.splendo.kaluga.uitest

sealed class ViewType {
    abstract val index: Int

    class Images(override val index: Int = 0) : ViewType()
    class Text(override val index: Int = 0) : ViewType()
    class TextField(override val index: Int = 0) : ViewType()
    class SecureTextField(override val index: Int = 0) : ViewType()
    class Button(override val index: Int = 0) : ViewType()
    class Any(override val index: Int = 0) : ViewType()
}