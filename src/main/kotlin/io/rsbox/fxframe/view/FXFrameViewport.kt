package io.rsbox.fxframe.view

import io.rsbox.fxframe.controller.FXFrameController
import tornadofx.View
import tornadofx.borderpane

/**
 * Represents the container view for the Borderless frame.
 */
class FXFrameViewport : View() {

    private val controller: FXFrameController by inject()

    init {
        titleProperty.bind(controller.initView.titleProperty)
    }

    override val root = borderpane {
        styleClass.add("viewport")

        /**
         * Add the titlebar fragment
         */
        top = find<FXFrameTitleBar>().root

        /**
         * Add the initView set in the controller
         */
        center = controller.initView.root
    }
}