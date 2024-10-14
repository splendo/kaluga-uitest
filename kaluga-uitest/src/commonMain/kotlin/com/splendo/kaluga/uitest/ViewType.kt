package com.splendo.kaluga.uitest

/**
 * This is used to specify mostly on iOS the view type of an element.
 */
sealed class ViewType {
    /**
     * If two or more element have the same tag and type, the index should be passed that represents
     * the index of the ui element on the view tree.
     */
    abstract val index: Int

    class Images(override val index: Int = 0) : ViewType()
    sealed class Text : ViewType() {
        class TextView(override val index: Int = 0) : Text()

        /**
         * In iOS to represents fixed text.
         * Especially in WebViews this can also represent buttons
         */
        class StaticText(override val index: Int = 0) : Text()
    }
    class TextField(override val index: Int = 0) : ViewType()

    /**
     * In iOS this a password textfield.
     */
    class SecureTextField(override val index: Int = 0) : ViewType()
    class Button(override val index: Int = 0) : ViewType()
    class Group(override val index: Int = 0) : ViewType()
    class Any(override val index: Int = 0) : ViewType()
}